package ru.karpov.AccountingOfWorkingHours.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.AccountingOfWorkingHours.models.PersonDAO;
import ru.karpov.AccountingOfWorkingHours.models.Worker;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class SignInController {

    private PersonDAO personDAO;

    @Autowired
    public void setPersonDAO(final PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/SignInForm")
    public String signIn()
    {
        return "sign-in-form";
    }

    @PostMapping("/PasswordForm")
    public String passwordForm(@RequestParam("FIO") @NotNull final String FIO, @RequestParam("post") final String post,
                               @RequestParam("management") final String management,
                               @RequestParam("department") final String department,
                               @RequestParam("email") @NotNull final String email,
                               @RequestParam("number") @NotNull final String number) throws SQLException {
        final String password = UUID.randomUUID().toString();
        final Worker worker = new Worker(FIO, post, management, department, email, number, password);
        personDAO.save(worker);
        return "password-form";
    }

    @GetMapping("/PasswordForm")
    public String passwordForm()
    {
        return "password-form";
    }

    @PostMapping("/LogInForm")
    public String logInFrom(@RequestParam("email") final String email,
                            @RequestParam("password") final String password, final Model model) throws SQLException {
        if(personDAO.checkPassword(email, password))
        {
            switch(Objects.requireNonNull(personDAO.identifyPerson(email)))
            {
                case "Administrator":
                    model.addAttribute("notConfirmedWorkers", personDAO.getNotConfirmedWorkers());
                    return "work-page-admin";
                case "Director":
                case "Co-worker":
                    model.addAttribute("start", 1);
                    return "work-page";
            }
        }
        return "redirect:/password-form";
    }
}
