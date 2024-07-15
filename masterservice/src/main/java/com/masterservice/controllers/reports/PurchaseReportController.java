package com.masterservice.controllers.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masterservice.mapper.PurchaseReport;
import com.masterservice.mapper.PurchaseReportDetails;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class PurchaseReportController {
    
    
    /**
     * * ===========================================================================
     * * ======================== Module : PurchaseReportController ================
     * * ======================== Created By : Umesh Kumar =========================
     * * ======================== Created On : 15-07-2024 ==========================
     * * ===========================================================================
     * * | Code Status : On
     */

     @GetMapping("/get-purchase-report")
     public ResponseEntity<?> getPurchaseReport(){
        
        PurchaseReport pr = new PurchaseReport();
        pr.setBillDate(LocalDate.now());
        pr.setInvoiceNo("INV324234");
        pr.setBillNo("BILL87234");
        pr.setInvoiceDate(LocalDate.now());
        pr.setAmount(900000D);
        pr.setSupplier("Umesh Kumar");

        List<PurchaseReportDetails> prdList = new ArrayList<>();

        PurchaseReportDetails prd = new PurchaseReportDetails();
        prd.setCategoryName("Laptop");
        prd.setProductName("HP");
        prd.setQuantity(5);
        prd.setRate(50000D);
        prd.setTotalAmount(250000D);

        PurchaseReportDetails prd2 = new PurchaseReportDetails();
        prd2.setCategoryName("Television");
        prd2.setProductName("Sony");
        prd2.setQuantity(5);
        prd2.setRate(5000D);
        prd2.setTotalAmount(250000D);

        prdList.add(prd);
        prdList.add(prd2);

        pr.setPurchaseDetailsList(prdList);
        
        Map<Object, Object> response = new HashMap<>();
        response.put("data", pr);
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
     }



}
