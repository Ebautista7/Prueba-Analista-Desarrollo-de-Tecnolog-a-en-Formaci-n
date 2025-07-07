package sura.prueba.tecnica.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private List<Expense> expenses;

    public Employee(int id, String name){
        this.id = id;
        this.name = name;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense){
        this.expenses.add(expense);
    }
}
