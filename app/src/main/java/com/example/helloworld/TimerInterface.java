package com.example.helloworld;

import java.util.concurrent.atomic.AtomicLong;

public interface TimerInterface {
    AtomicLong currentCentiseconds = new AtomicLong();
    void setCurrentCentiseconds(AtomicLong currentCentiseconds);
    AtomicLong getCurrentCentiseconds();
}