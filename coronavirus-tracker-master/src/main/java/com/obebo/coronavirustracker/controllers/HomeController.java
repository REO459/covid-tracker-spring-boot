package com.obebo.coronavirustracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.obebo.coronavirustracker.models.LocationStats;
import com.obebo.coronavirustracker.services.CoronaVirusDataService;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
        Date lastUpdated = coronaVirusDataService.getLastUpdated();
        String dataSourceUrl = coronaVirusDataService.getDataSourceUrl();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("lastUpdated", lastUpdated);
        model.addAttribute("dataSourceUrl", dataSourceUrl);

        return "home";
    }
}
