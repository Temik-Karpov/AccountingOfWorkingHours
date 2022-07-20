package ru.karpov.AccountingOfWorkingHours.models;

import java.sql.Time;
import java.util.Date;

public class Request {
    int worker_id;
    Date date;
    Time time;
    String text;

    public Date getDate() {
        return date;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public String getText() {
        return text;
    }

    public Time getTime() {
        return time;
    }
}
