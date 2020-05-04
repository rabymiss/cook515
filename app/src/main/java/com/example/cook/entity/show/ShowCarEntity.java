package com.example.cook.entity.show;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShowCarEntity {
    @Override
    public String toString() {
        return "ShowCarEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userIcon='" + userIcon + '\'' +
                ", username='" + username + '\'' +
                ", cookName='" + cookName + '\'' +
                ", heartNumber='" + heartNumber + '\'' +
                ", cookType='" + cookType + '\'' +
                ", seeNumber='" + seeNumber + '\'' +
                ", date='" + date + '\'' +
                ", endImage='" + endImage + '\'' +
                ", step='" + step + '\'' +
                ", hitSmall='" + hitSmall + '\'' +
                ", image='" + image + '\'' +
                ", t1='" + t1 + '\'' +
                ", t2='" + t2 + '\'' +
                ", t3='" + t3 + '\'' +
                ", t4='" + t4 + '\'' +
                ", t5='" + t5 + '\'' +
                '}';
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    //............................
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String userIcon;
    @ColumnInfo
    private String username;
    @ColumnInfo
    private String cookName;
    @ColumnInfo
    private String heartNumber;
    @ColumnInfo
    private String cookType;
    @ColumnInfo
    private String seeNumber;
    @ColumnInfo
    private String date;



    @ColumnInfo
    private String endImage;

    @ColumnInfo
    private String step;

    @ColumnInfo
    private String hitSmall;
    @ColumnInfo
    private String image;
    @ColumnInfo
    private String t1;
    @ColumnInfo
    private String t2;
    @ColumnInfo
    private String t3;
    @ColumnInfo
    private String t4;
    @ColumnInfo
    private String t5;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getHeartNumber() {
        return heartNumber;
    }

    public void setHeartNumber(String heartNumber) {
        this.heartNumber = heartNumber;
    }

    public String getCookType() {
        return cookType;
    }

    public void setCookType(String cookType) {
        this.cookType = cookType;
    }

    public String getSeeNumber() {
        return seeNumber;
    }

    public void setSeeNumber(String seeNumber) {
        this.seeNumber = seeNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndImage() {
        return endImage;
    }

    public void setEndImage(String endImage) {
        this.endImage = endImage;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getHitSmall() {
        return hitSmall;
    }

    public void setHitSmall(String hitSmall) {
        this.hitSmall = hitSmall;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getT1() {
        return t1;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public String getT2() {
        return t2;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public String getT3() {
        return t3;
    }

    public void setT3(String t3) {
        this.t3 = t3;
    }

    public String getT4() {
        return t4;
    }

    public void setT4(String t4) {
        this.t4 = t4;
    }

    public String getT5() {
        return t5;
    }

    public void setT5(String t5) {
        this.t5 = t5;
    }
}
