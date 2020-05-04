package com.amazter.model;


public class Note {

    String title;
    String body;
    String author;

    public Note(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public String toText() {
        StringBuilder bodyText = new StringBuilder().append(title);
        if(author == null || author.equals("")) {
            bodyText.append(" by ").append(author).append("\n \n");
        }
        bodyText.append(body);
        return bodyText.toString();
    }

}

