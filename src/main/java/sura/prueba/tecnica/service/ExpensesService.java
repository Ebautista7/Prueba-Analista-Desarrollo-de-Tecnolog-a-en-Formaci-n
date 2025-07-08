package sura.prueba.tecnica.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sura.prueba.tecnica.model.Employee;
import sura.prueba.tecnica.model.Expense;

@Service
public class ExpensesService {
    
    private Map<Integer, Employee> employees = new HashMap<>();
    private static final double PERCENT_IVA = 0.19;

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
        return new ArrayList<Employee>(employees.values().stream().sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toList()));
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
        DateTimeFormatter dateEntry = DateTimeFormatter.ofPattern("d/M/yy");
        Locale locale = Locale.forLanguageTag("es-CO");

        List<Map<String, Object>> reporList = new ArrayList<>();

        for (Employee employee : employees.values()) {
            Map<String, Double> expenseForMonth = new HashMap<>();
            for (Expense expense : employee.getExpenses()) {
                String month;
                try {
                    LocalDate dateMonth = LocalDate.parse(expense.getDate_expense(), dateEntry);
                    String monthText = dateMonth.getMonth().getDisplayName(TextStyle.FULL, locale);
                    month = monthText + "/" + dateMonth.getYear();
                } catch (Exception e) {
                    continue;
                }
                expenseForMonth.put(month, expenseForMonth.getOrDefault(month, 0.0) + expense.getTotal());
            }
            for (Map.Entry<String, Double> entry : expenseForMonth.entrySet()) {
                reporList.add(datForMontlyReport(entry, expenseForMonth, employee));
            }
        }
        
        reporList.sort(Comparator.comparing(data -> (String) data.get("nombre"),Comparator.nullsLast(String::compareToIgnoreCase)));

        return reporList;
    }

    private Map<String, Object> datForMontlyReport(Map.Entry<String, Double> entry, Map<String, Double> expenseForMonth, Employee employee){
        String month = entry.getKey();
        double totalMonth = entry.getValue();
        double totalWithIVA = (totalMonth * PERCENT_IVA) + totalMonth;
        String assumeer = totalWithIVA > 1000000 ? "SURA": "Empleado";
        Map<String, Object> data = new HashMap<>();
        data.put("id", employee.getId());
        data.put("nombre", employee.getName());
        data.put("month", month);
        data.put("totalMonth", totalMonth);
        data.put("totalWithIVA", totalWithIVA);
        data.put("asumido por", assumeer);
        return data;
    }

}
