package sura.prueba.tecnica.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalEmployeeDTO {
    private int id;
    private String name;
    private double total;
}
