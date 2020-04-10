package com.lix.freemarker.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.List;


/**
 *         需要fastjson与dom4j
 *          <dependency>
 *             <groupId>org.freemarker</groupId>
 *             <artifactId>freemarker</artifactId>
 *             <!--<version>2.3.22</version>-->
 *         </dependency>
 *         <dependency>
 *             <groupId>dom4j</groupId>
 *             <artifactId>dom4j</artifactId>
 *         </dependency>
 *         <dependency>
 *             <groupId>com.alibaba</groupId>
 *             <artifactId>fastjson</artifactId>
 *             <version>1.2.47</version>
 *         </dependency>
 */
public class XMLUtils {


    public static void main(String[] args) throws DocumentException {
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "<ResInfo>\n" +
//                "    <barcode/>\n" +
//                "    <asset>\n" +
//                "        <assetcardno>B</assetcardno>\n" +
//                "        <comments/>\n" +
//                "        <buydate/>\n" +
//                "        <category/>\n" +
//                "    </asset>\n" +
//                "    <entity>\n" +
//                "        <entityid>901061314</entityid>\n" +
//                "        <entitycode>LTJHXYYCX01/XA-HWMACBTS529</entitycode>\n" +
//                "        <entityname>蓝田局华胥电信营业厅CDMA基站/BTS529</entityname>\n" +
//                "        <entityspec>BTS</entityspec>\n" +
//                "        <vendorname>HuaWei</vendorname>\n" +
//                "        <model>HUAWEI BTS3900</model>\n" +
//                "        <installaddress/>\n" +
//                "    </entity>\n" +
//                "    <version/>\n" +
//                "    <sectornum>3</sectornum>\n" +
//                "    <rackname>HW401C</rackname>\n" +
//                "    <containers>\n" +
//                "        <container>\n" +
//                "            <shelfhight>0.086</shelfhight>\n" +
//                "            <cardinfos>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框3槽(0)HECM</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框3槽(10)FAN</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框3槽(9)UPEU</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框3槽(7)CMPT</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框3槽(2)HCPM</cardname>\n" +
//                "                </cardinfo>\n" +
//                "            </cardinfos>\n" +
//                "        </container>\n" +
//                "        <container>\n" +
//                "            <shelfhight>0.308</shelfhight>\n" +
//                "            <cardinfos>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框1槽(2)CRFU</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框1槽(4)CRFU</cardname>\n" +
//                "                </cardinfo>\n" +
//                "                <cardinfo>\n" +
//                "                    <cardname>290.蓝田局华胥电信营业厅CDMA基站/BTS529/架1列1/框1槽(0)CRFU</cardname>\n" +
//                "                </cardinfo>\n" +
//                "            </cardinfos>\n" +
//                "        </container>\n" +
//                "        <container>\n" +
//                "            <shelfhight>0.086</shelfhight>\n" +
//                "        </container>\n" +
//                "        <container>\n" +
//                "            <shelfhight>0.044</shelfhight>\n" +
//                "        </container>\n" +
//                "    </containers>\n" +
//                "    <ReturnResult>0</ReturnResult>\n" +
//                "    <ReturnInfo>成功</ReturnInfo>\n" +
//                "</ResInfo>";
//        JSONObject parse = parse(xml);
//        System.out.println(parse);

        // 计算int类型的长度
        int i = 574836584;
        int size = 0;
        for (; i > 1; i = i / 10) {
            size++;
        }
        System.out.println(size);
    }

    public static JSONObject parse(String xml) throws DocumentException {
        if (xml == null || xml.length() < 1) return null;
        SAXReader reader = new SAXReader();
        Document read = reader.read(new StringReader(xml));
        Element rootElement = read.getRootElement();
        JSONObject container = new JSONObject();
        convert(rootElement, container);
        return container;
    }


    private static void convert(Element ele, JSONObject jsonObject) {
        List<Element> elements = ele.elements();
        for (Element sub : elements) {
            if (sub.isTextOnly()) {
                jsonObject.put(sub.getName(), sub.getTextTrim());
                continue;
            }
            JSONObject obj = new JSONObject();
            convert(sub, obj);

            Object value = jsonObject.get(sub.getName());
            if (value == null) {
                jsonObject.put(sub.getName(), obj);
                continue;
            }
            if (value instanceof JSONObject) {
                JSONArray array = new JSONArray();
                array.add(value);
                array.add(obj);
                jsonObject.put(sub.getName(), array);
            } else {
                ((JSONArray) value).add(obj);
            }
        }
    }


}
