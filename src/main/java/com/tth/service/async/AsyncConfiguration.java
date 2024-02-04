package com.tth.service.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: Kedaya
 * @Date: 2024/2/4 18:18:36
 * @ClassName: asyncService  18:18
 * @Description:
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
    // 自定义线程池
    @Bean("asyncTheadPool")
    public Executor asyncTheadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数，线程池创建的时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数，线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列，用来缓冲执行任务的队列
        executor.setQueueCapacity(200);
        // 允许的空闲时间60s，当超过了核心线程之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程名称前缀，方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("nc-thead-pool-");
        // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 该方法用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(60);
        // 设置拒绝策略，当线程池满了之后，依然有任务到来的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }
}
