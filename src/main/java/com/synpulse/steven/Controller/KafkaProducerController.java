package com.synpulse.steven.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.synpulse.steven.Services.KafkaProducerServices;
import com.synpulse.steven.Pojo.Transaction;
import com.synpulse.steven.Pojo.AppConstants;
import java.util.Properties;

import static com.synpulse.steven.Pojo.AppConstants.*;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

@Slf4j
@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
    private final KafkaProducerServices producerService;

    @Autowired
    public KafkaProducerController(KafkaProducerServices producerService)
    {
        this.producerService = producerService;
    }

    @PostMapping(value = "/publish")
    public void sendPostMessageToKafkaTopic(@RequestBody Transaction postTransaction) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(postTransaction);
        this.producerService.sendPost(jsonObject.toString());
    }

    @GetMapping(value = "/query")
    public void sendGetMessageToKafkaTopic(@RequestBody Transaction getTransaction)
    {
        try {
            JSONObject getJson = new JSONObject(getTransaction);
            String username = getJson.getString("username");
            Properties props = new Properties();
            props.put(StreamsConfig.APPLICATION_ID_CONFIG, "demo");
            props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            StreamsBuilder builder = new StreamsBuilder();
            KStream<String, String> messageStream = builder.stream(POST_TOPIC);
            KStream<String, String> filteredStream = messageStream.filter((key, value) ->
            {
                try {
                    JSONObject jsonObject = new JSONObject(value);
                    log.info("message found: "+jsonObject);
                    return jsonObject.getString("username").equals(username);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            filteredStream.to(GET_TOPIC);
            KafkaStreams streams = new KafkaStreams(builder.build(), props);
            streams.start();
        }
        catch (Exception exception) {
            log.info(exception.toString());
        }
    }
}
