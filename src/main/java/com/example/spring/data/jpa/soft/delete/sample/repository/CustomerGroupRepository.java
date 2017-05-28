package com.example.spring.data.jpa.soft.delete.sample.repository;

import com.example.spring.data.jpa.soft.delete.sample.model.CustomerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by pkpk1234 on 2017/5/28.
 */

@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup,Long> {
    @Modifying
    @Query(value = "truncate table customer_group",nativeQuery = true)
    void truncate();
}
