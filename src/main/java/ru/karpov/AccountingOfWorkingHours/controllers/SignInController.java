package ru.karpov.AccountingOfWorkingHours.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.AccountingOfWorkingHours.models.MailSender;
import ru.karpov.AccountingOfWorkingHours.models.PersonDAO;
import ru.karpov.AccountingOfWorkingHours.models.Person;

import java.sql.SQLException;
import java.util.UUID;

@Controller
public class SignInController {

    private MailSender mailSender;

    private PersonDAO personDAO;

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/SignInForm")
    public String signIn()
    {
        return "sign-in-form";
    }

    @PostMapping("/PasswordForm")
    public String passwordForm(@RequestParam("FIO") String FIO, @RequestParam("post") String post,
                               @RequestParam("management") String management, @RequestParam("department") String department,
                               @RequestParam("email") String email, @RequestParam("number") String number) throws SQLException {
        String password = UUID.randomUUID().toString();
        Person person = new Person(FIO, post, management, department, email, number, password);
        personDAO.save(person);
        mailSender.send(person.getEmail_(), "Password", "Your password is " + password);
        return "password-form";
    }

    @PostMapping("/LogInForm")
    public String logInFrom(@RequestParam("email") String email,
                            @RequestParam("password") String password) throws SQLException {
        if(personDAO.checkPassword(email, password))
        {
            return "work-page";
        }
        return "password-form";
    }
}
