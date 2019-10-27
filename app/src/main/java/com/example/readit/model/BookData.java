package com.example.readit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class BookData {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int RomId;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "publisher")
    private String publisher;
    @ColumnInfo(name = "publishedDate")
    private String publishedDate;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "previewLink")
    private String previewLink;
    @ColumnInfo(name = "poster_path")
    private String thumbnail;

    @ColumnInfo(name = "book_id")
    private String id;



    public BookData(String id , String title, String author, String publisher, String publishedDate, String description, String previewLink, String thumbnail) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.previewLink = previewLink;
        this.thumbnail = thumbnail;
    }
    @Ignore
    public BookData(int romId, String title, String author, String publisher, String publishedDate, String description, String previewLink, String thumbnail) {
        RomId = romId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.previewLink = previewLink;
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setRomId(int romId) {
        RomId = romId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getRomId() {
        return RomId;
    }

    public String getTitle() {
        return title;
    }

    public String getauthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setauthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
