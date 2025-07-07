package sura.prueba.tecnica.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sura.prueba.tecnica.model.Employee;
import sura.prueba.tecnica.service.ExpensesService;

@RestController
@RequestMapping("api/expenses")
public class ExpensesController {
    
    @Autowired
    private ExpensesService expensesService;

    @GetMapping("/getEmployes")
    public List<Employee> getEmployees(){
        return expensesService.getEmployees();
    }

    @GetMapping("/totalExpenses")
    public Double getTotalExpenses(){
        return expensesService.getTotalExpenses();
    }

    @GetMapping("/totalForEmployee")
    public Map<Integer, Double> getTotalForEmployee(){
        return expensesService.getTotalForEmployee();
    }
}
