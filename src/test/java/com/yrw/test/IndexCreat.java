package com.yrw.test;

import com.yrw.dao.PaperList;
import com.yrw.pojo.paper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IndexCreat {

    @Test
    public void creatIndex() throws Exception {
        PaperList paperList = new PaperList();
        List<paper> pList = paperList.queryPaperList();

        List<Document> docList = new ArrayList<>();

        for (paper p : pList) {
            Document document = new Document();
            document.add(new StoredField("filename", p.getFilename()));
            document.add(new TextField("author",p.getAuthor(), Field.Store.YES));
            document.add(new TextField("title",p.getTitle(), Field.Store.YES));
            document.add(new TextField("keywords",p.getKeywords(), Field.Store.YES));
            document.add(new TextField("reference", p.getReference(), Field.Store.YES));
            document.add(new TextField("fulltext",p.getFulltext(), Field.Store.YES));
            document.add(new TextField("summary", p.getSummary(), Field.Store.YES));
            document.add(new LongPoint("date", p.getDate()));
            document.add(new StoredField("date", p.getDate()));
//            System.out.println(p.getFulltext());

            //将文档对象放入文档集合中
            docList.add(document);
        }
        Analyzer analyzer = new StandardAnalyzer();

        Directory dir = FSDirectory.open(Paths.get("D:\\Users\\14036\\Desktop\\lucene\\dir"));

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(dir, config);

        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }

        indexWriter.close();
    }

    @Test
    public void indexTest() throws Exception {
        PaperList paperList = new PaperList();
        List<paper> pList = paperList.queryPaperList();

        for (paper p : pList) {
            System.out.println(p.getAuthor());
            System.out.println(p.getDate());
        }

    }


}
