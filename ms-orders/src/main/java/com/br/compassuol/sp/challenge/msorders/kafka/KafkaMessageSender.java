package com.br.compassuol.sp.challenge.msorders.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaMessageSender implements MessageSender {

    private final ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate;

    public KafkaMessageSender(ReplyingKafkaTemplate<Long, String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Object getResult(String request, String topic) throws Exception {
        ProducerRecord<Long, String> record = new ProducerRecord<>(topic, 1L, request);
        RequestReplyFuture<Long, String, Object> replyFuture = kafkaTemplate.sendAndReceive(record);
        SendResult<Long, String> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<Long, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }
}
