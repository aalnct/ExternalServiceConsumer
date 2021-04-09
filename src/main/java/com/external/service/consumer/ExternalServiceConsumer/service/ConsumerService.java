package com.external.service.consumer.ExternalServiceConsumer.service;

import com.external.service.consumer.ExternalServiceConsumer.bean.Tasks;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface ConsumerService {
    List<Tasks> fetchConsumerDetails();
    Map<Integer, List<Tasks>> fetchConsumerDetailsGroupByUserId();
    List<Tasks> fetchConsumerDetailsByUserID(int userid);
    List<Tasks> fetchConsumerDetailsByCompletedOrNotCompleted(boolean status);
    List<Tasks> fetchConsumerDetailsByUserIDAndStatus(int userid,boolean status);
}
