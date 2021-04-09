package com.external.service.consumer.ExternalServiceConsumer.streamapifilter.streamapifilterimpl;

import com.external.service.consumer.ExternalServiceConsumer.bean.Tasks;
import com.external.service.consumer.ExternalServiceConsumer.streamapifilter.StreamAPIFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StreamAPIFilterImpl implements StreamAPIFilter {

    @Value("${todo.endpoint.tasks.details.complete}")
    private String completeTODOItems;

    @Override
    @Cacheable("streamFilterUserIDAndStatus")
    public List<Tasks> fetchUsingFilterStreamAPI(int userId, boolean status) {
        List<Tasks> tasksDetails =  getTaskDetailsFilterStreamAPI(userId,status);
        return tasksDetails;
    }

    private List<Tasks> getTaskDetailsFilterStreamAPI (int userId, boolean status)  {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(completeTODOItems,String.class);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        } catch (JsonProcessingException | RestClientException processingException ) {
            processingException.printStackTrace();
        }

        List<Tasks> filteredTask = task.stream().filter(taskdetails -> taskdetails.getUserId() == userId).filter(tasksdetails1 -> tasksdetails1
                .isCompleted() == status).collect(Collectors.toList());

        return filteredTask;
    }

    @Override
    @Cacheable("streamFilterUserID")
    public List<Tasks> fetchUsingUserIDFilter(int userid) {
        List<Tasks> tasksDetails =  getTaskDetailsFilterUsingUserID(userid);
        return tasksDetails;
    }

    private List<Tasks> getTaskDetailsFilterUsingUserID (int userId)  {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(completeTODOItems,String.class);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        } catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }

        List<Tasks> filteredTask = task.stream().filter(taskdetails -> taskdetails.getUserId() == userId).collect(Collectors.toList());

        return filteredTask;
    }

    @Override
    @Cacheable("streamFilterStatus")
    public List<Tasks> fetchUsingStatusFilter(boolean status) {
        List<Tasks> tasksList=  getTaskDetailsFilterUsingStatus(status);
        return tasksList;
    }

    private List<Tasks> getTaskDetailsFilterUsingStatus (boolean status)  {
        RestTemplate restTemplate = new RestTemplate();
        List<Tasks> task = null;
        try {
            String consumerResultSet = restTemplate.getForObject(completeTODOItems,String.class);
            task = new ObjectMapper().readValue(consumerResultSet,new TypeReference<List<Tasks>>(){});
        } catch (JsonProcessingException | RestClientException  processingException ) {
            processingException.printStackTrace();
        }

        List<Tasks> filteredTask = task.stream().filter(taskdetails -> taskdetails.isCompleted() == status).collect(Collectors.toList());

        return filteredTask;
    }
}
