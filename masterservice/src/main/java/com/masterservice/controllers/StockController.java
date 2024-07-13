package com.masterservice.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masterservice.mapper.CurrentStockFormatter;
import com.masterservice.models.Category;
import com.masterservice.models.Product;
import com.masterservice.services.CategoryService;
import com.masterservice.services.OrderService;
import com.masterservice.services.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private ProductService productService;

    private OrderService orderService;

    @GetMapping("/get-data")
    public String getCurrentStockReport() {
        List<CurrentStockFormatter> csfList = new ArrayList<>();

        List<Category> categories = categoryService.getCategoryList();

        List<Product> products = productService.getProductList();

        Iterator<Product> itr = products.iterator();

        while (itr.hasNext()) {
            Product product = itr.next();

            CurrentStockFormatter csf = new CurrentStockFormatter();

            csf.setCategoryName(product.getCategory().getCategoryName());
            csf.setProductName(product.getProductName());

            Integer purchaseQty = 0;
            Integer saleQty = 0;
            Integer availableQty = 0;

            // List<OrderDetails> pdList = pds.getPurchaseDetailsByproductId(product.getId());
            // Iterator<PurchaseDetails> i = pdList.iterator();
            // while (i.hasNext()) {
            //     PurchaseDetails pd = i.next();
            //     purchaseQty = purchaseQty + pd.getQuantity();
            // }

            // List<SaleDetails> sdList = sds.getSaleDetailsByproductId(product.getId());
            // Iterator<SaleDetails> i2 = sdList.iterator();
            // while (i2.hasNext()) {
            //     SaleDetails sd = i2.next();
            //     saleQty = saleQty + sd.getQuantity();
            // }
            // availableQty = purchaseQty - saleQty;
            // csf.setPurchaseQuantity(purchaseQty);
            // csf.setSaleQuantity(saleQty);
            // csf.setAvailableQty(availableQty);
            // csfList.add(csf);

        }

        return "currentstock";
    }
}
