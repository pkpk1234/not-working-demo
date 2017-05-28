package com.example.spring.data.jpa.soft.delete.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by pkpk1234 on 2017/5/27.
 */

@Entity(name = "Customer")
@Table(name = "customer")
@Data
@NoArgsConstructor
@Loader(namedQuery = "findCustomers")
@NamedQuery(name = "findCustomer", query = "select t from Customer t where t.id=?1 and t.deleted = false ")
@Where(clause = "deleted = false")
@SQLDelete(sql = "update customer set customer.deleted = true where customer.id=?1")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="group_id",referencedColumnName = "id")
    private CustomerGroup customerGroup;

    public Customer(String name) {
        this.name = name;
    }

}
