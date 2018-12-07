package com.example.yossi.firebaseproject;

public class Answer {

    String answerId;
    String content;
    int likes = 0;
    String questionId;
    String userId;

    public Answer()
    {

    }

    public Answer(String answerId, String content, String questionId, String userId) {
        this.answerId = answerId;
        this.content = content;
        this.questionId = questionId;
        this.userId = userId;
    }
}
