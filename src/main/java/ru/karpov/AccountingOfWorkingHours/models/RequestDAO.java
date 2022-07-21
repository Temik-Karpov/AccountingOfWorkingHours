package ru.karpov.AccountingOfWorkingHours.models;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class RequestDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/AccountingOfWorkingHours";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "TemikPos12";
    private ResultSet rs = null;

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

    void RequestDAO()
    {

    }

    public void addRequestToTable(final Request request)
    {
        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO work_accounting " +
                    "VALUES(?,?,?,?)");
            preparedStatement.setInt(1, request.getWorker_id());
            preparedStatement.setDate(2, (Date) request.getDate());
            preparedStatement.setTime(3, request.getTime());
            preparedStatement.setString(4, request.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
