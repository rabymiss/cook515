package com.example.cook.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyCookEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String ImageIcon;
    @ColumnInfo
    private String Title;
    @ColumnInfo
    private String AuthorName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageIcon() {
        return ImageIcon;
    }

    public void setImageIcon(String imageIcon) {
        ImageIcon = imageIcon;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    @Override
    public String toString() {
        return "CollectCenterEntity{" +
                "id=" + id +
                ", ImageIcon='" + ImageIcon + '\'' +
                ", Title='" + Title + '\'' +
                ", AuthorName='" + AuthorName + '\'' +
                '}';
    }
}
