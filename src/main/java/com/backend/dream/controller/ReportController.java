package com.backend.dream.controller;

import com.backend.dream.repository.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReportController {
    @Autowired
    private Report report;

    private static final int orderStatus = 4;
    @GetMapping("/report")
    public String getReport(Model model){
        Double revenue = report.getRevenue(orderStatus);
        model.addAttribute("revenue",revenue);

        Double totalOrder = report.getTotalOrder(orderStatus);
        model.addAttribute("total",totalOrder);

        Integer totalProductHasSold = report.totalProductHasSold(orderStatus);
        model.addAttribute("totalProductHasSold",totalProductHasSold);

        List<Object[]> coutProductHasSold = report.countProductSold(orderStatus);
        model.addAttribute("countProductHasSold",coutProductHasSold);


        List<Object[]> totalRevenue = report.getTotalRevenue(orderStatus);
        model.addAttribute("totalRevenue",totalRevenue);
      return "/admin/report";
    }
}
