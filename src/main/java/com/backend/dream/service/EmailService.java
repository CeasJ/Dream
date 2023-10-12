//package com.backend.dream.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    VerificationTokenService verificationTokenService;
//
//    public void sendWelcomeEmail(String to, String fullname) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setSubject("Welcome to TimeZone");
//        msg.setText("Welcome " + fullname + " to TimeZone");
//        msg.setText("Welcome " + fullname + " to TimeZone");
//        javaMailSender.send(msg);
//    }
//
//    public void sendEmail(String to, String token, String fullname) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setSubject("Reset Password");
//        msg.setText("Hi " + fullname + ",\n\n"
//                + "We have received a request to reset the password for your account. \n\n"
//                + "Your authentication code is: " + token + "\n\n"
//                + "If you did not request a password reset, please ignore this email.\n\n"
//                + "Regards,\n"
//                + "TimeZone");
//        javaMailSender.send(msg);
//
//    }
//
//    public void sendPaymentSuccess(String to, String token, String fullName, String orderID) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setSubject("Thanh toán thành công");
//        msg.setText("Chào " + fullName + ",\n\n"
//                + "Chúng tôi đã xác nhận bạn đã thanh toán thành công cho đơn hàng " + orderID + "\n\n "
//                + "Mã xác thực của bạn là: " + token
//                + " vui lòng giữ lại thông tin này để xác nhận với nhân viên của chúng tôi \n\n"
//                + "Trân trọng,\n"
//                + "TimeZone");
//        javaMailSender.send(msg);
//    }
//
//    public void sendPaymentStatus(String to, String token, String orderID) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(to);
//        msg.setSubject("Thanh toán thành công");
//        msg.setText("Thông báo " + ",\n\n"
//                + "Đã có đơn hàng thanh toán thành công mã đơn hàng là:" + orderID + "\n\n"
//                + "Mã xác thực là: " + token + "\n\n"
//                + "Yêu cầu kiểm tra lại thông tin đơn hàng và mã xác thực đơn hàng.\n\n"
//                + "Trân trọng,\n"
//                + "TimeZone");
//        javaMailSender.send(msg);
//    }
//
//}
