package com.example.project_lv1_mobile.model;

import java.io.Serializable;
import java.util.HashMap;

public class Member implements Serializable {
    private String idMember, fullName, userName, passWord, gender, email;
    private int rank, status;

    public Member() {
    }

    public Member(String idMember, String fullName, String userName, String passWord, String gender, String email, int rank, int status) {
        this.idMember = idMember;
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.gender = gender;
        this.email = email;
        this.rank = rank;
        this.status = status;
    }

    public String getIdMember() {
        return idMember;
    }

    public Member setIdMember(String idMember) {
        this.idMember = idMember;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Member setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Member setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public Member setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Member setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    public int getRank() {
        return rank;
    }

    public Member setRank(int rank) {
        this.rank = rank;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Member setStatus(int status) {
        this.status = status;
        return this;
    }

    public HashMap<String, Object> objectMember() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("idMember", this.idMember);
        data.put("fullName", this.fullName);
        data.put("userName", this.userName);
        data.put("passWord", this.passWord);
        data.put("gender", this.gender);
        data.put("email", this.email);
        data.put("rank", this.rank);
        data.put("status", this.status);

        return data;
    }
}
