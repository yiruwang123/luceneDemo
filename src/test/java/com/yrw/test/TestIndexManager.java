package com.yrw.test;

import com.yrw.dao.SkuDao;
import com.yrw.dao.SkuDaoImpl;
import com.yrw.pojo.Sku;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引库维护
 */
public class TestIndexManager {


    //创建索引库
    @Test
    public void creatIndexTest() throws IOException {
        //1.采集数据
        SkuDao skuDao = new SkuDaoImpl();
        List<Sku> skuList = skuDao.querySkuList();

        List<Document> docList = new ArrayList<>();

        for (Sku sku : skuList) {
            //2.创建文档对象
            Document document = new Document();

            //创建域对象并放入文档对象中
            //是否存储要看页面是否展示
            /*
                是否分词：否，分词无意义
                是否索引：是，可能要根据id查询
                是否存储：是
             */
            document.add(new StringField("id",sku.getId(), Field.Store.YES));

            /*
                是否分词：是
                是否索引：是
                是否存储：是
             */
            document.add(new TextField("name",sku.getName(), Field.Store.YES));

            /*
                是否分词：是(lucene底层算法规定，如果根据价格范围查询，必须分词)
                是否索引：是，需要根据价格索引
                是否存储：是
             */
            document.add(new IntPoint("price",sku.getPrice()));
            document.add(new StoredField("price",sku.getPrice()));  //用于存储

            /*
                是否分词：否，不查询
                是否索引：否，不需要根据图片路径查
                是否存储：是，需要展示
             */
            document.add(new StoredField("image",sku.getImage()));

            /*
                是否分词：否(看情况吧，是否是专有名称)
                是否索引：是
                是否存储：是
             */
            document.add(new StringField("categoryName",sku.getCategoryName(), Field.Store.YES));

            /*
                是否分词：否，原因同上
                是否索引：是
                是否存储：是，需要展示
             */
            document.add(new StringField("brandName",sku.getBrandName(), Field.Store.YES));

            //将文档对象放入文档集合中
            docList.add(document);
        }

        //3.创建分词器,StandardAnalyzer对英文分词效果好，对中文是单字分词
        Analyzer analyzer = new StandardAnalyzer();

        //4.创建Directory对象，目录对象表示索引位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //6.创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        //7.写入文档到索引库
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }

        //8.释放资源
        indexWriter.close();
    }

    @Test
    public void updateIndexTest() throws IOException {

        //2.创建文档对象
        Document document = new Document();

        document.add(new StringField("id","100000003145", Field.Store.YES));

        document.add(new TextField("name","xxxxx", Field.Store.YES));

        document.add(new IntPoint("price",123));
        document.add(new StoredField("price",123));  //用于存储

        document.add(new StoredField("image","xxx.jpg"));

        document.add(new StringField("categoryName","手机", Field.Store.YES));

        document.add(new StringField("brandName","华为", Field.Store.YES));



        //3.创建分词器,StandardAnalyzer对英文分词效果好，对中文是单字分词
        Analyzer analyzer = new StandardAnalyzer();

        //4.创建Directory对象，目录对象表示索引位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //6.创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        //7.修改,第一个参数：修改条件，第二个参数：修改成的内容
        //修改的内容会添加到文档最后
        indexWriter.updateDocument(new Term("id","100000003145"),document);

        //8.释放资源
        indexWriter.close();
    }

    /*
      测试条件删除
     */
    @Test
    public void deleteIndexTest() throws Exception {
        //3.创建分词器,StandardAnalyzer对英文分词效果好，对中文是单字分词
        Analyzer analyzer = new StandardAnalyzer();

        //4.创建Directory对象，目录对象表示索引位置
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //6.创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        //测试条件删除
        indexWriter.deleteDocuments(new Term("id","100000003145"));

        //测试删除所有
        //indexWriter.deleteAll();

        //8.释放资源
        indexWriter.close();
    }

    @Test
    public void creatIndexTest2() throws IOException {
        //1.采集数据
        SkuDao skuDao = new SkuDaoImpl();
        List<Sku> skuList = skuDao.querySkuList();

        List<Document> docList = new ArrayList<>();

        for (Sku sku : skuList) {
            Document document = new Document();
            document.add(new StringField("id",sku.getId(), Field.Store.YES));
            document.add(new TextField("name",sku.getName(), Field.Store.YES));
            document.add(new IntPoint("price",sku.getPrice()));
            document.add(new StoredField("price",sku.getPrice()));  //用于存储
            document.add(new StoredField("image",sku.getImage()));
            document.add(new StringField("categoryName",sku.getCategoryName(), Field.Store.YES));
            document.add(new StringField("brandName",sku.getBrandName(), Field.Store.YES));
            docList.add(document);
        }

        //3.创建分词器,StandardAnalyzer对英文分词效果好，对中文是单字分词
        Analyzer analyzer = new StandardAnalyzer();

        //4.创建Directory对象，目录对象表示索引位置，MMapDirectory更好一点，FSDirectory是操作硬盘，没有内存
        //参与，MMapDirectory是使用内存的，有缓存
        //MMapDirectory第一次查询后会把结果缓存到内存，第二次就会非常快，FSDirectory没有
        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));
        //Directory dir = MMapDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        //5.创建IndexWriterConfig对象，这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //设置内存中多少给文档向磁盘中写一次数据
        //设置的数值过大会过度消耗内存，但是可以提升速度
        //config.setMaxBufferedDocs(100000);

        //6.创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);

        //设置多少个文档合并成一个段文件，数值越大索引速度越快，搜索速度越慢，越小索引速度越慢，搜索速度越快
        indexWriter.forceMerge(1000);

        //7.写入文档到索引库
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }

        //8.释放资源
        indexWriter.close();
    }
}
