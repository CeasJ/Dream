package com.backend.dream.controller;

import com.backend.dream.repository.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class ReportController {
  @Autowired
  private Report report;

  private static final int orderStatus = 4;

  @GetMapping("/report")
  public String getReport(Model model) {
    // Double revenue = report.getRevenue(orderStatus);
    // Double totalOrder = report.getTotalOrder(orderStatus);
    // Integer totalProductHasSold = report.totalProductHasSold(orderStatus);
    // model.addAttribute("revenue", revenue);
    // model.addAttribute("total", totalOrder);
    // model.addAttribute("totalProductHasSold", totalProductHasSold);
    return "/admin/home/report";
  }
}
