package com.ngworks.loanengine.domain.service;

import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class CurrentTimeProvider {

    public LocalTime getLocalTimeNow() {
        return LocalTime.now();
    }
}
