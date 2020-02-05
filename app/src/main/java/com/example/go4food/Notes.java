package com.example.go4food;

public class Notes {
    private String title;
    private String discription;
    public Notes(String title,String discription)
    {
        this.title=title;
        this.discription=discription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
