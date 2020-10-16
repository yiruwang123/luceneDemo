package com.yrw.pojo;


public class paper {
    //文件名
    private String filename;
    //作者
    private String author;
    //标题
    private String title;
    //关键词
    private String keywords;
    //引文
    private String reference;
    //全文
    private String fulltext;
    //摘要
    private String summary;
    //日期
    private Long date;

    public paper(){}

    public paper(String filename, String author, String title, String keywords, String reference, String fulltext, String summary, Long date) {
        this.filename = filename;
        this.author = author;
        this.title = title;
        this.keywords = keywords;
        this.reference = reference;
        this.fulltext = fulltext;
        this.summary = summary;
        this.date = date;
    }

    @Override
    public String toString() {
        return "paper{" +
                "filename='" + filename + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", reference='" + reference + '\'' +
                ", fulltext='" + fulltext + '\'' +
                ", summary='" + summary + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }


}
