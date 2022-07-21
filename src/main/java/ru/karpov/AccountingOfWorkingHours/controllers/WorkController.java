package ru.karpov.AccountingOfWorkingHours.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.AccountingOfWorkingHours.models.PersonDAO;
import ru.karpov.AccountingOfWorkingHours.models.RequestDAO;

import java.sql.Time;
import java.util.Date;

@Controller
public class WorkController {

    private PersonDAO personDAO;
    private RequestDAO requestDAO;
    private boolean isStart = false;

    @Autowired
    public void setRequestDAO(final RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    @Autowired
    public void setPersonDAO(final PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @PostMapping("/confirmWorker")
    public String confirmWorker(@RequestParam("idWorker") int idWorker)
    {
        personDAO.confirmWorker(idWorker);
        return "work-page-admin";
    }

    @PostMapping("/StartDay")
    public String startDay(@RequestParam("date") /*@DateTimeFormat(pattern="dd.MM.yyyy")*/ final String date,
                           @RequestParam("time")/* @DateTimeFormat(pattern="HH:mm")*/ final String time,
                            @RequestParam("text") final String text,
                           Model model)
    {
        if (isStart) {
            model.addAttribute("start", 1);
            isStart = false;
        } else {
            model.addAttribute("start", 0);
            isStart = true;
        }
        return "work-page";
    }
}
