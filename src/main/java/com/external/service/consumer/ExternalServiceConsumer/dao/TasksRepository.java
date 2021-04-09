package com.external.service.consumer.ExternalServiceConsumer.dao;

import com.external.service.consumer.ExternalServiceConsumer.entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends CrudRepository<Task, Integer> {



}
