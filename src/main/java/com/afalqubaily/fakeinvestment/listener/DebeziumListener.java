//package com.afalqubaily.fakeinvestment.listener;
//
//import io.debezium.engine.DebeziumEngine;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
//@Component
//public class DebeziumListener {
//
//    private final DebeziumEngine<?> engine;
//    private final Executor executor = Executors.newSingleThreadExecutor();
//
//    public DebeziumListener(DebeziumEngine<?> engine) {
//        this.engine = engine;
//    }
//
//    @PostConstruct
//    public void start() {
//        this.executor.execute(engine);
//    }
//
//    @PreDestroy
//    public void stop() {
//        if (engine != null) {
//            engine.close();
//        }
//    }
//}