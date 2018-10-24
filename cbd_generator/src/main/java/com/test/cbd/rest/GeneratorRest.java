package com.test.cbd.rest;

import com.alibaba.fastjson.JSON;
import com.test.cbd.common.TableResultResponse;
import com.test.cbd.service.GeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>
 * <b>作者：</b>chenjiefeng<br>
 * <b>日期：</b> 2018-3-1 <br>
 * <b>版权所有：<b>广州弘度信息科技有限公司 版权所有(C) 2018<br>
 */
@Controller
@RequestMapping("/base/generator")
public class GeneratorRest {

    @Autowired
    private GeneratorService generatorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/page")
    public TableResultResponse<Map<String, Object>> list(@RequestParam Map<String, Object> params) {
        List<Map<String, Object>> result = generatorService.queryList(params);
        int total = generatorService.queryTotal(params);
        return new TableResultResponse<Map<String, Object>>(total, result);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[]{};
        String tables = request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        byte[] data = generatorService.generatorCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator-code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
