package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    EmployerRepository employers;

    @Autowired
    JobRepository jobs;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("job", new Job());
        model.addAttribute("employers", employers.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors,
                                    @RequestParam (required=false) Integer employerId,
                                    Model model) {
        if (employerId == null) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("jobs", employers.findAll());
        } else {
            Optional<Employer> result = employers.findById(employerId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid employer id :" + employerId);
            } else {
                Employer employer = result.get();
                employer.getJobs().add(newJob);
                jobs.save(newJob);
                model.addAttribute("title", "Jobs that Employer " + employer.getName() + " has:");
                model.addAttribute("jobs", employer.getJobs());
            }
        }
        return "index";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

            return "view";
    }

}
