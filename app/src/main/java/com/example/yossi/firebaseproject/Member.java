package com.example.yossi.firebaseproject;

public class Member {

    String memberId;
    int questionsCounter = 0;
    int answersCounter = 0;
    String profileImageUrl = "";

    public Member()
    {

    }

    public Member(String memberId) {
        this.memberId = memberId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
