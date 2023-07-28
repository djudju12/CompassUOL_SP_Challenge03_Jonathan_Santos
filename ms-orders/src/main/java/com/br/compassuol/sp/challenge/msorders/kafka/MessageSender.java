package com.br.compassuol.sp.challenge.msorders.kafka;

public interface MessageSender {
    Object getResult(String request, String topic) throws Exception;
}
