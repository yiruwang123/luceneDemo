package com.yrw.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Paths;

/*
    测试搜索
 */
public class TestSearch {

    @Test
    public void testIndexSearch() throws Exception {
        //1.创建分词器(对搜索的关键词进行分词)
        //注意：分词器要和创建索引的时候使用的分词器一样
        Analyzer analyzer = new StandardAnalyzer();

        //2.创建查询对象,第一个参数：默认查询域，如果查询的关键字中带搜索的域名，则从指定域中查询，如果不带，则从默认域中查询
        // 第二个参数：使用的分词器
        QueryParser queryParser = new QueryParser("name", analyzer);

        //3.设置搜索关键词
        //这里也可以带上域名，如 queryParser.parse("brandname:华为手机");则会从brandname中查而不是默认的name中查
        Query query = queryParser.parse("华为手机");

        //4.创建directory目录对象，指定索引库的位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建输入流对象
        IndexReader indexReader = DirectoryReader.open(dir);

        //6.创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //7.搜索并返回结果,第二个参数是返回多少条数据用于展示，分页使用
        TopDocs topDocs = indexSearcher.search(query, 100);

        //获取查询到的结果集的总数
        System.out.println("totalcount:::::" + topDocs.totalHits);

        //8.获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        //9.遍历结果集
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                //获取查询到的文档唯一标识，文档id，这是lucene创建文档时自动分配的
                int docID = scoreDoc.doc;

                //通过文档id读取文档
                Document doc = indexSearcher.doc(docID);
                System.out.println("---------------------------------");
                //通过域名从文档中获取域值
                System.out.println("===id=="+doc.get("id"));
                System.out.println("===name=="+doc.get("name"));
                System.out.println("===brandName=="+doc.get("brandName"));
            }
        }

        //10.关闭流
        indexReader.close();
    }

    /*
        数值查询测试
     */
    @Test
    public void testRangeSearch() throws Exception {
        //1.创建分词器(对搜索的关键词进行分词)
        //注意：分词器要和创建索引的时候使用的分词器一样
        Analyzer analyzer = new StandardAnalyzer();

        //2.创建查询对象
        Query query = IntPoint.newRangeQuery("price", 100, 100);

        //4.创建directory目录对象，指定索引库的位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建输入流对象
        IndexReader indexReader = DirectoryReader.open(dir);

        //6.创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //7.搜索并返回结果,第二个参数是返回多少条数据用于展示，分页使用
        TopDocs topDocs = indexSearcher.search(query, 100);

        //获取查询到的结果集的总数
        System.out.println("totalcount:::::" + topDocs.totalHits);

        //8.获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        //9.遍历结果集
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                //获取查询到的文档唯一标识，文档id，这是lucene创建文档时自动分配的
                int docID = scoreDoc.doc;

                //通过文档id读取文档
                Document doc = indexSearcher.doc(docID);
                System.out.println("---------------------------------");
                //通过域名从文档中获取域值
                System.out.println("===id=="+doc.get("id"));
                System.out.println("===name=="+doc.get("name"));
                System.out.println("===brandName=="+doc.get("brandName"));
            }
        }

        //10.关闭流
        indexReader.close();
    }

    /*
        组合查询测试
     */
    @Test
    public void testBooleanSearch() throws Exception {
        //1.创建分词器(对搜索的关键词进行分词)
        //注意：分词器要和创建索引的时候使用的分词器一样
        Analyzer analyzer = new StandardAnalyzer();

        //2.创建查询对象
        Query query1 = IntPoint.newRangeQuery("price", 100, 1000);

        QueryParser queryParser = new QueryParser("name", analyzer);
        Query query2 = queryParser.parse("华为手机");

        //创建布尔查询对象(组合查询对象)
        /*
            BooleanClause.Occur.MUST 相当于and
            BooleanClause.Occur.SHOULD 相当于or
            BooleanClause.Occur.MUST_NOT 相当于not
         */
        BooleanQuery.Builder query = new BooleanQuery.Builder();
        query.add(query1, BooleanClause.Occur.MUST);
        query.add(query2, BooleanClause.Occur.MUST);

        //4.创建directory目录对象，指定索引库的位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建输入流对象
        IndexReader indexReader = DirectoryReader.open(dir);

        //6.创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //7.搜索并返回结果,第二个参数是返回多少条数据用于展示，分页使用
        TopDocs topDocs = indexSearcher.search(query.build(), 100);

        //获取查询到的结果集的总数
        System.out.println("totalcount:::::" + topDocs.totalHits);

        //8.获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        //9.遍历结果集
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                //获取查询到的文档唯一标识，文档id，这是lucene创建文档时自动分配的
                int docID = scoreDoc.doc;

                //通过文档id读取文档
                Document doc = indexSearcher.doc(docID);
                System.out.println("---------------------------------");
                //通过域名从文档中获取域值
                System.out.println("===id=="+doc.get("id"));
                System.out.println("===name=="+doc.get("name"));
                System.out.println("===brandName=="+doc.get("brandName"));
            }
        }

        //10.关闭流
        indexReader.close();
    }

}
