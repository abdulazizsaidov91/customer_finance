package com.is.customerfinance.service.impl;

import com.is.customerfinance.annatation.WriteTransactional;
import com.is.customerfinance.exception.BadRequestException;
import com.is.customerfinance.model.Customer;
import com.is.customerfinance.repository.CustomerRepository;
import com.is.customerfinance.service.CustomerService;
import com.is.customerfinance.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final LocalizationService localizationService;

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
    public Customer updateCustomer(UUID id, Customer updatedCustomer, Locale locale) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new BadRequestException("Customer Not Found", localizationService.getMessage("customer.not.found", locale)));
    }

    @WriteTransactional
    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }
}
