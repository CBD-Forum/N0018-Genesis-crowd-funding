package com.fbd.core.app.sxyPay.utils;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
/**
 * dom解析xml全节点
 * @author xw
 *
 */
public class DuXMLDoc {
    public List xmlElements(String xmlDoc) {
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称（测试）
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            //获得XML中的命名空间（XML中未定义可不写）
            Namespace ns = root.getNamespace();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);//循环依次得到子元素
                System.out.println("--"+et.getName());
                /**//*
                 * 无命名空间定义时
                 * et.getChild("users_id").getText();
                 * et.getChild("users_address",ns).getText()
                 */
//                System.out.println(et.getChild("users_id",ns).getText());
//                System.out.println(et.getChild("users_address",ns).getText());
            }
            /**//*
             * 如要取<row>下的子元素的名称
             */
            et = (Element) jiedian.get(1);
            List zjiedian = et.getChildren();
            Namespace nss = et.getNamespace();
            for(int j=0;j<zjiedian.size();j++){
                Element xet = (Element) zjiedian.get(j);
               // System.out.println(xet.getName());
                System.out.println(xet.getChild("refid",nss).getText());
                System.out.println(xet.getChild("oid",nss).getText());
                System.out.println(xet.getChild("refmoneytype",nss).getText());
                System.out.println(xet.getChild("refamount",nss).getText());
                System.out.println(xet.getChild("refstatus",nss).getText());
                System.out.println(xet.getChild("reftype",nss).getText());
                System.out.println(xet.getChild("refdate",nss).getText());
                
                System.out.println();
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        DuXMLDoc doc = new DuXMLDoc();
        String xml = "<?xml version='1.0' encoding='gb2312'?><ordermessage><messagehead><status>0</status><statusdesc>Success</statusdesc><mid>6565</mid><ymd>20130711</ymd><version>1.1</version></messagehead><messagebody><refund><refundindex>1</refundindex><oid>20130608-6565-000119</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1920055</refid><refamount>0.00</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>9fe51f0f7ba2dd75a63cc2194a3a42f3bd7cbcd58e0d6b4fd5cb29a32a6e6136438837f3ad5b47396cb62fab4b15d90ead12060f8b0ce997705fc3c466f7a574febb07cd399a02b0d921d36b4488fe72bf699a6e4015e17a43e8b1b9d9a9232c718abfefe3516ec351225fdfafeb3046f33e51c90b8456e8be8e7423949e34e9</sign></refund><refund><refundindex>2</refundindex><oid>20130711-6565-000178</oid><amount>0.02</amount><moneytype>0</moneytype><refid>1918825</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>a0981033720f5de03aae8440c77fb322f2511a1947b6b89d7b0f27a96a464daf057bb71cd7560a8383db4bb795e5f224a3252cd973292396d0acde8a221d195da6680f41dc89578c678c664e4eb7c5a1543ff1b5984e9a0b975542ca336d3ea34725a0f30682bb39343ddfd71d5ba5f99061f6e235a67a50caf9f63330e622ea</sign></refund><refund><refundindex>3</refundindex><oid>20130711-6565-000185</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1919381</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>a39089433741d4edba445afd35f1549402d881eb2796b45478a2ecd89365c4b483973c8d87dbf1fd7b36930999a8733a2af565c304c0272e20efb6f025b816aebb864321528776fcf44395baed5c25fc7cc522242a7e9faeee3be36e8fc68e71137f600b1b57d985717e5f52b4b812a97fab8ee8bf521685de85244bbb542b30</sign></refund><refund><refundindex>4</refundindex><oid>20130711-6565-000184</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1919356</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>24d52dde5dfbc7ab531d2f7d90352b06d4001e872fccc8843676ef82ff2f3931bbd01544f283318a760eda20596fa2bda0ad294399f4e3ee4d494d9631f9bcc5405e3d89708d942f70692afc3254e2f1541b2c583db6532b7c9743ef2b6e4f34b0580ac7796319635a72f1c90c4178b8ad8db62faa609612ddffb34b97cdced7</sign></refund><refund><refundindex>5</refundindex><oid>20130711-6565-000187</oid><amount>0.02</amount><moneytype>0</moneytype><refid>1919461</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>4b2582cfcd7ad9e5d08fe02073381ebba48d3d371934fb45406580c5b0bda4135c6c5e737b63172de3f9404f89541c89c163e605567df17eb9c0626e81fe599dbe60dba8d346b8eb9d8fe135c5d799f36c63a61938197d75f0cf5e649a97cbf353fcb42826a23577c3deccc7f16337c7dd117bb955daff7fdeaa8614a3d64006</sign></refund><refund><refundindex>6</refundindex><oid>20130711-6565-000178</oid><amount>0.02</amount><moneytype>0</moneytype><refid>1918820</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>531a47f54511ac04ef4300636be0090122401f68f68ae4526b1bcd00fc36f7b9d2fb1d7d7f252b795914a8dee000de7599464eaa56db75917acf48d5af917e6533e3a23482fd0ee089d5597d10e3078e15ff141a9f003bf103298c860c630146d6a4b10393ff76a253c8971a7b7b44c484dacef28288dd43bd62515026ad6f66</sign></refund><refund><refundindex>7</refundindex><oid>20130115-6565-000022</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1918783</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>1343586448aad237684624c182e2eb8c791ade0a7039fc7398e6408428dde6d3c1e24278005b67dc3db3411a349133b03b84f101513c077eb6aab7e5630aa40885323c7d93bfe735dc162b7bddbd769f4b27580fc406d35f18b7d7c74a072e1d91750ea0f7a6825dd11bc256caf40d753bb5f569f0f59447f9ade2fa7b944de5</sign></refund><refund><refundindex>8</refundindex><oid>20130114-6565-000014</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1918784</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>75487566f50508f1d839c7b642677ada5ed18b7490f96d9d846bda3a4d54329d038c0ea6285aa318c1314a1b16511bcd1f582f2ca1790df08f3eae66eabafe1ac3b94f72dd3dbd6a01b50e51c04a2f004d525e7ccca7e87b03c6f469d946dcf31009d02529caa9609263396be76829ea1576e0976c990d4b8c5dcea737fd1a2d</sign></refund><refund><refundindex>9</refundindex><oid>20130114-6565-000015</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1918785</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>590e030a9ae63c28144fc310d8b06ad9dd7ac5e09251db19b58c51b04359dd4090099b846f999a041fb8cbe9c0d8379ce3952689cc8f06b4c96b809efb32d370e48cf925d1c51b915ca00347019268141a9467c991a62c14bedf647978859a11d20eebd39f23ddc10e850b0c3159e8277fd8c081df23a0cd94e2695f383dc901</sign></refund><refund><refundindex>10</refundindex><oid>20130114-6565-000106</oid><amount>0.01</amount><moneytype>0</moneytype><refid>1919655</refid><refamount>0.01</refamount><refmoneytype>0</refmoneytype><refdate>20130711</refdate><refstatus>1</refstatus><reftype>4</reftype><sign>4e547e76d6e9987495fd89962a8841d452102e8e082f5b58c24c4125082385d9f6628662d26dfa4ed686f1e45414834ed107160c6cce9cc75fc6e2ebe300f74cfb37a2be05135be1f12bdee51890b8c21b45f893aa47aac211cebd0fd96b2ad2986271fc608a98a1e8212cfe6e6086f5b0c880c2da58b69d12cd2d5f57c4e7fd</sign></refund></messagebody></ordermessage>";
        doc.xmlElements(xml);
    }
}
