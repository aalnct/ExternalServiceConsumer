package com.external.service.consumer.ExternalServiceConsumer.controller;

import com.external.service.consumer.ExternalServiceConsumer.bean.Tasks;
import com.external.service.consumer.ExternalServiceConsumer.service.ConsumerService;
import com.external.service.consumer.ExternalServiceConsumer.streamapifilter.StreamAPIFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/consumer/")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private StreamAPIFilter streamAPIFilter;

    @GetMapping(value = "/fetch")
    public ResponseEntity<List<Tasks>> callServiceDetails () {
        List<Tasks> tasksList = consumerService.fetchConsumerDetails();
        return new ResponseEntity<>(tasksList, HttpStatus.OK);
    }

    @GetMapping(value = "/fetchConsumerDetailsGroupByUserId")
    public Map<Integer, List<Tasks>> fetchConsumerDetailsGroupByUserId () {
        Map<Integer, List<Tasks>> taskMap = consumerService.fetchConsumerDetailsGroupByUserId();
        return taskMap;
    }

    @GetMapping(value = "/fetchByUserId/{userid}")
    public List<Tasks> fetchToDoItemsGroupBy (@PathVariable("userid") int userid) {
        List<Tasks> tasksList = consumerService.fetchConsumerDetailsByUserID(userid);
        return tasksList;
    }

    @GetMapping(value = "/fetchByStatus/{status}")
    public List<Tasks> fetchConsumerDetailsByCompletedOrNotCompleted (@PathVariable("status") boolean status) {
        List<Tasks> tasksList = consumerService.fetchConsumerDetailsByCompletedOrNotCompleted(status);
        return tasksList;
    }

    @GetMapping(value = "/fetchByStatus/{userid}/{status}")
    public List<Tasks> fetchConsumerDetailsByUserIDAndStatus (@PathVariable("userid") int userid , @PathVariable("status") boolean status) {
        List<Tasks> tasksList = consumerService.fetchConsumerDetailsByUserIDAndStatus(userid,status);
        return tasksList;
    }
    @GetMapping(value = "/fetchUsingFilterStreamAPI/{userid}/{status}")
    public List<Tasks> fetchUsingFilterStreamAPI (@PathVariable("userid") int userid , @PathVariable("status") boolean status) {
        List<Tasks> tasksList = streamAPIFilter.fetchUsingFilterStreamAPI(userid,status);
        return tasksList;
    }

    @GetMapping(value = "/fetchUsingUserIDFilter/{userid}")
    public List<Tasks> fetchUsingUserIDFilter (@PathVariable("userid") int userid) {
        List<Tasks> tasksList = streamAPIFilter.fetchUsingUserIDFilter(userid);
        return tasksList;
    }

    @GetMapping(value = "/fetchUsingStatusFilter/{status}")
    public List<Tasks> fetchUsingStatusFilter (@PathVariable("status") boolean status) {
        List<Tasks> tasksList = streamAPIFilter.fetchUsingStatusFilter(status);
        return tasksList;
    }
}
