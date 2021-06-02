package pt.santander.customerapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pt.santander.customerapi.dto.CustomerRequest;
import pt.santander.customerapi.dto.CustomerResponse;

import javax.validation.Valid;
import java.util.List;

public interface CustomerService {


    public List<CustomerResponse> getCustomers(String name, String nif, String email, String rangeMin, String rangeMax, String active) throws Exception;
    public CustomerResponse createCustomer(CustomerRequest request) throws Exception;
    public CustomerResponse changeCustomer(Integer id, CustomerRequest request) throws Exception;
    public Void deleteCustomer(Integer id) throws Exception;
    public Void deleteCustomers(List<Integer> ids) throws Exception;
    public List<CustomerResponse> getCustomerEmailQuery(@RequestParam String group) throws Exception;



    }
