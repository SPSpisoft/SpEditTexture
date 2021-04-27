package com.spisoft.spedittexture;

public class TextureItem {
    private int Code;
    private String Id;
    private String Title;
    private int Icon;

    public TextureItem(int code, String id, String title, int icon){
        this.Code = code;
        this.Id = id;
        this.Title = title;
        this.Icon = icon;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }
}
