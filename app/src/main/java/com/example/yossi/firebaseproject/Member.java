package com.example.yossi.firebaseproject;

public class Member {

    String memberId;
    int questionsCounter = 0;
    int answersCounter = 0;


    public Member() {
    }

    public Member(String memberId) {
        this.memberId = memberId;
    }
}
