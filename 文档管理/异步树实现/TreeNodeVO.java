package com.hdvon.nmp.vcase.framework.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：同步树
 * 作者：chenjiefeng
 * 日期：2018/6/15
 * 版权所有：广州弘度信息科技有限公司 版权所有(C) 2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeVO implements Serializable {

    private String id;
    //名称
    private String title;
    //名称
    private String name;
    //编码
    private String code;

    private Integer level;

    //是否还有子节点
    private Boolean expand;
    //父id
    private String pid;

    private List<TreeNodeVO> children;

}
