package models;

import java.util.Date;

public class Blog {

    public final String title;
    public final String author;
    public final Date publishDate;
    public final String content;

    public Blog(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.publishDate = new Date();
        this.content = content;
    }

}