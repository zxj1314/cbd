package com.test.cbd.es; /**
 * @Project: rcp-java
 * @Copyright: ©2017  广州弘度信息科技有限公司. 版权所有
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huangjunjie
 * @Description
 * @Date 2017/10/30.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "monitoralarm", type = "monitorAlarms")
public class MonitorAlarmVO implements Serializable {

    @Id
    private Long id;
    private String uniqueKey;
    private Long monitoryPointId;
    private int modelDivisionType;
    private Long groupId;
    private int type;
    private int status;
    private String serverHost;
    private String data;
    private int picWidth;
    private int picHeight;
    private String objects;
    private Date alarmTime;
    private Date createTime;
    private boolean isRemove;
    private Date removeTime;
}
