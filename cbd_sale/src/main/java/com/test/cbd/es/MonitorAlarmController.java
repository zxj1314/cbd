/**
 * @Project: rcp-java
 * @Copyright: ©2017  广州弘度信息科技有限公司. 版权所有
 */
package com.test.cbd.es;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonParser;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.QueryStringQueryBuilder;
//import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
//import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
///**
// * @author zxj
// * @ClassName PrisonAlarmController
// * @Description
// * @Date 2017/10/30.
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "prison/alarm")
//public class MonitorAlarmController {
//
//
//    @Autowired
//    private MonitorAlarmRepository monitorAlarmRepository;
//
//    //============================elasticsearch
//    /**
//     * 1、根据id查
//     * @param id
//     * @return
//     */
//    @GetMapping("/esById/{id}")
//    public MonitorAlarmVO getBookById(@PathVariable Long id) {
//        return monitorAlarmRepository.findOne(id);
//    }
//
//    /**
//     * 2、查  ++:全文检索（根据整个实体的所有属性，可能结果为0个）
//     * @param key
//     * @return
//     */
//    @GetMapping("/esByKey/{key}")
//    public List<MonitorAlarmVO> testSearch(@PathVariable String key) {
//        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(key);
//        Iterable<MonitorAlarmVO> searchResult = monitorAlarmRepository.search(builder);
//        Iterator<MonitorAlarmVO> iterator = searchResult.iterator();
//        List<MonitorAlarmVO> list = new ArrayList<MonitorAlarmVO>();
//        while (iterator.hasNext()) {
//            list.add(iterator.next());
//        }
//        return list;
//    }
//
//    /**
//     * 3、查   +++：分页、分数、分域（结果一个也不少）
//     * @param page
//     * @param size
//     * @param q
//     * @return
//     * @return
//     */
//    @GetMapping("/{page}/{size}/{key}")
//    public List<MonitorAlarmVO> searchCity(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String key) {
//
//        // 分页参数
//        Pageable pageable = new PageRequest(page, size);
//
//        // 分数，并自动按分排序
//        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name", key)),
//                        ScoreFunctionBuilders.weightFactorFunction(1000)) // 权重：name 1000分
//                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("message", key)),
//                        ScoreFunctionBuilders.weightFactorFunction(100)); // 权重：message 100分
//
//        // 分数、分页
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable)
//                .withQuery(functionScoreQueryBuilder).build();
//
//        Page<MonitorAlarmVO> searchPageResults = monitorAlarmRepository.search(searchQuery);
//        return searchPageResults.getContent();
//
//    }
//
//    /**
//     * 4、增
//     * @param monitorAlarmVO
//     * @return
//     */
//    @PostMapping("/esInsert")
//    public MonitorAlarmVO insertBook(MonitorAlarmVO monitorAlarmVO) {
//        monitorAlarmRepository.save(monitorAlarmVO);
//        return monitorAlarmVO;
//    }
//
//    /**
//     * 5、删 id
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/esDelete/{id}")
//    public MonitorAlarmVO insertBook(@PathVariable Long id) {
//        MonitorAlarmVO monitorAlarmVO = monitorAlarmRepository.findOne(id);
//        monitorAlarmRepository.delete(id);
//        return monitorAlarmVO;
//    }
//
//    /**
//     * 6、改
//     * @param monitorAlarmVO
//     * @return
//     */
//    @PutMapping("/esUpdate")
//    public MonitorAlarmVO updateBook(MonitorAlarmVO monitorAlarmVO) {
//        monitorAlarmRepository.save(monitorAlarmVO);
//        return monitorAlarmVO;
//    }
//
//
//    //============================elasticsearch
//}
//