package ru.karpov.AccountingOfWorkingHours.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public final class PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/AccountingOfWorkingHours";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "TemikPos12";
    private ResultSet rs = null;
    private MailSender mailSender;

    @Autowired
    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }

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

    public void save(Worker worker)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers " +
                    "WHERE email = ?");
            rs = null;
            preparedStatement.setString(1, worker.getEmail_());
            rs = preparedStatement.executeQuery();
            preparedStatement = connection.prepareStatement("INSERT INTO workers (FIO, Post, Department, Management," +
                    " Email, Phone, Password, isConfirm) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, worker.getFIO_());
            preparedStatement.setString(2, worker.getPost_());
            preparedStatement.setString(3, worker.getDepartment_());
            preparedStatement.setString(4, worker.getManagement_());
            preparedStatement.setString(5, worker.getEmail_());
            preparedStatement.setString(6, worker.getPhone_());
            preparedStatement.setString(7, worker.getPassword_());
            preparedStatement.setBoolean(8, false);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean checkPassword(final String email, final String password) throws SQLException {
        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT password, isconfirm " +
                    "FROM workers WHERE email = ?");
            rs = null;
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if(rs != null)
        {
            while(rs.next()) {
                final String passwordDb = rs.getString(1);
                final boolean confirm = rs.getBoolean(2);
                if (passwordDb.equals(password) && confirm) {
                    return true;
                } else {
                    return false;   //Валидация: неверный пароль
                }
            }
        }
       return false;    //Валидация: незарегестрированы
    }

    public String identifyPerson(final String email) throws SQLException {
        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT post FROM workers " +
                    "WHERE email = ?");
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        if(rs != null)
        {
            while(rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }

    public ArrayList<Worker> getNotConfirmedWorkers() throws SQLException {
        final ArrayList<Worker> notConfirmedWorkers = new ArrayList<>();
        try
        {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers " +
                    "WHERE isconfirm = ?");
            preparedStatement.setBoolean(1, false);
            rs = preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        while(rs.next())
        {
            final Worker worker = new Worker(rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8));
            worker.setId_(rs.getInt(1));
            notConfirmedWorkers.add(worker);
        }
        return notConfirmedWorkers;
    }

    public void confirmWorker(final int idWorker)
    {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE workers SET " +
                    "isconfirm = ? WHERE worker_id = ?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, idWorker);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT email, password FROM workers " +
                    "WHERE worker_id = ?");
            preparedStatement.setInt(1, idWorker);
            rs = preparedStatement.executeQuery();
            while(rs.next())
            {
                mailSender.send(rs.getString(1), "Password", "Your password is " + rs.getString(2));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
