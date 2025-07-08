package sura.prueba.tecnica.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public List<Map<String, Object>> monthlyReport(){
        SimpleDateFormat dateEntry = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM/yyyy");

        List<Map<String, Object>> reporList = new ArrayList<>();

        for (Employee employee : employees.values()) {
            Map<String, Double> expenseForMonth = new HashMap<>();
            for (Expense expense : employee.getExpenses()) {
                String month;
                try {
                    month = dateMonth.format(dateEntry.parse(expense.getDate_expense()));
                } catch (Exception e) {
                    continue;
                }
                expenseForMonth.put(month, expenseForMonth.getOrDefault(month, 0.0) + expense.getTotal());
            }
            for (Map.Entry<String, Double> entry : expenseForMonth.entrySet()) {
                String month = entry.getKey();
                double totalMonth = entry.getValue();
                double totalWithIVA = (totalMonth * 0.19) + totalMonth;
                String assumeer = totalWithIVA > 1000000 ? "SURA": "Empleado";
                Map<String, Object> data = new HashMap<>();
                data.put("id", employee.getId());
                data.put("nombre", employee.getName());
                data.put("month", month);
                data.put("totalMonth", totalMonth);
                data.put("totalWithIVA", totalWithIVA);
                data.put("asumido por", assumeer);
                reporList.add(data);
            }
        }
        return reporList;
    }

}
