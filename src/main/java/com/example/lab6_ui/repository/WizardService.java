package com.example.lab6_ui.repository;

import com.example.lab6_ui.pojo.Wizard;
import com.example.lab6_ui.pojo.Wizards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }

    public List<Wizard> returnAllWizard(){
        return wizardRepository.findAll();
    }
    public Wizard getWizard(String ID){
        return wizardRepository.findByPerson(ID);
    }
    public Wizard createWizard(Wizard wizard){
        return wizardRepository.save(wizard);
    }
    public boolean deleteWizard(Wizard wizard) {
        try { wizardRepository.delete(wizard);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public int getCountWizard(){
        return (int)wizardRepository.count();
    }
    public Wizard updateWizard(Wizard wizard) {
        return wizardRepository.save(wizard);
    }
}
