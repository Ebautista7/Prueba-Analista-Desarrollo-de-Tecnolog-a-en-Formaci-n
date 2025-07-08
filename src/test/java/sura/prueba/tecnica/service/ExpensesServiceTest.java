package sura.prueba.tecnica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sura.prueba.tecnica.model.Employee;
import sura.prueba.tecnica.model.Expense;

public class ExpensesServiceTest {
    private ExpensesService expensesService;

    @BeforeEach
    void setUp(){
        this.expensesService = new ExpensesService();
    }

    //Prueba para verificar el total de gastos
    @Test
    void testTotalCorrect(){
        double totalCorrect = 2000000 + 1000000 + 400000 + 900000 + 1100000 + 5100000 + 4000000 + 899999 + 
                                59999 + 500000 + 500000 + 1100000 + 1100000;
        double totalObtained = expensesService.getTotalExpenses();
        assertEquals(totalCorrect, totalObtained, 0.001);
    }

    //Prueba para buscar a Adam con sus gastos en febrero
    //Los gastos de Adam en febrero suman 500000 por lo cual el debe asumir los gastos
    @Test
    void testReportExpensesForEmploye(){
        List<Map<String, Object>> reportMonthly = expensesService.monthlyReport();
        assertFalse(reportMonthly.isEmpty());

        Map<String, Object> reportAdam = reportMonthly.stream().filter(m -> ((int) m.get("id")) == 1 
                                                                            && ((String) m.get("month")).equals("febrero/2021"))
                                                                            .findFirst()
                                                                            .orElse(null);
        assertNotNull(reportAdam);
        assertEquals("Adam", reportAdam.get("name"));
        assertEquals("Empleado", reportAdam.get("asumido por"));

    }

    //En este test se va a evaluar los gastos de Chelsea que es la que mas cerca al limite de 1M
    //Los datos de ella en enero son 900000 y 59999 con el calculo con su IVA da de 1142398.81 por lo cual asume la empresa
    @Test
    void testAssumeerCorrectInLimitCase(){
        List<Map<String, Object>> reportExpenses = expensesService.monthlyReport();

        Map<String, Object> reportChelsea = reportExpenses.stream().filter(m -> ((int) m.get("id")) == 3 
                                                                            && ((String) m.get("month")).equals("enero/2021"))
                                                                            .findFirst()
                                                                            .orElse(null);
        assertNotNull(reportChelsea);
        assertEquals("Chelsea", reportChelsea.get("name"));
        assertEquals("SURA", reportChelsea.get("asumido por"));
    }

    //En este test se va a evaluar los gastos de Bolton que esta cerca al limite de 1M sin exceder ese dato sin IVA
    //Los datos de el en febrero son 500000 y 400000 con el calculo da que lo asume el empleador
    @Test
    void testAssumeerCorrectLowInLimitCase(){
        List<Map<String, Object>> reportExpenses = expensesService.monthlyReport();

        Map<String, Object> reportBolton = reportExpenses.stream().filter(m -> ((int) m.get("id")) == 2 
                                                                            && ((String) m.get("month")).equals("enero/2021"))
                                                                            .findFirst()
                                                                            .orElse(null);
        assertNotNull(reportBolton);
        assertEquals("Bolton", reportBolton.get("name"));
        assertEquals("SURA", reportBolton.get("asumido por"));
    }

    //Verificar que todos los empleados de la tabla esten 
    @Test
    void testTotalNumberEmployees(){
        int totalEmployeesCorrect = 6;
        int totalEmployeesObteined = expensesService.getEmployees().size();
        assertEquals(totalEmployeesCorrect, totalEmployeesObteined);
    }

    // Verificar que todos los gastos de la tabla esten
    @Test
    void testTotalNumberExpenses(){
        int totalExpensesCorrect = 13;
        int totalExpensesObteined = 0;
        List<Employee> employees = expensesService.getEmployees();
        for (Employee employee : employees) {
            totalExpensesObteined += employee.getExpenses().size();
        }
        assertEquals(totalExpensesCorrect, totalExpensesObteined);
    }

    // Verificar que el total de adam de gastos sea correcto 3500000
    @Test
    void testTotalExpensesForEmployee(){
        int totalExpensesCorrect = 3500000;
        int totalExpensesObteined = 0;
        List<Employee> employees = expensesService.getEmployees();
        for (Employee employee : employees) {
            if(employee.getName().compareToIgnoreCase("Adam") == 0){
                for (Expense expense : employee.getExpenses()) {
                    totalExpensesObteined += expense.getTotal();
                }
            }
            
        }
        assertEquals(totalExpensesCorrect, totalExpensesObteined);
    }


}
