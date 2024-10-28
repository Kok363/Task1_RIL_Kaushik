package controller;

import entity.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.CollegeService;

import java.util.List;

@RestController
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/colleges")
    public List<College> getColleges(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "name", required = false) String name) {

        if (name != null && country != null) {
            return collegeService.getCollegesByNameAndCountry(name, country);
        } else if (name != null) {
            return collegeService.getCollegesByName(name);
        } else if (country != null) {
            return collegeService.getCollegesByCountry(country);
        } else {
            return collegeService.fetchCollegesFromAPI();  // Return all colleges if no filters
        }
    }
}
