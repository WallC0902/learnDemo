package com.example.export.freeMarker;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: 测试doc转存html再导出pdf
 * @author: Wangchangpeng
 * @create: 2020-03-28
 **/
public class TestDocHtmlExport {

    public static void main(String[] args) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("address", "杭州涂鸦智能科技有限公司");
        data.put("title", "杭州涂鸦智能科技有限公司");
        data.put("yiFang", "杭州涂鸦智能科技有限公司444444444444");
        data.put("ddd", "杭州涂鸦智能科技有限公司444444444444");

        PdfTemplateUtil.render("uEditor.html", "ddd.pdf", data);
    }
}
