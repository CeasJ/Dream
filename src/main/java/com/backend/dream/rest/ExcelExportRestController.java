package com.backend.dream.rest;

import com.backend.dream.dto.ProductDTO;
import com.backend.dream.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/export")
public class ExcelExportRestController {

    @Autowired
    private ProductService productService;
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ProductDTO> data = productService.findAll(); // Lấy dữ liệu từ service của bạn

        // Tạo một workbook và một sheet mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Number", "ID", "Image", "Name", "Price", "Describe", "Category", "Date", "Active"};

        // Điền header vào row đầu tiên của sheet
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu vào từng row
        int rowNum = 1;
        for (ProductDTO item : data) {
            Row row = sheet.createRow(rowNum++);
            // Điền dữ liệu vào từng cell
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(item.getId());
            // Điền dữ liệu hình ảnh có thể cần xử lý khác
            row.createCell(2).setCellValue("/img/gallery/" + item.getImage());
            row.createCell(3).setCellValue(item.getName());
            row.createCell(4).setCellValue(item.getPrice());
            row.createCell(5).setCellValue(item.getDescribe());
            row.createCell(6).setCellValue(item.getName_category());
            // Xử lý ngày tháng
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            Cell dateCell = row.createCell(7);
            dateCell.setCellValue(item.getCreateDate());
            dateCell.setCellStyle(cellStyle);

            row.createCell(8).setCellValue(item.getActive());
        }

        // Tạo file Excel và trả về client
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
