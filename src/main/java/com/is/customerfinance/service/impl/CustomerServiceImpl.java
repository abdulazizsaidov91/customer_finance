package com.is.customerfinance.service.impl;

import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.model.Customer;
import com.is.customerfinance.repository.CustomerRepository;
import com.is.customerfinance.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    @WriteTransactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @WriteTransactional
    public Customer updateCustomer(UUID id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @WriteTransactional
    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
