package org.demo.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.demo.app.dto.EmployeeDto;
import org.demo.app.mapper.EmployeeMapper;
import org.demo.app.model.Employee;
import org.demo.app.repo.EmployeeRepo;
import org.demo.app.service.EmployeeService;
import org.demo.app.util.EmployeeUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    @Override
    public Flux<Employee> createRandomList() {
        return Flux
                .fromStream(EmployeeUtil.getEmployeeList(10).stream())
                .delayElements(Duration.ofMillis(200));
    }

    @Override
    public Flux<Employee> createRandomList(int size) {
        List<Employee> employeeList = EmployeeUtil.getEmployeeList(size);
        Flux<Employee> employeeFlux = employeeRepo.saveAll(employeeList);
        return Flux
                .fromStream(employeeList.stream())
                .delayElements(Duration.ofMillis(200));
    }

    @Override
    public Flux<Employee> findList() {
        return employeeRepo.findAll().delayElements(Duration.ofMillis(100));
    }

    @Override
    public Mono<Employee> findById(Long id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Mono<Long> count() {
        return employeeRepo.count();
    }

    @Override
    public Mono<Employee> createOrUpdate(EmployeeDto employeeDto) {
        if (employeeDto.getId() == null) {
            employeeDto.setId(UUID.randomUUID().toString());
        }
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        employeeRepo.save(employee);
        return Mono.just(employee);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return employeeRepo.deleteById(id);
    }
}
