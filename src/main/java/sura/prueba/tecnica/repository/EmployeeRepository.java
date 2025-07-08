package sura.prueba.tecnica.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import sura.prueba.tecnica.model.Employee;
import sura.prueba.tecnica.model.Expense;

public class EmployeeRepository {
    private final String path_csv; 

    public EmployeeRepository(String path_csv){
        this.path_csv = path_csv;
    }

    public Map<Integer, Employee> readDataEmployees(){
        Map<Integer, Employee> employees = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path_csv)))){
            String linea;
            
            br.readLine();
            int row = 1;

            while ((linea = br.readLine()) != null) {
                row++;
                if (linea.trim().isEmpty()) continue; 
                String[] fields = linea.split(",");

                if (fields.length < 4) {
                    throw new IllegalArgumentException("Fila " + row + " Incompleta");
                }

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String date_expense = fields[2];
                Double total = Double.parseDouble(fields[3]);

                Expense newExpense = new Expense(date_expense, total);

                employees.computeIfAbsent(id, e -> new Employee(id, name)).addExpense(newExpense);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo los datos de los empleados: " + e.getMessage());
        }
        return employees;
    }
}
