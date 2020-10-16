package com.yrw.dao;

import com.yrw.pojo.paper;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaperList {

    public List<paper> queryPaperList() throws DocumentException {
        //论文列表
        List<paper> paperList = new ArrayList<>();
        //获取所有文件名
        List<String> files = getFiles("D:\\Users\\14036\\Desktop\\lucene\\_output");
        String path = "D:\\Users\\14036\\Desktop\\lucene\\_output\\";

        File file = null;
        SAXReader reader = null;
        //遍历文件
        for (String filename : files) {
            paper pp = new paper();
            reader = new SAXReader();
            file = new File(path + filename);
            //读xml文件返回文档对象
            Document doc = reader.read(file);
            //得到xml根节点
            Element root = doc.getRootElement();
            try{
                pp.setFilename(filename);
                pp.setAuthor(getAuthor(root));
                pp.setDate(getDate(root));
                pp.setFulltext(getFullText(root));
                pp.setKeywords(getKeywords(root));
                pp.setReference(getReference(root));
                pp.setSummary(getSummary(root));
                pp.setTitle(getTitle(root));
            } catch(Exception e){
                e.printStackTrace();
            }
            paperList.add(pp);
        }

        return paperList;
    }

    //遍历文件夹，获取所有文件名
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                //files.add(tempList[i].toString());
                //文件名，不包含路径
                String fileName = tempList[i].getName();
                files.add(fileName);
            }
        }
        return files;
    }

    public static String getAuthor(Element root){
        Element analytic = root.element("teiHeader").element("fileDesc").element("sourceDesc").element("biblStruct").
                element("analytic");
        List<Element> al = analytic.elements();
        String allname = "";
        for (Element element : al) {
            if (element.getName().equals("author")){
                String name = "";
                Element persname = element.element("persName");
                if (persname != null) {
                    List<Element> nl = persname.elements();
                    for (Element n : nl) {
                        if (n.getName().equals("forename") || n.getName().equals("surname")){
                            name = name + " " + n.getTextTrim();
                        }
                    }
                    //System.out.println(name.trim());
                    allname = allname + "," + name.trim();
                }
            }
        }

        return allname.replaceFirst(",","");
    }

    public static Long getDate(Element root) throws ParseException {
        Element date = root.element("teiHeader").element("fileDesc").element("publicationStmt").element("date");
        if (date != null) {
            Attribute when = date.attribute("when");
            if (when != null) {
                String t = when.getValue().trim();
                SimpleDateFormat sdf = null;
                if (t.length() == 4) {
                    sdf = new SimpleDateFormat("yyyy");
                }else if (t.length() == 7) {
                    sdf = new SimpleDateFormat("yyyy-MM");
                }else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                try {
                    Date dateParse = sdf.parse(t);
                    return dateParse.getTime();
                } catch (Exception e) {
                    return  new Long(0);
                }
            }
        }
        return new Long(0);
    }

    public static String getFullText(Element root){
        Element body = root.element("text").element("body");
        String b = body.getStringValue().trim().replace("\n"," ").replace("\t"," ")
                .replace("  "," ").replace("  "," ");
        return b;
    }

    public static String getKeywords(Element root){
        Element keywords = root.element("teiHeader").element("profileDesc").element("textClass");
        if (keywords == null){
            return "";
        }
        return  getText(keywords.element("keywords"));

    }

    public static String getReference(Element root){
        List<Element> ref = root.element("text").element("back").elements();
        String res = "";
        for (Element element : ref) {
            if (element.attribute("type").getValue().trim().equals("references")) {
                Element listBibl = element.element("listBibl");
                List<Element> elements = listBibl.elements();
                for (Element o : elements) {
                    String item = o.getStringValue().trim().replace("\n"," ").replace("\t"," ")
                            .replace("  "," ").replace("  "," ");
                    res = res + item + "\n";

                }

            }
        }
        return res;
    }

    public static String getSummary(Element root){
        Element summary = root.element("teiHeader").element("profileDesc").element("abstract").element("div");
        if (summary == null) {
            return "";
        }
        String s = summary.getStringValue().trim().replace("\n","");
        return s;
    }

    public static String getTitle(Element root){
        Element title = root.element("teiHeader").element("fileDesc").element("titleStmt").element("title");
        return title.getTextTrim();
    }


    //遍历子节点，并将值拼接起来
    public static String getText(Element node){
        String text = "";
        List<Element> l = node.elements();
        for (Element element : l) {
            text = text + element.getTextTrim() + ".";
        }
        return text;
    }


}
