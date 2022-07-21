package ru.karpov.AccountingOfWorkingHours.models;

public final class Worker {
    int id_;
    String FIO_;
    String post_;
    String management_;
    String department_;
    String email_;
    String phone_;
    String password_;

    public Worker(final String FIO, final String post, final String management, final String department,
                  final String email, final String phone, final String password)
    {
        this.department_ = department;
        this.email_ = email;
        this.FIO_ = FIO;
        this.management_ = management;
        this.phone_ = phone;
        this.post_ = post;
        this.password_ = password;
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

    public String getPassword_() {
        return password_;
    }

    public void setId_(final int id_) {
        this.id_ = id_;
    }
}
