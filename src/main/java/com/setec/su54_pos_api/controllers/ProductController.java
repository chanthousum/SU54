package com.setec.su54_pos_api.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.setec.su54_pos_api.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.setec.su54_pos_api.models.Product;
import com.setec.su54_pos_api.services.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    final private ProductService productService;
    Logger logger =
            LoggerFactory.getLogger(ProductController.class);
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        logger.info("Get all products");
        var products = this.productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestParam("name") String name, @RequestParam("barcode") long barcode,
            @RequestParam("sellPrice") double sellPrice, @RequestParam("unitInStock") int unitInStock,
            @RequestParam("categoryId") int categoryId,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.productService.createProduct(name, barcode, sellPrice, unitInStock, categoryId, file);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateById(@PathVariable int id, @RequestParam("name") String name,
            @RequestParam("barcode") long barcode,
            @RequestParam("sellPrice") double sellPrice, @RequestParam("unitInStock") int unitInStock,
            @RequestParam("categoryId") int categoryId,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        this.productService.updateById(id, name, barcode, sellPrice, unitInStock, categoryId, file);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) throws Exception {
        this.productService.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product deleted successfully");
        return ResponseEntity.ok(response);
    }
}
