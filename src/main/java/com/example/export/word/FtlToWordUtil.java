package com.example.export.word;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 利用freemark生成word，再生成pdf导出
 * @author: Wangchangpeng
 * @create: 2020-03-25
 **/
public class FtlToWordUtil {

    private static Logger logger = LoggerFactory.getLogger(FtlToWordUtil.class);

    /**
     * 图片转base64的字符串
     *
     * @param imageAbsolutePath 图片路径
     * @return String
     */
    public static String imageToBase64Str(String imageAbsolutePath) {
        InputStream is = null;
        byte[] data = null;
        try {
            is = new FileInputStream(imageAbsolutePath);
            data = new byte[is.available()];
            is.read(data);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 初始化配置文件
     *
     * @param folderPath 生成word的文件夹路径
     * @return Configuration
     */
    private static Configuration initConfiguration(String folderPath) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(folderPath));
        } catch (IOException e) {
            logger.error("初始化configuration失败，路径不存在：{}", folderPath);
            e.printStackTrace();
        }
        return configuration;
    }

    /**
     * 将数据写入word，并输出到指定路径
     *
     * @param dataMap        需要写入word的数据
     * @param ftlFolderPath  指定的word模板所在的文件夹
     * @param tempName       指定的word模板名称
     * @param outputFilePath 输出路径
     */
    public static boolean createWord(Map<String, Object> dataMap, String ftlFolderPath, String tempName, String outputFilePath) {
        logger.info("开始创建word到本地");
        boolean result = false;
        Configuration configuration = initConfiguration(ftlFolderPath);
        Template t = null;
        try {
            t = configuration.getTemplate(tempName);
            t.setEncoding("utf-8");
        } catch (IOException e) {
            logger.error("在路径：{}里找不到：{}", ftlFolderPath, tempName);
            e.printStackTrace();
        }
//        JavaToPdfHtmlFreeMarker.createPdf(t.toString());


        File outFile = new File(outputFilePath);
        Writer out = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(outFile);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos, "UTF-8");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            t.process(dataMap, out);
            out.close();
            fos.close();
            result = true;
            logger.info("word创建成功");
        } catch (FileNotFoundException e) {
            logger.error("文件不存在：{}", outFile);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("未知的编码格式");
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("模板异常");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO异常");
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 导出word文档，响应到请求端
     *
     * @param tempName，要使用的模板
     * @param docName，导出文档名称
     * @param dataMap，模板中变量数据
     * @param resp,HttpServletResponse
     */
    public static boolean exportDoc(String folderPath, String tempName, String docName, Map<?, ?> dataMap, HttpServletResponse resp) {
        boolean status = false;
        Configuration configuration = initConfiguration(folderPath);
        ServletOutputStream sos = null;
        InputStream fin = null;
        if (resp != null) {
            resp.reset();
        }

        Template t = null;
        try {
            // tempName.ftl为要装载的模板
            t = configuration.getTemplate(tempName);
            t.setEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出文档路径及名称 ,以临时文件的形式导出服务器，再进行下载
        String name = folderPath + "temp" + (int) (Math.random() * 100000) + ".doc";
        File outFile = new File(name);

        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            status = true;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fin = new FileInputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 文档下载
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/msword");
        try {
            docName = new String(docName.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        resp.setHeader("Content-disposition", "attachment;filename=" + docName + ".doc");
        try {
            sos = resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[512]; // 缓冲区
        int bytesToRead = -1;
        // 通过循环将读入的Word文件的内容输出到浏览器中
        try {
            while ((bytesToRead = fin.read(buffer)) != -1) {
                sos.write(buffer, 0, bytesToRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outFile != null) {
                outFile.delete(); // 删除临时文件
            }
        }

        return status;
    }


    public static void makePdfByXcode(String wordUrl, String pdfUrl) {
        long startTime = System.currentTimeMillis();
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(new File(wordUrl)));
            File outFile = new File(pdfUrl);
            outFile.getParentFile().mkdirs();
            OutputStream out = new FileOutputStream(outFile);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Generate ooxml.pdf with " + (System.currentTimeMillis() - startTime) + " ms.");
    }


    public static void main(String[] args) throws IOException {
        Map<String, Object> dataMap = new HashMap<>();
//        List<Map<String, Object>> list = new ArrayList<>(16);
//        for (int i = 0; i < 10; i++) {
//            Map<String, Object> itemMap = new HashMap<>(16);
//            itemMap.put("name", "姓名" + (i + 1));
//            itemMap.put("age", 14 + (i + 1));
//            itemMap.put("sex", i % 2 == 0 ? "男" : "女");
//            itemMap.put("point", 80 + (i + 1));
//            itemMap.put("address", "这个是地址哦-" + (i + 1));
//            list.add(itemMap);
//        }
//
//        dataMap.put("itemList", list);


//        dataMap.put("title", "我是替换后的标题");
//        dataMap.put("txt", "我是被替换后的普通文本");
//        //图片转码
//        dataMap.put("imageUrl", imageToBase64Str("/Users/wall/Develop/Code/learnDemo/src/main/resources/timg.jpeg"));


        List<Map<String, Object>> list = new ArrayList<>(16);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> itemMap = new HashMap<>(16);
            itemMap.put("name", "APP代码定制开发" + (i + 1));
            itemMap.put("num", 20000 + (i + 1));
            itemMap.put("type", 6);
            list.add(itemMap);
        }

        dataMap.put("dataList", list);
//        1 APP代码定制开发 20000 6
//        2 APP面板定制开发 10000 3
//        3 APPUI定制 10000 3
//        4 固件定制 5000 2
//        5 APP上架 2000 1
//        6 APP与硬件联调 6000 2.5


        dataMap.put("jiafang", "我是被替换后的普通文本22222222");

//        createWord(dataMap, "/Users/wall/Develop/Code/learnDemo/src/main/resources/", "model.ftl", "/Users/wall/Develop/Code/learnDemo/src/main/resources/test.docx");
        createWord(dataMap, "/Users/wall/Develop/Code/learnDemo/src/main/resources/", "666666666.xml", "/Users/wall/Develop/Code/learnDemo/src/main/resources/test.docx");
//        makePdfByXcode("/Users/wall/Develop/Code/learnDemo/src/main/resources/test.docx", "/Users/wall/Develop/Code/learnDemo/src/main/resources/test.pdf");

        System.out.println("模板生成成功");
    }

}
