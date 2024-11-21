package org.launchcode.techjobs.persistent.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Job extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name="employer_id")
    private Employer employer;

    @ManyToMany
    private List<Skill> skills = new ArrayList<>();




    // Initialize the id and value fields.
    public Job(String aName, Employer anEmployer, List<Skill> theSkills) {
        super.setName(aName);
        this.employer = anEmployer;
        this.skills = theSkills;
    }

    public Job() {
    }

    // Getters and setters.

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}
