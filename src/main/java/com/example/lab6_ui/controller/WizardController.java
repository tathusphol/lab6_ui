package com.example.lab6_ui.controller;

import com.example.lab6_ui.pojo.Wizard;
import com.example.lab6_ui.pojo.Wizards;
import com.example.lab6_ui.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;
    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizard(){
        Wizards wizards = new Wizards(wizardService.returnAllWizard());
        return  ResponseEntity.ok(wizards);
    }
//
    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> createWizard(@RequestBody MultiValueMap <String, String> information) {
        Map<String, String> wizard = information.toSingleValueMap();
        String sex = "";
        String name = wizard.get("fullname");
        String gender = wizard.get("gender");
        String school = wizard.get("school");
        String position = wizard.get("position").toLowerCase();
        String house = wizard.get("house");
        double dollar = Double.parseDouble(wizard.get("dollar"));
        if(gender.equals("Male")){
            sex = "m";
        }
        else{
            sex = "f";
        }
        Wizard n = wizardService.createWizard(new Wizard(null, sex, name, school, house, (int)dollar, position));
        return ResponseEntity.ok(n);
    }


    @RequestMapping(value ="/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody MultiValueMap <String, String> information) {
        Map<String, String> wizard = information.toSingleValueMap();
        String sex = "";
        String id = wizard.get("id");
        String name = wizard.get("fullname");
        String gender = wizard.get("gender");
        String school = wizard.get("school");
        String position = wizard.get("position").toLowerCase();
        String house = wizard.get("house");
        double dollar = Double.parseDouble(wizard.get("dollar"));
        if(gender.equals("Male")){
            sex = "m";
        }
        else{
            sex = "f";
        }
        Wizard person = wizardService.getWizard(id);
        if(person != null){
            Wizard n = wizardService.updateWizard(new Wizard(id, sex, name, school, house, (int)dollar, position));
            return true;
        }
        return false;
    }

    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public ResponseEntity<?> deleteBook(@RequestBody MultiValueMap <String, String> information) {
        Map<String, String> wizard = information.toSingleValueMap();
        Wizard delwizard = wizardService.getWizard(wizard.get("id"));
        System.out.println(delwizard);
        boolean status = wizardService.deleteWizard(delwizard);
        return ResponseEntity.ok(status);
    }
}
