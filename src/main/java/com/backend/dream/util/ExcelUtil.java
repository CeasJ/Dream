package com.backend.dream.util;

import com.backend.dream.entity.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static String HEADERPRODUCT[] = {"Id", "Name", "Price", "Image", "Describe", "Create Date", "Active", "Category"};

    public static String SHEET_NAMEPRODUCT = "sheetForProductData";
    public static ByteArrayInputStream dataToExcelProduct(List<Product> productList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMEPRODUCT);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERPRODUCT.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERPRODUCT[i]);
            }

            int rowIndex = 1;
            for (Product p :productList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getName());
                row1.createCell(2).setCellValue(p.getPrice());
                row1.createCell(3).setCellValue(p.getImage());
                row1.createCell(4).setCellValue(p.getDescribe());
                row1.createCell(5).setCellValue(p.getCreateDate());
                row1.createCell(6).setCellValue(p.getActive());
                row1.createCell(7).setCellValue(p.getCategory().getName());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }
    public static String HEADERPRODUCTSIZE[] = {"Id", "Name", "Size", "Price", "Category"};
    public static String SHEET_NAMEPRODUCTSIZE = "sheetForProductSizeData";

    public static ByteArrayInputStream dataToExcelProductSize(List<ProductSize> productSizeList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMEPRODUCTSIZE);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERPRODUCTSIZE.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERPRODUCTSIZE[i]);
            }

            int rowIndex = 1;
            for (ProductSize p :productSizeList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getProduct().getName());
                row1.createCell(2).setCellValue(p.getSize().getName());
                row1.createCell(3).setCellValue(p.getPrice());
                row1.createCell(4).setCellValue(p.getProduct().getCategory().getName());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }

    public static String HEADERDISCOUNT[] = {"Id", "Name", "Number", "Precent", "Active", "Actice Date", "Expired Date", "Category"};
    public static String SHEET_NAMEDISCOUNT = "sheetForDiscountData";

    public static ByteArrayInputStream dataToExcelDiscount(List<Discount> discountList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMEDISCOUNT);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERDISCOUNT.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERDISCOUNT[i]);
            }

            int rowIndex = 1;
            for (Discount p :discountList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getName());
                row1.createCell(2).setCellValue(p.getNumber());
                row1.createCell(3).setCellValue(p.getPercent());
//                row1.createCell(4).setCellValue(p.getActive());
                row1.createCell(5).setCellValue(p.getActiveDate());
                row1.createCell(6).setCellValue(p.getExpiredDate());
                row1.createCell(7).setCellValue(p.getCategory().());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }

    public static String HEADERVOUCHER[] = {"Id", "Name", "Number", "Precent","Condition", "Voucher Status", "Actice Date", "Expired Date","Icon", "Name Account"};
    public static String SHEET_NAMEVOUCHER = "sheetForVoucherData";

    public static ByteArrayInputStream dataToExcelVoucher(List<Voucher> vouchersList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMEVOUCHER);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERVOUCHER.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERVOUCHER[i]);
            }

            int rowIndex = 1;
            for (Voucher p :vouchersList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getName());
                row1.createCell(2).setCellValue(p.getNumber());
                row1.createCell(3).setCellValue(p.getPercent());
                row1.createCell(4).setCellValue(p.getCondition());
                row1.createCell(5).setCellValue(p.getStatus().getName());
                row1.createCell(6).setCellValue(p.getCreateDate());
                row1.createCell(7).setCellValue(p.getExpiredDate());
                row1.createCell(8).setCellValue(p.getIcon());
                row1.createCell(9).setCellValue(p.getAccount().getFullname());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }

    public static String HEADERSTAFF[] = {"Id", "Avatar", "Username","Firstname","Lastname","Fullname","Email","Phone","Address"};
    public static String SHEET_NAMESTAFF = "sheetForStaffData";

    public static ByteArrayInputStream dataToExcelSTAFF(List<Account> accountList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMESTAFF);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERSTAFF.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERSTAFF[i]);
            }

            int rowIndex = 1;
            for (Account p :accountList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getAvatar());
                row1.createCell(2).setCellValue(p.getUsername());
                row1.createCell(3).setCellValue(p.getFirstname());
                row1.createCell(4).setCellValue(p.getLastname());
                row1.createCell(5).setCellValue(p.getFullname());
                row1.createCell(6).setCellValue(p.getEmail());
                row1.createCell(7).setCellValue(p.getPhone());
                row1.createCell(8).setCellValue(p.getAddress());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }

    public static String HEADERAUTHORITY[] = {"Id", "Name Account", "Role"};
    public static String SHEET_NAMEAUTHORITY = "sheetForAuthorityData";

    public static ByteArrayInputStream dataToExcelAuthority(List<Authority> authoritiesList) throws IOException {
        Workbook workbook  = new XSSFWorkbook();

        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        try {
            Sheet sheet = workbook.createSheet(SHEET_NAMEAUTHORITY);
            Row row = sheet.createRow(0);

            for (int i  =0; i< HEADERAUTHORITY.length;i++){

                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERAUTHORITY[i]);
            }

            int rowIndex = 1;
            for (Authority p :authoritiesList){
                Row row1 = sheet.createRow(rowIndex);
                rowIndex++;
                row1.createCell(0).setCellValue(p.getId());
                row1.createCell(1).setCellValue(p.getAccount().getFullname());
                row1.createCell(2).setCellValue(p.getRole().getName());
            }

            workbook.write(byteArrayOutputStream);
            return  new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            workbook.close();
            byteArrayOutputStream.close();
        }
    }
}


