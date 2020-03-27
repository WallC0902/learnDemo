package com.example.export.word;


import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.lowagie.text.html.HtmlTags.FONT;

/**
 * @program: demo
 * @description: freeMarker转pdf
 * @author: Wangchangpeng
 * @create: 2020-03-26
 **/
public class JavaToPdfHtmlFreeMarker {


//        private static final String DEST = "target/HelloWorld_CN_HTML_FREEMARKER_FS.pdf";
//        private static final String HTML = "template_freemarker_fs.html";
//        private static final String FONT = "simhei.ttf";
//        private static final String LOGO_PATH = "file://"+PathUtil.getCurrentPath()+"/logo.png";
//
//        private static Configuration freemarkerCfg = null;
//
//        static {
//            freemarkerCfg =new Configuration();
//            //freemarker的模板目录
//            try {
//                freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public static void main(String[] args) throws IOException, DocumentException, com.lowagie.text.DocumentException {
//            Map<String,Object> data = new HashMap();
//            data.put("name","鲁家宁");
//            String content = JavaToPdfHtmlFreeMarker.freeMarkerRender(data,HTML);
//            JavaToPdfHtmlFreeMarker.createPdf(content,DEST);
//        }

//    /**
//     * freemarker渲染html
//     */
//    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
//        Writer out = new StringWriter();
//        try {
//            // 获取模板,并设置编码方式
//            Template template = freemarkerCfg.getTemplate(htmlTmp);
//            template.setEncoding("UTF-8");
//            // 合并数据模型与模板
//            template.process(data, out); //将合并后的数据和模板写入到流中，这里使用的字符流
//            out.flush();
//            return out.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return null;
//    }

    public static void createPdf(String content) {
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        try {
            fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
        render.getSharedContext().setBaseURL("/Users/wall/Develop/Code/learnDemo/src/main/resources/timg.jpeg");
        render.layout();
        try {
            render.createPDF(new FileOutputStream("/Users/wall/Develop/Code/learnDemo/src/main/resources/HelloWorld_CN_HTML_FREEMARKER_FS.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
