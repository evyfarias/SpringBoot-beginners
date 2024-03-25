package com.amigoscode;

import org.springframework.web.bind.annotation.*;

import java.util.List;



/*
Todas essas anotações, representam metodos da classe @SpringBootApplication
Ou seja, a anotacao @SpringBootApplication engloba as 3 anotações abaixo

@ComponentScan(basePackages = "com.amigoscode") -> identifica a main
@EnableAutoConfiguration                        -> configura o server
@Configuration                                  -> configuração

 */

/*
A anotação @RestController indica que há o uso da anotação @Controller, junto com a @ResponseBody,
ou seja, ela irá retornar um JSON
 */
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name,
                              String email,
                              Integer age){

    }

    @PostMapping
    public void postCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setEmail(request.email());
        customer.setName(request.name());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void putCustomer(@PathVariable Integer id, @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.getReferenceById(id);
        customer.setId(id);
        customer.setEmail(request.email());
        customer.setName(request.name());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
}
