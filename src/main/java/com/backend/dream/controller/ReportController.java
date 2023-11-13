package com.backend.dream.controller;

import com.backend.dream.repository.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ReportController {
  @Autowired
  private Report report;

  private static final int orderStatus = 4;

  @GetMapping("/report")
  public String getReport(Model model) {
    getStatistic(model);

    Date endDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH,-7);
    Date startDate = calendar.getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String formattedStartDate = dateFormat.format(startDate);
    String formattedEndDate = dateFormat.format(endDate);



    List<Object[]> totalRevenue = report.getTotalRevenue(orderStatus);
    model.addAttribute("totalRevenue",totalRevenue);

    return "/admin/home/report";
  }

  @GetMapping("/getByDate")
  public String getRevenue(Model model, @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
    getStatistic(model);

    List<Object[]> getRevenueByDateAndStatus = report.getTotalRevenueByDateAndStatus(orderStatus,startDate,endDate);
    model.addAttribute("totalRevenue", getRevenueByDateAndStatus);

    return "/admin/home/report";
  }

  @GetMapping("/week")
  public String revenueWeek(Model model){
    getStatistic(model);

    Date endDate = new Date();

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH,-7);

    Date startDate = calendar.getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    List<Object[]> getRevenueByDateAndStatus = report.getTotalRevenueByDateAndStatus(orderStatus,startDate,endDate);
    model.addAttribute("totalRevenue", getRevenueByDateAndStatus);

    return "/admin/home/report";
  }
  @GetMapping("/month")
  public String revenueMonth(Model model){
    getStatistic(model);

    Date endDate = new Date();

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH,-30);

    Date startDate = calendar.getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    List<Object[]> getRevenueByDateAndStatus = report.getTotalRevenueByDateAndStatus(orderStatus,startDate,endDate);
    model.addAttribute("totalRevenue", getRevenueByDateAndStatus);

    return "/admin/home/report";
  }
  private void getStatistic(Model model){
    Double revenue = report.getRevenue(orderStatus);
    model.addAttribute("revenue",revenue);

    Double totalOrder = report.getTotalOrder(orderStatus);
    model.addAttribute("total",totalOrder);

    Integer totalProductHasSold = report.totalProductHasSold(orderStatus);
    model.addAttribute("totalProductHasSold",totalProductHasSold);

    List<Object[]> coutProductHasSold = report.countProductSold(orderStatus);
    model.addAttribute("countProductHasSold",coutProductHasSold);

    List<Object[]> getProductHasSoldByCategory = report.getProductHasSoldByCategory(orderStatus);
    model.addAttribute("productHasSoldByCategory",getProductHasSoldByCategory);
  }

}
