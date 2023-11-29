package com.backend.dream.rest;

import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.dto.VoucherStatusDTO;
import com.backend.dream.entity.Voucher;
import com.backend.dream.service.VoucherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // Get voucher for user
    @GetMapping("/applicable")
    public List<VoucherDTO> getApplicableVouchers(){
        String user = request.getRemoteUser();
        return voucherService.getApplicableVouchers(user);
    }

    @GetMapping("/all")
    public List<VoucherDTO> getAllVouchers(){
        return voucherService.getAllVouchers();
    }

    @GetMapping("/voucherstatus/all")
    public List<VoucherStatusDTO> getAllVoucherStatus() {
        return voucherService.getAllVoucherStatus();
    }

    @GetMapping("/filterByStatus/{statusId}")
    public List<VoucherDTO> getVouchersByStatus(@PathVariable Long statusId) {
        return voucherService.getVouchersByStatusId(statusId);
    }

    // Searching features
    @GetMapping("/search")
    public List<VoucherDTO> searchVouchersByName(@RequestParam String name) {
        return voucherService.searchVouchersByName(name);
    }

    @PostMapping()
    public Voucher createVoucher(@RequestBody VoucherDTO voucherDTO){
        return voucherService.createVoucher(voucherDTO);
    }
    @PutMapping("{id}")
    public VoucherDTO updateVoucher(@RequestBody VoucherDTO voucherDTO,@PathVariable Long id){
        return voucherService.updateVoucher(voucherDTO,id);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long voucherId) {
        try {
            voucherService.delete(voucherId);
            return ResponseEntity.ok("Voucher has been deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting voucher: " + e.getMessage());
        }
    }




}
