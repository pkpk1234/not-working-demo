package com.example.spring.data.jpa.soft.delete.sample.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkpk1234 on 2017/5/28.
 */
@Entity(name = "CustomerGroup")
@Table(name = "customer_group")
@Data
@NoArgsConstructor
//@Loader(namedQuery = "findGroup")
//@NamedQuery(name = "findGroup", query = "select t from CustomerGroup t where t.id=?1 and t.deleted = false ")
@Where(clause = "deleted = false")
@SQLDelete(sql = "update customer_group set customer_group.deleted = true where customer_group.id=?1")
public class CustomerGroup extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "customerGroup", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Customer> customers = new ArrayList<>();

    public CustomerGroup(String name) {
        this.name = name;
    }
}
