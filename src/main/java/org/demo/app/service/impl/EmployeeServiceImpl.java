package org.demo.app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.demo.app.dto.EmployeeDto;
import org.demo.app.exception.JuniorEmployeeException;
import org.demo.app.mapper.EmployeeMapper;
import org.demo.app.model.Employee;
import org.demo.app.repo.EmployeeRepo;
import org.demo.app.service.EmployeeService;
import org.demo.app.subscriper.EmployeeSubscriber;
import org.demo.app.util.EmployeeUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final EmployeeMapper employeeMapper;

    @Override
    public Flux<Employee> createRandomList() {
        Flux<Employee> flux = Flux
                .fromIterable(EmployeeUtil.getEmployeeList(5))
                .doOnNext(employee -> {
                    if (employee.getSalary().compareTo(new BigDecimal(20_000)) <= 0) {
                        throw new JuniorEmployeeException("employee salary is less than 10K");
                    }
                })
                .onErrorContinue(JuniorEmployeeException.class, (e, o) ->
                        log.error("{} with id {} and salary {}", e.getMessage(), ((Employee) o).getId(), ((Employee) o).getSalary()))
                .delayElements(Duration.ofSeconds(1));

        flux.subscribe(new EmployeeSubscriber());
        return flux;
    }

    // 1. log
    // 2. distinct & distinctUntilChanged
    // 3. filter, map,m and flatMap
    // 4. collect
    // 5. doOnEach and doOnNext
    // 6. concatWith and thenMany
    // 7. zipWith
    @Override
    public Flux<Employee> createRandomList(int size) {
        List<Employee> employeeList1 = EmployeeUtil.getEmployeeList(size);
        List<Employee> employeeList2 = EmployeeUtil.getEmployeeList(size);
        Flux<Employee> employeeFlux1 = Flux
                .fromStream(employeeList1.stream())
                .delayElements(Duration.ofMillis(200));
        Flux<Employee> employeeFlux2 = Flux
                .fromStream(employeeList2.stream())
                .delayElements(Duration.ofMillis(200));
        return employeeFlux1
                .distinct()
                //.distinctUntilChanged()
                .filter(employee -> employee.getSalary().compareTo(new BigDecimal(20000)) > 0)
                //.map(employee -> Employee.builder().firstName(employee.getFirstName()).salary(employee.getSalary()).build())
                .doOnEach(employee -> {
                    if (employee.hasValue()) {
                        sendMail(employee.get().getEmail());
                    }
                })
                .concatWith(employeeFlux2)
                .log();
    }


    private void sendMail(String email) {
        log.info("send email to {}", email);
    }

    @Override
    public Flux<Employee> createStaticList(int size) {
        List<Employee> employeeList = EmployeeUtil.getEmployeeList(size);
        return employeeRepo.saveAll(employeeList);
    }

    private Flux<Employee> saveStaticList(int size) {
        Flux<Employee> empFlux = Flux.create(flux -> {
            for (int i = 0; i < size; i++) {
                flux.next(EmployeeUtil.createRandomEmployee());
                threadSleep(1000);
            }
            flux.complete();
        });
        empFlux.subscribe(new EmployeeSubscriber());
        empFlux.log().subscribe(s -> log.info("employee mail {}", s.getEmail()));
        return empFlux;
    }

    @Override
    public Flux<Employee> findList() {
        return employeeRepo.findAll().delayElements(Duration.ofMillis(100));
    }

    @Override
    public Mono<Employee> findById(String id) {
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
        return employeeRepo.save(employee);
    }

    @Override
    public Mono<Void> delete(String id) {
        return employeeRepo.deleteById(id);
    }

    @SneakyThrows
    private void threadSleep(long time) {
        sleep(time);
    }
}
