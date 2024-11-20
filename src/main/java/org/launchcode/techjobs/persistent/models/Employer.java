package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Employer extends AbstractEntity {
    @NotBlank(message = "Location must not be blank.")
    @Size(min=2, max=50, message = "Location name must be between 2 and 50 characters.")
    private String location;

    @OneToMany (mappedBy="employer")
    private final List<Job> jobs = new ArrayList<>();

    public Employer(String location) {
        setLocation(location);
    }

    public Employer() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
