package com.nik.covidtracker.controllers;

import com.nik.covidtracker.models.LocationStats;
import com.nik.covidtracker.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = covidDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        String parsedTotalCases = covidDataService.addCommasToNumericString(totalReportedCases);
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        String parsedNewCases = covidDataService.addCommasToNumericString(totalNewCases);
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", parsedTotalCases);
        model.addAttribute("totalNewCases", parsedNewCases);

        return "home";

    }
}
