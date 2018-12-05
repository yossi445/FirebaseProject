package com.example.yossi.firebaseproject;

public class Question {

    String QuestionId;
    String title;
    String body;
    String userId;


    public Question()
    {

    }

    public Question(String questionId, String title, String body, String userId) {
        QuestionId = questionId;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
