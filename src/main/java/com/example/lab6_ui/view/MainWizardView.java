package com.example.lab6_ui.view;

import com.example.lab6_ui.pojo.Wizard;
import com.example.lab6_ui.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "mainPage.it")
public class MainWizardView extends FormLayout {
    Wizard person;
    private TextField full_name;
    private NumberField Dollar;
    private ComboBox position, school, house;
    private RadioButtonGroup<String> gender;
    private Button previous, next, create, update, delete;
    private int page = -1;
    public MainWizardView(){
        HorizontalLayout boxButton = new HorizontalLayout();
        VerticalLayout container = new VerticalLayout();
        full_name = new TextField();
        Dollar = new NumberField();
        position = new ComboBox<>();
        school = new ComboBox<>();
        house = new ComboBox<>();
        gender = new RadioButtonGroup<>();
        previous = new Button("<<");
        next = new Button(">>");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        full_name.setPlaceholder("Fullname");
        gender.setLabel("Gender : ");
        gender.setItems("Male", "Female");
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        Div dollarPrefix = new Div();
        dollarPrefix.setText("$");
        Dollar.setPrefixComponent(dollarPrefix);
        school.setPlaceholder("School");
        house.setPlaceholder("House");
        boxButton.add(previous, create, update, delete, next);
        container.add(full_name, gender, position, Dollar, school, house, boxButton);
        add(container);

        create.addClickListener(event ->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("fullname", full_name.getValue());
            formData.add("gender", gender.getValue());
            formData.add("school", school.getValue().toString());
            formData.add("position", position.getValue().toString());
            formData.add("dollar", Dollar.getValue().toString());
            formData.add("house", house.getValue().toString());
            String response = WebClient.create().post().uri("http://localhost:8080/addWizard").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(String.class).block();
            System.out.println(response);
            this.page += 1;
            Notification.show("Wizard has been Created", 5000, Notification.Position.BOTTOM_START);
        });
        previous.addClickListener(event -> {
            Wizards wizards = WebClient.create().get().uri("http://localhost:8080/wizards").retrieve().bodyToMono(Wizards.class).block();
            if(this.page > 0){
                this.page -= 1;
            }
            this.person = wizards.getModel().get(this.page);
            full_name.setValue(person.getName());
            if(person.getSex().equals("m")){
                gender.setValue("Male");
            } else if (person.getSex().equals("f")) {
                gender.setValue("Female");
            }
            school.setValue(person.getSchool());
            house.setValue(person.getHouse());
            position.setValue(person.getPosition().toUpperCase());
            Dollar.setValue((double)person.getMoney());
        });
        next.addClickListener(event -> {
            Wizards wizards = WebClient.create().get().uri("http://localhost:8080/wizards").retrieve().bodyToMono(Wizards.class).block();
            if(this.page < wizards.getModel().size()-1){
                this.page += 1;
            }
            this.person = wizards.getModel().get(this.page);
            full_name.setValue(person.getName());
            if(person.getSex().equals("m")){
                gender.setValue("Male");
            } else if (person.getSex().equals("f")) {
                gender.setValue("Female");
            }
            school.setValue(person.getSchool());
            house.setValue(person.getHouse());
            position.setValue(person.getPosition().toUpperCase());
            Dollar.setValue((double)person.getMoney());
        });
        delete.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", person.get_id());
            boolean response = WebClient.create().post().uri("http://localhost:8080/deleteWizard").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(boolean.class).block();
            Wizards wizards = WebClient.create().get().uri("http://localhost:8080/wizards").retrieve().bodyToMono(Wizards.class).block();
            this.person = wizards.getModel().get(wizards.getModel().size()-1);
            full_name.setValue(person.getName());
            if(person.getSex().equals("m")){
                gender.setValue("Male");
            } else if (person.getSex().equals("f")) {
                gender.setValue("Female");
            }
            school.setValue(person.getSchool());
            house.setValue(person.getHouse());
            position.setValue(person.getPosition().toUpperCase());
            Dollar.setValue((double)person.getMoney());
            this.page -= 1;
            Notification.show("Wizard has been Removed", 5000, Notification.Position.BOTTOM_START);
        });
        update.addClickListener(event ->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", person.get_id());
            formData.add("fullname", full_name.getValue());
            formData.add("gender", gender.getValue());
            formData.add("school", school.getValue().toString());
            formData.add("position", position.getValue().toString());
            formData.add("dollar", Dollar.getValue().toString());
            formData.add("house", house.getValue().toString());
            String response = WebClient.create().post().uri("http://localhost:8080/updateWizard").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(String.class).block();
            System.out.println(response);
            Notification.show("Wizard has been Updated", 5000, Notification.Position.BOTTOM_START);
        });
    }
}
