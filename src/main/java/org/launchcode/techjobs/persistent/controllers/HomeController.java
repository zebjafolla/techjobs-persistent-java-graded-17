package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.launchcode.techjobs.persistent.models.dto.JobSkillDTO;
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
    EmployerRepository employerRepository;

    @Autowired
    JobRepository jobs;

    @Autowired
    SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobs.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("job", new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors,
                                    @RequestParam (required=false) Integer employerId,
                                    @RequestParam (required=false) List<Integer> skills,
                                    Model model) {
        if (errors.hasErrors() || employerId == null || skills == null) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());
            if (employerId == null) {
                model.addAttribute("employerError", "Please select an employer");
            }
            if (skills == null) {
                model.addAttribute("skillsError", "Please select a skill");
            }
            return "add";

        }
        Optional<Employer> employerResult = employerRepository.findById(employerId);
        if (employerResult.isEmpty()) {
            model.addAttribute("title", "Add Jobs");
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());
            model.addAttribute("employerError", "Invalid employer ID.");
            return "add";
        }
        List<Skill> skillList = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setEmployer(employerResult.get());
        newJob.setSkills(skillList);
        jobs.save(newJob);
        return "redirect:/";
    }
//        else {
//            if (result.isEmpty()){
//                model.addAttribute("title", "Invalid employer id :" + employerId);
//            } else {
//                Employer employer = result.get();
//                newJob.setEmployer(employer);
//                newJob.setSkills(skillList);
//                employer.getJobs().add(newJob);
//                jobs.save(newJob);
//                model.addAttribute("title", "Jobs that Employer " + employer.getName() + " has:");
//                model.addAttribute("jobs", jobs.findAll());
//                model.addAttribute("skills", skills);
//            }
//        }
//        return "index";
//    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable Integer jobId) {
        Optional<Job> optionalJob = jobs.findById(jobId);

        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            // Handle the case where the job is not found
            model.addAttribute("errorMessage", "Job not found");
            return "error"; // Redirect to an error page if needed
        }
    }
//        Job job = optionalJob.get();
//        Skill skill = optionalSkill.get();
//        model.addAttribute("job", job);
//        model.addAttribute("skill", skill);
//        return "view/{jobId}"; // The Thymeleaf template to display th
//    }

//    public String displayAddSkillForm(@RequestParam Integer jobId, Model model){
//        Optional <Job> result = jobs.findById(jobId);
//        Job job = result.get();
//        model.addAttribute("title", "Add Skill to " + job.getName());
//        model.addAttribute("skills", skillsRepository.findAll());
//        model.addAttribute("job", job);
//
//        return "view";
//    }




}
