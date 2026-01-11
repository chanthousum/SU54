package com.setec.su54_pos_api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.setec.su54_pos_api.dto.request.ProductRequestDTO;
import com.setec.su54_pos_api.exceptions.MyResourceNotFoundException;
import com.setec.su54_pos_api.models.Product;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.setec.su54_pos_api.configurations.FileUploadUtil;
import com.setec.su54_pos_api.models.Category;
import com.setec.su54_pos_api.repositorys.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class ProductService {
    @Value("${file.upload-dir}")
    private String folderUploads;
    @Autowired
    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }
    public String getBaseUrl(){
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();
        return baseUrl;
    }
    @Async
    public List<Product> getAllProducts() {
        var products = this.productRepository.findAll();
        List<Product> products1 = new ArrayList<Product>();
        for (Product product : products) {
            Product product1 = new Product();
             product1.setId(product.getId());
             product1.setProductName(product.getProductName());
             product1.setBarcode(product.getBarcode());
             product1.setSellPrice(product.getSellPrice());
             product1.setUnitInStock(product.getUnitInStock());
             product1.setPhoto(this.getBaseUrl()+"/"+this.folderUploads+product.getPhoto());
             product1.setCategory(product.getCategory());
             products1.add(product1);
        }
        return products1;


    }

    @Transactional
    @Async
    public Product createProduct(String name, long barcode, double sellPrice, int unitInStock, int categoryId,
            MultipartFile file) throws IOException {
        Product product = new Product();
        product.setProductName(name);
        product.setBarcode(barcode);
        product.setSellPrice(sellPrice);
        product.setUnitInStock(unitInStock);
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            FileUploadUtil.saveFile(folderUploads, fileName, file);
            product.setPhoto(fileName);
        } else {
            product.setPhoto("");
        }
        return  this.productRepository.save(product);

    }

    @Transactional
    @Async
    public Product updateById(int id, String name, long barcode, double sellPrice, int unitInStock, int categoryId,
            MultipartFile file) throws IOException {
        var existingProduct = this.productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new MyResourceNotFoundException("Product not found with id: " + id);
        }

        if (productRepository.existsByProductNameAndIdNot(name, id)) {
            throw new MyResourceNotFoundException("Product name already exists");
        }

        if (productRepository.existsByBarcodeAndIdNot(barcode, id)) {
            throw new MyResourceNotFoundException("Product barcode already exists");
        }
        existingProduct.setProductName(name);
        existingProduct.setBarcode(barcode);
        existingProduct.setSellPrice(sellPrice);
        existingProduct.setUnitInStock(unitInStock);
        Category category = new Category();
        category.setId(categoryId);
        existingProduct.setCategory(category);
        if (existingProduct.getPhoto() != null && !existingProduct.getPhoto().isEmpty()) {
            if (file != null && !file.isEmpty()) {
                FileUploadUtil.removePhoto(folderUploads, existingProduct.getPhoto());
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                FileUploadUtil.saveFile(folderUploads, fileName, file);
                existingProduct.setPhoto(fileName);
            }
        } else {
            if (file != null && !file.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                FileUploadUtil.saveFile(folderUploads, fileName, file);
                existingProduct.setPhoto(fileName);
            }
        }
        return this.productRepository.save(existingProduct);
    }

    @Transactional
    @Async
    public void deleteById(int id) throws IOException {
        var existingProduct = this.productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new MyResourceNotFoundException("Product not found with id: " + id);

        }
        if (!existingProduct.getPhoto().isEmpty()) {
            FileUploadUtil.removePhoto(folderUploads, existingProduct.getPhoto());
        }
        this.productRepository.deleteById(id);
    }

    public ProductRequestDTO createProduct(ProductRequestDTO productRequestDTO, MultipartFile file) throws IOException {
        Product product = new Product();
        product.setProductName(productRequestDTO.getProductName());
        product.setBarcode(productRequestDTO.getBarcode());
        product.setSellPrice(productRequestDTO.getSellPrice());
        product.setUnitInStock(productRequestDTO.getQuantity());
        Category category = new Category();
        category.setId(productRequestDTO.getCategoryId());
        product.setCategory(category);
        if (file != null && !file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            FileUploadUtil.saveFile(folderUploads, fileName, file);
            product.setPhoto(fileName);
        } else {
            product.setPhoto("");
        }
        this.productRepository.save(product);
        return  productRequestDTO;
    }
}
