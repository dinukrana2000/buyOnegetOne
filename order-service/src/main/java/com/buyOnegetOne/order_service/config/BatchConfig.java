package com.buyOnegetOne.order_service.config;

import com.buyOnegetOne.order_service.dto.OrderLineItemsRequestDto;
import com.buyOnegetOne.order_service.entity.OrderLineItems;
import com.buyOnegetOne.order_service.util.batch.CustomProcessor;
import com.buyOnegetOne.order_service.util.batch.CustomReader;
import com.buyOnegetOne.order_service.util.batch.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class BatchConfig {

    private final CustomReader customReader;

    public BatchConfig(CustomReader customReader) {
        this.customReader = customReader;
    }

    @Bean
    Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .start(createStep(jobRepository, transactionManager))
                .preventRestart()
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(100); // Set 3 threads
        taskExecutor.setMaxPoolSize(100);  // Maximum number of threads
        taskExecutor.setQueueCapacity(500); // Adjust as necessary
        taskExecutor.setThreadNamePrefix("spring_batch_thread-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<OrderLineItems, OrderLineItemsRequestDto> chunk(1000, transactionManager)
                .reader(customReader)
                .processor(new CustomProcessor())
                .writer(new CustomWriter())
                .taskExecutor(taskExecutor())
                .allowStartIfComplete(true)
                .build();
    }



}
