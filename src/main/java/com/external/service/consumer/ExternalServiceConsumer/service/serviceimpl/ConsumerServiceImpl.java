package com.external.service.consumer.ExternalServiceConsumer.service.serviceimpl;

import com.external.service.consumer.ExternalServiceConsumer.bean.Tasks;
import com.external.service.consumer.ExternalServiceConsumer.dao.TasksRepository;
import com.external.service.consumer.ExternalServiceConsumer.entity.Task;
import com.external.service.consumer.ExternalServiceConsumer.service.ConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private TasksRepository tasksRepository;

    @Value("${todo.endpoint.tasks.details.complete}")
    private String completeTODOItems;

    @Value("${todo.endpoint.tasks.details.userid}")
    private String userIDTODOItems;

    @Value("${todo.endpoint.tasks.details.status}")
    private String statusTODOItems;


    @Value("${todo.endpoint.tasks.details.userIDAndStatus}")
    private String userIDAndStatusTODOItems;


    @Override
    @Cacheable("tasks")
    public List<Tasks> fetchConsumerDetails() {
        List<Tasks> tasksDetails =  getTaskDetails();
        saveConsumerDetails(tasksDetails);
        return tasksDetails;
    }

    @Scheduled(cron = "0 0/5 * * * *")
    @CacheEvict(value = { "tasks" })
    public void clearCache() {
        System.out.println("Running after every 5 minutes to evict the cache.....");
    }

    @Override
    public Map<Integer, List<Tasks>> fetchConsumerDetailsGroupByUserId() {
        List<Tasks> tasksDetails =  getTaskDetails();
        Map<Integer, List<Tasks>> groupByUserID = tasksDetails.stream()
                .collect(Collectors.groupingBy(Tasks::getUserId));
        return groupByUserID;
    }


    @Override
    public List<Tasks> fetchConsumerDetailsByUserID(int userid) {
        List<Tasks> taskList = getTaskDetailsByUserId(userid);
        return taskList;
    }

    @Override
    public List<Tasks> fetchConsumerDetailsByCompletedOrNotCompleted(boolean status) {
        List<Tasks> taskList = getTaskDetailsBasedOnStatus(status);
        return taskList;
    }

    @Override
    public List<Tasks> fetchConsumerDetailsByUserIDAndStatus(int userid, boolean status) {
        List<Tasks> taskList = getTaskDetailsBasedOnUserIdAndStatus(userid,status);
        return taskList;
    }

    private List<Tasks> getTaskDetails ()  {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(completeTODOItems,String.class);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        } catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }
        return task;
    }

   private List<Tasks> getTaskDetailsByUserId(int userid) {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(userIDTODOItems,String.class, userid);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        }catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }
        return task;
    }


    private List<Tasks> getTaskDetailsBasedOnStatus (boolean status) {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(statusTODOItems,String.class, status);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        }catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }
        return task;
    }

    private List<Tasks> getTaskDetailsBasedOnUserIdAndStatus (int userid, boolean status) {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(userIDAndStatusTODOItems,String.class, userid,status);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        }catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }
        return task;
    }


    private void saveConsumerDetails(List<Tasks> tasks) {
        List<Task> taskList = new ArrayList<>();
        Task task;
        if (tasks!=null && tasks.size() >0) {
            for (Tasks t : tasks) {
                task = new Task();
                task.setUserId(t.getUserId());
                task.setId(t.getId());
                task.setTitle(t.getTitle());
                task.setCompleted(t.isCompleted());

                taskList.add(task);
            }
            tasksRepository.saveAll(taskList);
        }
    }

}
