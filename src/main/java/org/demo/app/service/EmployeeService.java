package org.demo.app.service;

import org.demo.app.dto.EmployeeDto;
import org.demo.app.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EmployeeService {

    Flux<Employee> createRandomList();

    Flux<Employee> createRandomList(int size);

    Flux<Employee> createStaticList(int size);

    Flux<Employee> findList();

    Mono<Employee> findById(String id);

    Mono<Long> count();

    Mono<Employee> createOrUpdate(EmployeeDto employeeDto);

    Mono<Void> delete(String id);
}
