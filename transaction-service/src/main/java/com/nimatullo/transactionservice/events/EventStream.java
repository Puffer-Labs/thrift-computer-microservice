package com.nimatullo.transactionservice.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EventStream {
    String INBOUND = "payment-response";
    String OUTBOUND = "payment-request";

    @Input(INBOUND)
    SubscribableChannel consumer();

    @Output(OUTBOUND)
    MessageChannel producer();
}
