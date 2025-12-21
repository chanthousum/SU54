package com.setec.su54_pos_api.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.setec.su54_pos_api.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.setec.su54_pos_api.configurations.FileUploadUtil;
import com.setec.su54_pos_api.models.Category;
import com.setec.su54_pos_api.repositorys.ProductRepository;

import jakarta.transaction.Transactional;

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

    @Async
    public List<Product> getAllProducts() {
        var products = this.productRepository.findAll();
        return products;
    }

    @Transactional
    @Async
    public void createProduct(String name, long barcode, double sellPrice, int unitInStock, int categoryId,
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
        this.productRepository.save(product);

    }

    @Transactional
    @Async
    public void updateById(int id, String name, long barcode, double sellPrice, int unitInStock, int categoryId,
            MultipartFile file) throws IOException {
        var existingProduct = this.productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new com.setec.su14_23_api.exceptions.MyResourceNotFoundException("Product not found with id: " + id);

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
        this.productRepository.save(existingProduct);
    }

    @Transactional
    @Async
    public void deleteById(int id) throws IOException {
        var existingProduct = this.productRepository.findProductById(id);
        if (existingProduct == null) {
            throw new com.setec.su14_23_api.exceptions.MyResourceNotFoundException("Product not found with id: " + id);

        }
        if (!existingProduct.getPhoto().isEmpty()) {
            FileUploadUtil.removePhoto(folderUploads, existingProduct.getPhoto());
        }
        this.productRepository.deleteById(id);
    }
}
