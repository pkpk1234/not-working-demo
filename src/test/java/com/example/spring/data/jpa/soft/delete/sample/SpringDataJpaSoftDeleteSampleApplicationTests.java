package com.example.spring.data.jpa.soft.delete.sample;

import com.example.spring.data.jpa.soft.delete.sample.model.Customer;
import com.example.spring.data.jpa.soft.delete.sample.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class SpringDataJpaSoftDeleteSampleApplicationTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;
    private Customer deletedCustomer;

    @Before
    public void contextLoads() {
        log.info("init data");
        this.customerRepository.truncate();
        customer = this.testEntityManager.persist(new Customer("customer1"));
        this.testEntityManager.persist(new Customer("customer2"));
        deletedCustomer = this.testEntityManager.persist(new Customer("deletedCustomer"));
        this.customerRepository.delete(deletedCustomer);
    }

    @Test
    public void testLoadAll() {
        List<Customer> customers = this.customerRepository.findAll();
        assertEquals(2, customers.size());
    }

    @Test
    public void testFindOne() {
        Customer customer1 = this.customerRepository.findOne(this.customer.getId());
        assertEquals("customer1", customer1.getName());
        assertEquals(false, customer1.getDeleted());
    }

    @Test
    public void testNoLoadDeleted() {
        Customer customer3 = this.customerRepository.findOne(this.deletedCustomer.getId());
        assertNull(customer3);
    }

    @Test
    public void testNoLoadDeleted2() {
        Customer result = this.customerRepository.findCustomersByName(this.deletedCustomer.getName());
        assertNull(result);
    }

    @Test
    public void testGetAllIncludeDeleted() {
        List<Customer> result = this.customerRepository.getAllIncludeDeleted();
        assertEquals(3, result.size());
        assertEquals(true, result.get(result.size() - 1).getDeleted());
    }
}
