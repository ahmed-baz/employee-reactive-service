package org.demo.app.controller;


import lombok.RequiredArgsConstructor;
import org.demo.app.dto.EmployeeDto;
import org.demo.app.model.Employee;
import org.demo.app.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> findEmployeeList() {
        return employeeService.findList();
    }

    @GetMapping(value = "/create/{size}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> createRandomList(@PathVariable int size) {
        return employeeService.createRandomList(size);
    }

    @GetMapping(value = "/create", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> createRandomList() {
        return employeeService.createRandomList();
    }

    @GetMapping(value = "/dummy/{size}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Employee> createList(@PathVariable int size) {
        return employeeService.createStaticList(size);
    }

    @GetMapping("/{id}")
    public Mono<Employee> findById(@PathVariable String id) {
        return employeeService.findById(id);
    }

    @GetMapping("/count")
    public Mono<Long> count() {
        return employeeService.count();
    }

    @PostMapping
    public Mono<Employee> addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createOrUpdate(employeeDto);
    }

    @PutMapping
    public Mono<Employee> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createOrUpdate(employeeDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteEmployee(@PathVariable String id) {
        return employeeService.delete(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAllEmployee() {
        return employeeService.deleteAll();
    }
}
