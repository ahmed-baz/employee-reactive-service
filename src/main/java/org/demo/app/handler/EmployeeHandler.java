package org.demo.app.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.demo.app.model.Employee;
import org.demo.app.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmployeeHandler {

    private final EmployeeService employeeService;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        String size = request.pathVariable("size");
        log.info("size  = {} ", size);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(employeeService.createRandomList(Integer.parseInt(size)), Employee.class);
    }
}
