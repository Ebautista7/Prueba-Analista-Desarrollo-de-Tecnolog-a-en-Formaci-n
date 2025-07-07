package sura.prueba.tecnica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import sura.prueba.tecnica.model.Employee;
import sura.prueba.tecnica.model.Expense;

@Service
public class ExpensesService {
    
    private Map<Integer, Employee> employees = new HashMap<>();

    public ExpensesService(){
        readDataEmployees();
    }

    private void readDataEmployees(){
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data.csv")));
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; 
                String[] fields = linea.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String date_expense = fields[2];
                Double total = Double.parseDouble(fields[3]);

                Expense newExpense = new Expense(date_expense, total);

                employees.computeIfAbsent(id, e -> new Employee(id, name)).addExpense(newExpense);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo los datos de los empleados");
        }
    }
}
