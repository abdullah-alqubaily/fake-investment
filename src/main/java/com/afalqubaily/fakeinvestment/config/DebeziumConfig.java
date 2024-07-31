//package com.afalqubaily.fakeinvestment.config;
//
//import io.debezium.config.Configuration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//
//import java.io.File;
//import java.io.IOException;
//
//@org.springframework.context.annotation.Configuration
//public class DebeziumConfig {
//
//    @Value("${debezium.datasource.user}")
//    private String user;
//
//    @Value("${debezium.datasource.password}")
//    private String password;
//
//    @Value("${debezium.datasource.dbname}")
//    private String dbname;
//
//    @Value("${debezium.datasource.hostname}")
//    private String hostname;
//
//    @Value("${debezium.datasource.port}")
//    private String port;
//
//    @Value("${debezium.datasource.slot.name}")
//    private String slotName;
//
//    @Value("${debezium.table.include.list}")
//    private String tableIncludeList;
//
//    @Value("${schema.registry.url}")
//    private String schemaRegistryUrl;
//
//    @Bean
//    public io.debezium.config.Configuration debeziumConfig() throws IOException {
//        var offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
//        return io.debezium.config.Configuration.create()
//                .with("name", "postgres-connector")
//                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector")
//                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
//                .with("offset.storage.file.filename", "/tmp/offsets.dat")
//                .with("offset.flush.interval.ms", 60000)
//                .with("database.hostname", hostname)
//                .with("database.port", port)
//                .with("database.user", user)
//                .with("database.password", password)
//                .with("database.dbname", dbname)
//                .with("database.server.name", "postgres-server")
//                .with("table.include.list", tableIncludeList)
//                .with("slot.name", slotName)
//                .with("plugin.name", "pgoutput")
//                .with("schema.registry.url", schemaRegistryUrl)
//                .build();
//
//    }
//}