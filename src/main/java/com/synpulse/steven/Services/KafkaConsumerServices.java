package com.synpulse.steven.Services;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.synpulse.steven.Pojo.AppConstants;

@Service
@Slf4j
public class KafkaConsumerServices {

    @KafkaListener(topics = {AppConstants.GET_TOPIC, AppConstants.POST_TOPIC},
            groupId = AppConstants.GROUP_ID)
    public void postConsume(ConsumerRecord<String,String> record)
    {
        log.info("Message received "+"(key: "+record.key()+"), (value: "+record.value()+")");
    }
}