package com.example.spring.data.jpa.soft.delete.sample.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

/**
 * Created by pkpk1234 on 2017/5/27.
 */

@MappedSuperclass
@Data
public class BaseEntity {
    private boolean deleted;
}
