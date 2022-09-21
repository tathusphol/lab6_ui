package com.example.lab6_ui.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@Document("Wizard")
public class Wizards implements Serializable {
    private List<Wizard> model = new ArrayList<>();

    public Wizards(){

    }

    public Wizards(List<Wizard> model) {
        this.model = model;
    }
    public List<Wizard> getModel() {
        return model;
    }

    public void setModel(List<Wizard> model) {
        this.model = model;
    }
}
