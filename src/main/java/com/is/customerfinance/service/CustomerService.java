package com.is.customerfinance.service;

import com.is.customerfinance.model.Customer;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(UUID id);

    Optional<Customer> getCustomerByName(String name);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(UUID id, Customer updatedCustomer, Locale locale);

    void deleteCustomer(UUID id);
}
