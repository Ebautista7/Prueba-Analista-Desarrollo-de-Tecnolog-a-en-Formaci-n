package sura.prueba.tecnica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Employee> getEmployees(){
        return new ArrayList<Employee>(employees.values());
    }

    public Double getTotalExpenses(){
        double total = 0;
        for (Employee employee : employees.values()) {
            for (Expense expense : employee.getExpenses()) {
                total += expense.getTotal();
            }
        }
        return total;
    }

    public Map<Integer, Double> getTotalForEmployee(){
        Map<Integer, Double> expensesMap = new HashMap<>();
        double total = 0;
        for (Employee employee : employees.values()) {
            for (Expense expense : employee.getExpenses()) {
                total += expense.getTotal();
                expensesMap.put(employee.getId(), total);
            }
            total = 0;
        }
        return expensesMap;
    }
}
