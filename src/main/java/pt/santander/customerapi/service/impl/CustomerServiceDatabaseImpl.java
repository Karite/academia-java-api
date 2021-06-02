package pt.santander.customerapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import pt.santander.customerapi.dto.CustomerRequest;
import pt.santander.customerapi.dto.CustomerResponse;
import pt.santander.customerapi.entity.Customer;
import pt.santander.customerapi.repository.CustomerRepository;
import pt.santander.customerapi.service.CustomerService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceDatabaseImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRep;

    /*@PostConstruct
    public void init(){
        // Após a construção do objeto CustomerServiceDatabase
    }*/

    /*@PreDestroy
    public void destroy(){
        //Antes de destruir o objeto
    }*/

    @Override
    public List<CustomerResponse> getCustomers(String name, String nif, String email, String rangeMin, String rangeMax, String active) throws Exception {
        List<CustomerResponse> responseList = new ArrayList<>();
        if(email != null){
            responseList.addAll(
                    customerRep.findByEmail(email)
                            .stream()
                            .map(customer -> {
                                return CustomerResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .nif(customer.getNif())
                                        .email(customer.getEmail())
                                        .active(customer.getActive())
                                        .build();
                            })
                            .collect(Collectors.toList())
            );
        }
        else if(rangeMin != null && rangeMax != null){
            responseList.addAll(
                    customerRep.findByNifBetween(rangeMin,rangeMax)
                            .stream()
                            .map(customer -> {
                                return CustomerResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .nif(customer.getNif())
                                        .email(customer.getEmail())
                                        .active(customer.getActive())
                                        .build();
                            })
                            .collect(Collectors.toList())
            );
        }
        else{
            if(active == null) {
                if (name != null && nif != null) {
                    // pesquisar por ambos
                    responseList.addAll(
                            customerRep.findByActiveAndNifAndNameContaining(true, nif, name)
                                    .stream()
                                    .map(customer -> {
                                        return CustomerResponse.builder()
                                                .id(customer.getId())
                                                .name(customer.getName())
                                                .nif(customer.getNif())
                                                .email(customer.getEmail())
                                                .active(customer.getActive())
                                                .build();
                                    })
                                    .collect(Collectors.toList())
                    );
                } else if (name != null) {
                    //pesquisar por nome
                    responseList.addAll(customerRep.findByActiveAndNameContaining(true, name)
                            .stream()
                            .map(customer -> {
                                return CustomerResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .nif(customer.getNif())
                                        .email(customer.getEmail())
                                        .active(customer.getActive())
                                        .build();
                            })
                            .collect(Collectors.toList())
                    );

                } else if (nif != null) {
                    //pesquisar por nif
                    responseList.addAll(customerRep.findByActiveAndNif(true, nif).stream()
                            .map(customer -> {
                                return CustomerResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .nif(customer.getNif())
                                        .email(customer.getEmail())
                                        .active(customer.getActive())
                                        .build();
                            })
                            .collect(Collectors.toList())
                    );

                } else {
                    //pesquisar só os ativos
                    responseList.addAll(customerRep.findByActive(true)
                            .stream()
                            .map(customer -> {
                                return CustomerResponse.builder()
                                        .id(customer.getId())
                                        .name(customer.getName())
                                        .nif(customer.getNif())
                                        .email(customer.getEmail())
                                        .active(customer.getActive())
                                        .build();
                            })
                            .collect(Collectors.toList())
                    );
                }
            }else {
                if ("true".equals(active)) {
                    if (name != null && nif != null) {
                        // pesquisar por ambos
                        responseList.addAll(
                                customerRep.findByActiveAndNifAndNameContaining(true, nif, name)
                                        .stream()
                                        .map(customer -> {
                                            return CustomerResponse.builder()
                                                    .id(customer.getId())
                                                    .name(customer.getName())
                                                    .nif(customer.getNif())
                                                    .email(customer.getEmail())
                                                    .active(customer.getActive())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                        );
                    } else if (name != null) {
                        //pesquisar por nome
                        responseList.addAll(customerRep.findByActiveAndNameContaining(true, name)
                                .stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );

                    } else if (nif != null) {
                        //pesquisar por nif
                        responseList.addAll(customerRep.findByActiveAndNif(true, nif).stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );

                    } else {
                        //pesquisar só os ativos
                        responseList.addAll(customerRep.findByActive(true)
                                .stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );
                    }
                } else if ("false".equals(active)) {
                    if (name != null && nif != null) {
                        // pesquisar por ambos
                        responseList.addAll(
                                customerRep.findByActiveAndNifAndNameContaining(false, nif, name)
                                        .stream()
                                        .map(customer -> {
                                            return CustomerResponse.builder()
                                                    .id(customer.getId())
                                                    .name(customer.getName())
                                                    .nif(customer.getNif())
                                                    .email(customer.getEmail())
                                                    .active(customer.getActive())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                        );
                    } else if (name != null) {
                        //pesquisar por nome
                        responseList.addAll(customerRep.findByActiveAndNameContaining(false, name)
                                .stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );

                    } else if (nif != null) {
                        //pesquisar por nif
                        responseList.addAll(customerRep.findByActiveAndNif(false, nif).stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );

                    } else {
                        //pesquisar só os ativos
                        responseList.addAll(customerRep.findByActive(false)
                                .stream()
                                .map(customer -> {
                                    return CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build();
                                })
                                .collect(Collectors.toList())
                        );
                    }
                } else if ("all".equals(active)) {
                    if (name == null && nif == null) {
                        customerRep.findAll().forEach(customer -> {
                            responseList.add(
                                    CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build()
                            );


                        });
                    } else if (name != null) {
                        customerRep.findByNameContaining(name).forEach(customer -> {
                            responseList.add(
                                    CustomerResponse.builder()
                                            .id(customer.getId())
                                            .name(customer.getName())
                                            .nif(customer.getNif())
                                            .email(customer.getEmail())
                                            .active(customer.getActive())
                                            .build()
                            );


                        });
                    }

                }
            }
        }
        return responseList;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 5)
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request) throws Exception{

        List<CustomerRequest> entry_list = request.getLista();
        List<CustomerResponse> output_list = new ArrayList<>();
        if(!request.getLista().isEmpty()){
            request.getLista().forEach(
                    customerReq -> {
                        Customer customer = Customer.builder()
                                .name(customerReq.getName())
                                .nif(customerReq.getNif())
                                .email(customerReq.getEmail())
                                .active(true)
                                .build();
                        Customer customerNew = customerRep.save(customer);
                        output_list.add(CustomerResponse.builder()
                                .id(customerNew.getId())
                                .name(customerNew.getName())
                                .nif(customerNew.getNif())
                                .email(customerNew.getEmail())
                                .active(customerNew.getActive())
                                .build());
                    }
            );
        }
        Customer customer = Customer.builder()
                .name(request.getName())
                .nif(request.getNif())
                .email(request.getEmail())
                .active(true)
                .build();
        Customer customerNew = customerRep.save(customer);


        return CustomerResponse.builder()
                .id(customerNew.getId())
                .name(customerNew.getName())
                .nif(customerNew.getNif())
                .email(customerNew.getEmail())
                .active(customerNew.getActive())
                .list(output_list)
                .build();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 5)
    public CustomerResponse changeCustomer(Integer id, CustomerRequest request) throws Exception{
        return customerRep.findById(id).
                map(customer ->{
                    customer.setName(request.getName());
                    customer.setNif(request.getNif());
                    customer.setEmail(request.getEmail());
                    Customer customerUpdate = customerRep.save(customer);
                    return CustomerResponse.builder()
                            .id(customerUpdate.getId())
                            .name(customerUpdate.getName())
                            .nif(customerUpdate.getNif())
                            .email(customerUpdate.getEmail())
                            .active(customerUpdate.getActive())
                            .build();
                })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));




    }

    public Void deleteCustomer(Integer id) throws Exception {
        customerRep.findById(id).
                map(customer ->{
                    customer.setActive(false);
                    Customer customerUpdate = customerRep.save(customer);
                    return CustomerResponse.builder()
                            .id(customerUpdate.getId())
                            .name(customerUpdate.getName())
                            .nif(customerUpdate.getNif())
                            .email(customerUpdate.getEmail())
                            .active(customerUpdate.getActive())
                            .build();
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));

        return null;

    }

    public Void deleteCustomers(List<Integer> ids) throws Exception{
        if(!ids.isEmpty()){
            ids.forEach(
                    id -> {
                        Customer customer = customerRep.findById(id).get();
                        customer.setActive(false);
                        customerRep.save(customer);

                    }
            );
        }
        return null;
    }

    public List<CustomerResponse> getCustomerEmailQuery(@RequestParam String group) throws Exception{
        List<CustomerResponse> responseList = new ArrayList<>();
        responseList.addAll(
                customerRep.findByEmailGroup(group)
                        .stream()
                        .map(customer -> {
                            return CustomerResponse.builder()
                                    .id(customer.getId())
                                    .name(customer.getName())
                                    .nif(customer.getNif())
                                    .email(customer.getEmail())
                                    .active(customer.getActive())
                                    .build();
                        })
                        .collect(Collectors.toList())
        );
         return responseList;
    }

}
