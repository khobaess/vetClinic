package com.vetclinic.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    
    private Pagination pagination = new Pagination();
    private Validation validation = new Validation();
    
    @Data
    public static class Pagination {
        private int defaultPageSize = 10;
        private int maxPageSize = 100;
        private String defaultSortBy = "id";
        private String defaultSortDir = "asc";
    }
    
    @Data
    public static class Validation {
        private int appointmentConflictMinutes = 30;
        private boolean allowPastAppointments = false;
    }
}
