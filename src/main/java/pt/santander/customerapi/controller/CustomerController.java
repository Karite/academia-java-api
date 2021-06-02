package pt.santander.customerapi.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.santander.customerapi.dto.CustomerRequest;
import pt.santander.customerapi.dto.CustomerResponse;
import pt.santander.customerapi.service.CustomerService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerServ;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getCustomers(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String nif,
                                                               @RequestParam(required = false) String email,
                                                               @RequestParam(required = false) String rangeMin,
                                                               @RequestParam(required = false) String rangeMax,
                                                               @RequestParam(required = false) String active) throws Exception{
        List<CustomerResponse> list = customerServ.getCustomers(name, nif, email, rangeMin, rangeMax, active);
        if(list.isEmpty()){
            return ResponseEntity.status(404).body(list); // Envia o status 404 de código de erro e o objeto no body
        }
        else{
            return ResponseEntity.status(200).body(list); // Envia o status 200 de código de sucesso e o objeto no body
        }

    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) throws Exception {
        return ResponseEntity.ok(customerServ.createCustomer(request));


    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> changeCustomer(@PathVariable Integer id,
            @Valid @RequestBody CustomerRequest request) throws Exception {

        return ResponseEntity.ok(customerServ.changeCustomer(id,request));



    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) throws Exception {
        customerServ.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customers")
    public ResponseEntity<Void> deleteCustomers(@RequestBody List<Integer> ids) throws Exception {
        customerServ.deleteCustomers(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customers/emailgroup")
    public List<CustomerResponse> getCustomerEmailQuery(@RequestParam String group) throws Exception{

        return customerServ.getCustomerEmailQuery(group);
    }
}
