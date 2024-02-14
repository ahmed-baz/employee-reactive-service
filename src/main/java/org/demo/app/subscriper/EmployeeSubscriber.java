package org.demo.app.subscriper;

import lombok.extern.log4j.Log4j2;
import org.demo.app.model.Employee;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Log4j2
public class EmployeeSubscriber implements Subscriber<Employee> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("onSubscribe");
        subscription.request(1);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Employee employee) {
        subscription.request(1);
        log.info("onNext | employee {}", employee.getEmail());
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError | {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }

}
