package com.lix.freemarker.utils;

import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerUtils {

    public static String geneFile(String fltFile, Map<String, Object> param) {

        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(FreeMarkerUtils.class, "/static/template/");
        try {
            Template template = cfg.getTemplate(fltFile);
            StringWriter sw = new StringWriter();
            template.process(param, sw);
            System.out.println("ok....");
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";

    }


    public static void main(String[] args) throws Exception {
        /*Map map = new HashMap();
        map.put("messageId", "122112");
        map.put("id", "1233211234567");
        map.put("version", "1.0.1");
        map.put("date", "20200324");
        geneFile("accountQuery.ftl", map);*/
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<students>\n" +
                "    <desc>这是关于学生信息的数据</desc>\n" +
                "    <student>\n" +
                "        <name>jack</name>\n" +
                "        <age>12</age>\n" +
                "        <desc>a good boy</desc>\n" +
                "    </student>\n" +
                "    <student>\n" +
                "        <name>jane</name>\n" +
                "        <age>13</age>\n" +
                "        <desc>a beautiful girl</desc>\n" +
                "    </student>\n" +
                "</students>\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        NodeModel parse = NodeModel.parse(new InputSource(inputStream));
        Map map=new HashMap();
        map.put("data", parse);
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(FreeMarkerUtils.class, "/static/template/");
        Template template = cfg.getTemplate("test.ftl");
        StringWriter sw = new StringWriter();
        template.process(map, sw);
        System.out.println(sw.toString());
    }
}
