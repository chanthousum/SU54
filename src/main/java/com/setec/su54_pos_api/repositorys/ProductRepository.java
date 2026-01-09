package com.setec.su54_pos_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setec.su54_pos_api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByProductName(String name);
    boolean existsByBarcode(long barcode);
    // Check if a product with the same name exists excluding the given id
    boolean existsByProductNameAndIdNot(String name, Integer id);
    Product findProductById(int id);
    boolean existsByBarcodeAndIdNot(long barcode, int id);
}
