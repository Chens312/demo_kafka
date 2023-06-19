package com.synpulse.steven.Services;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.synpulse.steven.Pojo.AppConstants;
@Service
@Slf4j
public class KafkaProducerServices {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPost(String postTransactionJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(postTransactionJson);
        String unique_id = jsonObject.getString("unique_id");
        int amount = jsonObject.getInt("amount");
        log.info("Message sent "+"(key: "+unique_id+"), (value: "+postTransactionJson+")");
        this.kafkaTemplate.send(AppConstants.POST_TOPIC, unique_id, postTransactionJson);
    }

}
