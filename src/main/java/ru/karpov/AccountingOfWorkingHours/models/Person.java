package ru.karpov.AccountingOfWorkingHours.models;

public final class Person {
    int id_;
    String FIO_;
    String post_;
    String management_;
    String department_;
    String email_;
    String phone_;

    public Person(String FIO, String post, String management, String department, String email, String phone)
    {
        this.department_ =department;
        this.email_ = email;
        this.FIO_ = FIO;
        this.management_ = management;
        this.phone_ = phone;
        this.post_ = post;
    }

    public String getEmail_() {
        return email_;
    }

    public int getId_() {
        return id_;
    }

    public String getDepartment_() {
        return department_;
    }

    public String getFIO_() {
        return FIO_;
    }

    public String getManagement_() {
        return management_;
    }

    public String getPhone_() {
        return phone_;
    }

    public String getPost_() {
        return post_;
    }
}
