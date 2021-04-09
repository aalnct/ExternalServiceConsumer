package com.external.service.consumer.ExternalServiceConsumer.streamapifilter.streamapifilterimpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

public class StreamCacheEviction {

    @Scheduled(cron = "0 0/5 * * * *")
    @CacheEvict(value = { "streamFilterUserIDAndStatus" })
    public void clearStreamFilterUserIDAndStatus() {
        System.out.println("Clearing cache named as ... streamFilterUserIDAndStatus");
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @CacheEvict(value = { "streamFilterUserID" })
    public void clearStreamFilterUserID() {
        System.out.println("Clearing cache named as ... streamFilterUserID");
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @CacheEvict(value = { "streamFilterStatus" })
    public void clearStreamFilterStatus() {
        System.out.println("Clearing cache named as ... streamFilterStatus");
    }

}
