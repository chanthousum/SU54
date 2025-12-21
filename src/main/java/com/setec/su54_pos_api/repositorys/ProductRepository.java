package com.setec.su54_pos_api.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setec.su54_pos_api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductById(int id);

}
