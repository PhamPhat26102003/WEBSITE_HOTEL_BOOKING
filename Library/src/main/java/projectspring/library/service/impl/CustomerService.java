package projectspring.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectspring.library.dto.CustomerDto;
import projectspring.library.model.Customer;
import projectspring.library.repository.ICustomerRepository;
import projectspring.library.repository.IRoleRepository;
import projectspring.library.service.ICustomerService;

import java.util.Arrays;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());
        customer.setCountry(customerDto.getCountry());
        customer.setCity(customerDto.getCity());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerUpdate = customerRepository.findByUsername(customer.getUsername());
        customerUpdate.setFirstName(customer.getFirstName());
        customerUpdate.setLastName(customer.getLastName());
        customerUpdate.setAddress(customer.getAddress());
        customerUpdate.setCountry(customer.getCountry());
        customerUpdate.setCity(customer.getCity());
        customerUpdate.setPhone(customer.getPhone());
        return customerRepository.save(customerUpdate);
    }
}
