package ru.karpov.AccountingOfWorkingHours.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karpov.AccountingOfWorkingHours.models.PersonDAO;
import ru.karpov.AccountingOfWorkingHours.models.Request;
import ru.karpov.AccountingOfWorkingHours.models.RequestDAO;

import java.sql.Time;
import java.util.Date;

@Controller
public class WorkController {

    private PersonDAO personDAO;
    private RequestDAO requestDAO;

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
    public String startDay(@RequestParam("date") @DateTimeFormat(pattern="MM/dd/yyyy") Date date,
                           @RequestParam("time") Time time,
                            @RequestParam("text") String text)
    {
        requestDAO.addRequestToTable(new Request());
        return "redirect:/work-page";
    }
}
