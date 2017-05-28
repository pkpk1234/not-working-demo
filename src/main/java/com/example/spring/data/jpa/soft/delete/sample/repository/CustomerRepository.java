package com.example.spring.data.jpa.soft.delete.sample.repository;

import com.example.spring.data.jpa.soft.delete.sample.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pkpk1234 on 2017/5/28.
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @Query(value = "truncate table customer",nativeQuery = true)
    void truncate();

    Customer findCustomersByName(String name);

    @Query(value="select * from customer t ORDER BY t.id",nativeQuery = true)
    List<Customer> getAllIncludeDeleted();


}
