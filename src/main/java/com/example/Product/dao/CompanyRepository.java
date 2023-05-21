package com.example.Product.dao;

import com.example.Product.entity.Company;
import com.example.Product.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);

    @Override
    void flush();
}

