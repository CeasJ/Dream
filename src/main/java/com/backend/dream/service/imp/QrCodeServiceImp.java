package com.backend.dream.service.imp;

import com.backend.dream.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class QrCodeServiceImp implements QrCodeService {
    private String path = "D:\\Dream\\dream\\src\\main\\resources\\static\\img\\qrcode";
    private static final String charset = "UTF-8";
    private static final String strDateFormat = "dd-MM-YYYY";
    private String qrCodeFileName;
    private void processQRcode(String data, String path, String charset, int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    private String prepareOutputFileName() {
        Date date = new Date();

        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);

        StringBuilder sb = new StringBuilder();
        sb.append(path).append("\\").append("QRCode-").append(formattedDate).append(".png");
        qrCodeFileName = sb.toString();
        return sb.toString();
    }

    @Override
    public void generateQrCode(String message) {
        try {
            processQRcode(message, prepareOutputFileName(), charset, 400, 400);
            deleteQrCodeOlderThanWeek();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteQrCodeOlderThanWeek() throws IOException{
        Path folderPath = Paths.get(path);

        Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Date creationDate = new Date(attrs.creationTime().toMillis());
                if (isOlderThanOneMonth(creationDate)) {
                    Files.delete(file);
                    System.out.println("Đã xóa file: " + file);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    @Override
    public String getQrCode() {
        return qrCodeFileName;
    }

    public boolean isOlderThanOneMonth(Date date){
        Date currentDate = new Date();

        long diffInMillies = Math.abs(currentDate.getTime() - date.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff > 7;
    }
}
