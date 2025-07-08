package sura.prueba.tecnica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
