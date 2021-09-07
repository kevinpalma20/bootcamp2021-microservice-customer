package com.everisbootcamp.customers.Controller;

import com.everisbootcamp.customers.Data.Customer;
import com.everisbootcamp.customers.Model.CustomerFrom;
import com.everisbootcamp.customers.Service.CustomerService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<Customer>>> findByAll() {
        return Mono.just(ResponseEntity.accepted().body(service.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Customer>> findById(@PathVariable("id") String id) {
        return service.getByIdcustomer(id).map(mapper -> ResponseEntity.ok().body(mapper))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(@RequestBody @Valid CustomerFrom model,
            BindingResult bindinResult) {
        if (bindinResult.hasErrors())
            return service.BindingResultErrors(bindinResult);

        return service.register(model).map(response -> {
            return ResponseEntity.ok(response);
        }).defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @PutMapping("/update/{idcustomer}")
    public Mono<ResponseEntity<Map<String, Object>>> update(@PathVariable("idcustomer") String idcustomer,
            @RequestBody @Valid CustomerFrom model, BindingResult bindinResult) {
        if (bindinResult.hasErrors())
            return service.BindingResultErrors(bindinResult);

        return service.update(idcustomer, model).map(response -> {

            return ResponseEntity.ok(response);
        }).defaultIfEmpty(ResponseEntity.internalServerError().build());

    }
}