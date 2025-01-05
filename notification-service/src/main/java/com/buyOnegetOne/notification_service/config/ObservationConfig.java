package com.buyOnegetOne.notification_service.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
@RequiredArgsConstructor
public class ObservationConfig {

    private final ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory;

    //after add this trace id will be propagated to kafka
    @PostConstruct
    public void setObservationForKafkaTemplate() {
        kafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
    }

    //Aop aspect to observe the metrics
    @Bean
    ObservedAspect observedAspect(ObservationRegistry registry) {
        return new ObservedAspect(registry);
    }
}
