package com.masterservice.mapper;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseReport {

    private String invoiceNo; 
	private LocalDate invoiceDate; 
    private String billNo; 
    private LocalDate billDate;
	private String supplier; 
	private Double amount;
    
    private List<PurchaseReportDetails> purchaseDetailsList;
	
}
