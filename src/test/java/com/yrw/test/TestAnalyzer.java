package com.yrw.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;

public class TestAnalyzer {

    /*
        使用第三方中文分词器IK分词
        特点：支持中文语义分析，提供停用词典，提供扩展词典
     */
    @Test
    public void TestIKAnalyzer() throws Exception{
        // 1. 创建分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();

        // 2. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();

        doc.add(new TextField("name", "vivo X23 8GB+128GB 幻夜蓝,水滴屏全面屏,游戏手机.移" +
                "动联通电信全网通4G手机", Field.Store.YES));
        indexWriter.addDocument(doc);

        // 6.释放资源
        indexWriter.close();
    }

}
