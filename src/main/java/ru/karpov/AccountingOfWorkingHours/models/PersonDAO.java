package ru.karpov.AccountingOfWorkingHours.models;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public final class PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/AccountingOfWorkingHours";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "TemikPos12";

    private static Connection connection;

    static {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public PersonDAO(){
    }

    public void save(Person person)
    {
        String table = null;
        switch (person.getPost_())
        {
            case "Administrator":
                table = "Administrator";
                break;
            case "Director":
                table = "Director";
                break;
            case "CoWorker":
                table = "CoWorker";
                break;
        }

        try
        {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO " + table + " (FIO, Post, Management, Department, Email, Phone) VALUES('" + person.getFIO_() + "','" + person.getPost_() +
                    "','" + person.getManagement_() + "','" + person.getDepartment_() + "','" + person.getEmail_() +
                    "','" + person.getPhone_() + "')";
            statement.executeUpdate(SQL);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
