package com.external.service.consumer.ExternalServiceConsumer.streamapifilter;

import com.external.service.consumer.ExternalServiceConsumer.bean.Tasks;

import java.util.List;

public interface StreamAPIFilter {
    List<Tasks> fetchUsingFilterStreamAPI(int userId, boolean status);
    List<Tasks> fetchUsingUserIDFilter (int userid);
    List<Tasks> fetchUsingStatusFilter (boolean status);
}
