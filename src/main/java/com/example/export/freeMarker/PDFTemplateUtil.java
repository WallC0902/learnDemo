package com.example.export.freeMarker;

import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.*;

import static com.example.export.word.FtlToWordUtil.imageToBase64Str;

/**
 * @program: demo
 * @description: freeMarker转pdf
 * @author: Wangchangpeng
 * @create: 2020-03-27
 **/
public class PDFTemplateUtil {

    public static void main(String[] args) throws Exception {
        // 模板中的数据，实际运用从数据库中查询
        Map<String, Object> data = new HashMap<>();
        data.put("title", "我是一个渲染后的标题");
        data.put("userName", "我是一个渲染后的用户名");
        data.put("content", "我是一个渲染后的内容");
        data.put("curr", 85);
        data.put("one", 87);
        data.put("two", 89);
        data.put("three", 85);

        List<Map<String, Object>> list = new ArrayList<>(16);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> itemMap = new HashMap<>(16);
            itemMap.put("name", "姓名" + (i + 1));
            itemMap.put("age", 14 + (i + 1));
            itemMap.put("gender", i % 2 == 0 ? "男" : "女");
            itemMap.put("point", 80 + (i + 1));
            itemMap.put("address", "这个是地址哦-" + (i + 1));
            list.add(itemMap);
        }
        data.put("dataList", list);
        data.put("imageUrl", "data:image/jpeg;base64," + imageToBase64Str(getClassPath("/timg.jpeg")));

        ByteArrayOutputStream pdf = PDFTemplateUtil.createPDF(data, "testExport.ftl");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(getClassPath("/freeMark.pdf"));
            fileOutputStream.write(pdf.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过模板导出pdf文件
     *
     * @param data             数据
     * @param templateFileName 模板文件名
     * @throws Exception
     */
    public static ByteArrayOutputStream createPDF(Map<String, Object> data, String templateFileName) throws Exception {
        // 创建一个FreeMarker实例, 负责管理FreeMarker模板的Configuration实例
        Configuration cfg = new Configuration();
        // 指定FreeMarker模板文件的位置
        cfg.setClassForTemplateLoading(PDFTemplateUtil.class, "/");
        ITextRenderer renderer = new ITextRenderer();
        OutputStream out = new ByteArrayOutputStream();
        try {
            // 设置 css中 的字体样式（暂时仅支持宋体和黑体） 必须，不然中文不显示
            // TODO 记得这个字体
            renderer.getFontResolver().addFont(getClassPath("/font/simsun.ttc"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // 设置模板的编码格式
            cfg.setEncoding(Locale.CHINA, "UTF-8");
            // 获取模板文件
            Template template = cfg.getTemplate(templateFileName, "UTF-8");
            StringWriter writer = new StringWriter();

            // 将数据输出到html中
            template.process(data, writer);
            writer.flush();

            String html = writer.toString();
            // 把html代码传入渲染器中
            renderer.setDocumentFromString(html);

            // 设置模板中的图片路径 （这里的images在resources目录下） 模板中img标签src路径需要相对路径加图片名 如<img src="images/xh.jpg"/>
//            String url = PDFTemplateUtil.class.getClassLoader().getResource("images").toURI().toString();
//            renderer.getSharedContext().setBaseURL(url);
            renderer.layout();

            renderer.createPDF(out, false);
            renderer.finishPDF();
            out.flush();
            return (ByteArrayOutputStream) out;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getClassPath(String name) {
        String path = PDFTemplateUtil.class.getResource(name).getPath();
        System.out.println(name + "相对应的文件目录: " + path);
        return path;
    }
}