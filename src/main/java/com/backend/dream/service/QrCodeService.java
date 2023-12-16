package com.backend.dream.service;

import java.io.IOException;

public interface QrCodeService {
    void generateQrCode(String message);

    void deleteQrCodeOlderThanWeek() throws IOException;

    String getQrCode();
}
