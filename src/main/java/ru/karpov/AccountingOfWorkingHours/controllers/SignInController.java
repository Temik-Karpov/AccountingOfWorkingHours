package ru.karpov.AccountingOfWorkingHours.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.AccountingOfWorkingHours.models.MailSender;
import ru.karpov.AccountingOfWorkingHours.models.PersonDAO;
import ru.karpov.AccountingOfWorkingHours.models.Worker;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Objects;
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
    public String passwordForm(@RequestParam("FIO") @NotNull String FIO, @RequestParam("post") String post,
                               @RequestParam("management") String management, @RequestParam("department") String department,
                               @RequestParam("email") @NotNull String email, @RequestParam("number") @NotNull String number) throws SQLException {
        final String password = UUID.randomUUID().toString();
        Worker worker = new Worker(FIO, post, management, department, email, number, password);
        personDAO.save(worker);
        mailSender.send(worker.getEmail_(), "Password", "Your password is " + password);
        return "password-form";
    }

    @GetMapping("/PasswordForm")
    public String passwordForm()
    {
        return "password-form";
    }

    @PostMapping("/LogInForm")
    public String logInFrom(@RequestParam("email") String email,
                            @RequestParam("password") String password, Model model) throws SQLException {
        if(personDAO.checkPassword(email, password))
        {
            switch(Objects.requireNonNull(personDAO.identifyPerson(email)))
            {
                case "Administrator":
                    model.addAttribute("notConfirmedWorkers", personDAO.getNotConfirmedWorkers());
                    return "work-page-admin";
                case "Director":
                case "Co-worker":
                    return "work-page";
            }
        }
        return "redirect:/password-form";
    }
}
