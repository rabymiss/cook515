package com.example.cook.apientity;

public class PersonMessage {
    @Override
    public String toString() {
        return "PersonMessage{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", icon='" + icon + '\'' +
                ", burthday='" + burthday + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", addressp='" + addressp + '\'' +
                ", workin='" + workin + '\'' +
                ", showyou='" + showyou + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBurthday() {
        return burthday;
    }

    public void setBurthday(String burthday) {
        this.burthday = burthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddressp() {
        return addressp;
    }

    public void setAddressp(String addressp) {
        this.addressp = addressp;
    }

    public String getWorkin() {
        return workin;
    }

    public void setWorkin(String workin) {
        this.workin = workin;
    }

    public String getShowyou() {
        return showyou;
    }

    public void setShowyou(String showyou) {
        this.showyou = showyou;
    }

    private int id;

    private  String username;
    private String        nickname;
    private String icon;
    private String burthday;
    private String email;
    private String phonenumber;
    private String addressp;
    private String workin;
    private String showyou;
}
