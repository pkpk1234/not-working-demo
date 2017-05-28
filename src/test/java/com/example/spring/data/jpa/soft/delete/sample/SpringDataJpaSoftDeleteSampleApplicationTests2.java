package com.example.spring.data.jpa.soft.delete.sample;

import com.example.spring.data.jpa.soft.delete.sample.model.Customer;
import com.example.spring.data.jpa.soft.delete.sample.model.CustomerGroup;
import com.example.spring.data.jpa.soft.delete.sample.repository.CustomerGroupRepository;
import com.example.spring.data.jpa.soft.delete.sample.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class SpringDataJpaSoftDeleteSampleApplicationTests2 {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    private Customer customer;
    private Customer deletedCustomer;
    private CustomerGroup customerGroup;

    @Before
    public void contextLoads() {
        log.info("init data");
        //this.customerRepository.truncate();
        //this.customerGroupRepository.truncate();
        CustomerGroup group1 = new CustomerGroup("Group1");
        this.customerGroup = this.testEntityManager.persist(group1);

        Customer customer1 = new Customer("customer1");
        customer1.setCustomerGroup(this.customerGroup);
        log.info("-------------{}",this.testEntityManager.persist(customer1));

        Customer customer2 = new Customer("customer2");
        customer2.setCustomerGroup(this.customerGroup);
        this.testEntityManager.persist(customer2);

        Customer customer3 = new Customer("deletedCustomer");
        customer3.setCustomerGroup(this.customerGroup);
        this.deletedCustomer = this.testEntityManager.persist(customer3);
        this.customerRepository.delete(this.deletedCustomer);

    }

    @Test
    public void testLoadAll() {
        List<CustomerGroup> results = this.customerGroupRepository.findAll();
        log.info(">>>>>>>>>>>>>>>>{}",results.get(0));
        List<Customer> customers2 = this.customerRepository.findAll();
        log.info("<<<<<<<<<<<<<<<<{}",customers2.get(0));

        CustomerGroup group1 = this.customerGroupRepository.getOne(this.customerGroup.getId());
        assertEquals(1L,group1.getId().longValue());
        List<Customer> customers = group1.getCustomers();
        assertEquals(2,customers.size());
        log.info(">>>>>>>>{}",this.customerRepository.count());
    }

}
