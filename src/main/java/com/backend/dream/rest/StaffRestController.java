package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/staff")
public class StaffRestController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/admin")
    public List<Account> getAccounts(@RequestParam("admin") Optional<Boolean> admin) {
        if (admin.orElse(false)) {
            return accountService.getStaff();
        } else {
            return accountService.findALL();
        }
    }
    @PostMapping("/add")
    public Account createStaff(@RequestBody AccountDTO accountDTO, Model model) {
        String username = accountDTO.getUsername();
        return accountService.isUsernameExists(username) ? null : accountService.createStaff(accountDTO);
    }

    @PutMapping("/update/{id}")
    public Account updateStaff(@RequestBody Account staffToUpdate, @PathVariable("id") Long id) {
        return accountService.updateStaff(staffToUpdate);
    }

    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-staffs.xlsx";
        ByteArrayInputStream inputStream = accountService.getdataStaff();
        InputStreamResource response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
