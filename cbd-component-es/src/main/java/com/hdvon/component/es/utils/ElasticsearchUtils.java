package com.hdvon.component.es.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ElasticsearchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtils.class);

    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 创建索引以及设置其内容
     * @param index
     * @param indexType
     * @param filePath:json文件路径
     */
    public static void createIndex(String index,String indexType,String filePath) throws RuntimeException {
        try {
                StringBuffer strBuf = new StringBuffer();
                //解析json配置
                ClassPathResource resource = new ClassPathResource(filePath);
                InputStream inputStream = resource.getInputStream();

                int len = 0;
                byte[] buf = new byte[1024];
                while((len=inputStream.read(buf)) != -1) {
                    strBuf.append(new String(buf, 0, len, "utf-8"));
                }
                inputStream.close();
                //创建索引
                createIndex(index);
                //设置索引元素
                putMapping(index, indexType, strBuf.toString());

        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


        /**
         * 创建索引
         *
         * @param index 索引名称
         * @return
         */
        public static boolean createIndex(String index){

            try {
                if (isIndexExist(index)) {
                    //索引库存在则删除索引
                    deleteIndex(index);
                }
                CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).setSettings(Settings.builder().put("index.number_of_shards", 5)
                        .put("index.number_of_replicas", 1)
                )
                        .get();
                LOGGER.info("创建索引 {} 执行状态 {}", index , indexresponse.isAcknowledged());

                return indexresponse.isAcknowledged();
            }catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }


    /**
     * 创建索引
     *
     * @param index 索引名称
     * @param indexType 索引类型
     * @param mapping 创建的mapping结构
     * @return
     */
    public static boolean putMapping(String index,String indexType,String mapping) throws RuntimeException {
        if (!isIndexExist(index)) {
            throw new RuntimeException("创建索引库"+index+"mapping"+mapping+"结构失败,索引库不存在!");
        }
        try {
            PutMappingResponse indexresponse = client.admin().indices().preparePutMapping(index).setType(indexType).setSource(mapping, XContentType.JSON).get();

            LOGGER.info("索引 {} 设置 mapping {} 执行状态 {}", index ,indexType, indexresponse.isAcknowledged());

            return indexresponse.isAcknowledged();
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index))
                .actionGet();
        return inExistsResponse.isExists();
    }


    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) throws RuntimeException{
        if (!isIndexExist(index)) {
            return true;
        }
        try {
            DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
            if (dResponse.isAcknowledged()) {
                LOGGER.info("delete index " + index + "  successfully!");
            } else {
                LOGGER.info("Fail to delete index " + index);
            }
            return dResponse.isAcknowledged();
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 数据添加
     *
     * @param jsonObject
     *            要增加的数据
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type) {
        return addData(jsonObject, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject
     *            要增加的数据
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @param id
     *            数据ID
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type, String id)throws RuntimeException {
        try {
            IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();

            LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());

            return response.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 批量数据添加，
     *
     * @param list
     *            要增加的数据
     * @param pkName
     *            主键id
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @return
     */
    public static <T> void addBatchData(List<T> list, String pkName, String index, String type) {
        if(list == null || list.isEmpty()) {
            return;
        }
        // 创建BulkPorcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
                // TODO Auto-generated method stub
            }

            // 执行出错时执行
            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
                // TODO Auto-generated method stub
            }
        })
                // 1w次请求执行一次bulk
                .setBulkActions(1000)
                // 1gb的数据刷新一次bulk
                // .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                // 设置退避, 100ms后执行, 最大请求3次
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

        for (T vo : list) {
            if(getPkValueByName(vo, pkName)!= null) {
                String id = getPkValueByName(vo, pkName).toString();
                bulkProcessor.add(new IndexRequest(index, type, id).source(JSON.toJSONString(vo), XContentType.JSON));
            }

        }
        bulkProcessor.close();
    }

    /**
     * 根据主键名称获取实体类主键属性值
     *
     * @param clazz
     * @param pkName
     * @return
     */
    private static Object getPkValueByName(Object clazz, String pkName) {
        try {
            String firstLetter = pkName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + pkName.substring(1);
            Method method = clazz.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(clazz, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 通过ID 更新数据
     *
     * @param jsonObject
     *            要增加的数据
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @param id
     *            数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String type, String id) throws RuntimeException {

        try{
            UpdateRequest updateRequest = new UpdateRequest();

            updateRequest.index(index).type(type).id(id).doc(jsonObject);

            client.update(updateRequest);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 批量数据更新，
     *
     * @param list
     *            要增加的数据
     * @param pkName
     *            主键id
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @return
     */
    public static <T> void updateBatchData(List<T> list, String pkName, String index, String type) {
        // 创建BulkPorcessor对象
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long paramLong, BulkRequest paramBulkRequest) {
                // TODO Auto-generated method stub
            }

            // 执行出错时执行
            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, Throwable paramThrowable) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterBulk(long paramLong, BulkRequest paramBulkRequest, BulkResponse paramBulkResponse) {
                // TODO Auto-generated method stub
            }
        })
                // 1w次请求执行一次bulk
                .setBulkActions(1000)
                // 1gb的数据刷新一次bulk
                // .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                // 设置退避, 100ms后执行, 最大请求3次
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

        for (T vo : list) {
            String id = getPkValueByName(vo, pkName).toString();
            bulkProcessor.add(new UpdateRequest(index, type, id).doc(JSON.toJSONString(vo), XContentType.JSON));
        }
        bulkProcessor.close();
    }


    /**
     * 通过ID获取数据
     *
     * @param index
     *            索引，类似数据库
     * @param type
     *            类型，类似表
     * @param id
     *            数据ID
     * @param fields
     *            需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse = getRequestBuilder.execute().actionGet();

        return getResponse.getSource();
    }

    /**
     * 使用分词查询
     *
     * @param index
     *            索引名称
     * @param type
     *            类型名称,可传入多个type逗号分隔
     * @param clz
     *            数据对应实体类
     * @param fields
     *            需要显示的字段，逗号分隔（缺省为全部字段）
     * @param boolQuery
     *            查询条件
     * @return
     */
    public static <T> List<T> searchListData(String index, String type, Class<T> clz, String fields,BoolQueryBuilder boolQuery) {
        return searchListData(index, type, clz, 0, fields, null,  null,boolQuery);
    }

    /**
     * 使用分词查询
     *
     * @param index
     *            索引名称
     * @param type
     *            类型名称,可传入多个type逗号分隔
     * @param clz
     *            数据对应实体类
     * @param size
     *            文档大小限制
     * @param fields
     *            需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField
     *            排序字段
     * @param highlightField
     *            高亮字段
     * @param boolQuery
     *            查询条件
     * @return
     */
    public static <T> List<T> searchListData(String index, String type, Class<T> clz,
                                             Integer size, String fields, String sortField, String highlightField,BoolQueryBuilder boolQuery) throws RuntimeException{

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }
        searchRequestBuilder.setQuery(boolQuery);
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }
        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }
        searchRequestBuilder.setScroll(new TimeValue(1000));
        searchRequestBuilder.setSize(10000);
        // 打印的内容 可以在 Elasticsearch head 和 Kibana 上执行查询
        LOGGER.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        if(LOGGER.isDebugEnabled()) {
            long length = searchResponse.getHits().getHits().length;

            LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);
        }


        if (searchResponse.status().getStatus() ==200) {
            // 解析对象
            return setSearchResponse(clz, searchResponse, highlightField);
        }

        return null;
    }


    /**
     * 高亮结果集 特殊处理
     *
     * @param clz
     *            数据对应实体类
     * @param searchResponse
     *
     * @param highlightField
     *            高亮字段
     */
    private static <T> List<T> setSearchResponse(Class<T> clz, SearchResponse searchResponse, String highlightField) {
        List<T> sourceList = new ArrayList<T>();
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());
            StringBuffer stringBuffer = new StringBuffer();
            if (StringUtils.isNotEmpty(highlightField)) {

                // System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                HighlightField highlight = searchHit.getHighlightFields().get(highlightField);
                if(highlight == null) {
                    continue;
                }
                Text[] text = highlight.getFragments();
                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    // 遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }

            T t = JSON.parseObject(JSON.toJSONString(searchHit.getSourceAsMap()), clz);
            sourceList.add(t);
        }

        return sourceList;
    }

}
