package projectspring.library.service;

import projectspring.library.dto.CustomerDto;
import projectspring.library.dto.ResetPass;
import projectspring.library.model.Customer;

public interface ICustomerService {
    Customer save(CustomerDto customerDto);
    Customer findByUsername(String username);
    Customer update(Customer customer);
    Customer resetPassword(Customer customer);
}
