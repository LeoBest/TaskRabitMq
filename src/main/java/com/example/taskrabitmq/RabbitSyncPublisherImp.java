package com.example.taskrabitmq;

import static java.util.Collections.unmodifiableMap;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class RabbitSyncPublisherImp implements RabbitSyncPublisher {

  private final ConnectionFactory factory;

  public int publishMessageAndGetReplyCode(byte[] body, Map<String, String> properties)
      throws IOException {
    int replyCode = 0;

    // Publish the message using RabbitMQ
    try {
      replyCode = publishMessage(body, properties);
    } catch (Exception e) {
      log.error("Error while publishing message", e);
    }

    if (replyCode > 0) {
      RabbitMQErrorCode errorCode = RabbitMQErrorCode.valueOf(String.valueOf(replyCode));
      if (errorCode != null) {
        log.error("Error while publishing message. Error code: {}", errorCode);
        throw new IOException(errorCode.name());
      }
    }

    return replyCode;
  }

  private int publishMessage(byte[] body, Map<String, String> properties)
      throws IOException, TimeoutException {
    var replyCode = 0;

    // Publish message using RabbitMQ
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
      BasicProperties props = getBasicProperties(properties);
      channel.basicPublish("exchangeName", "routingKey", props, body);
    } catch (TimeoutException e) {
      throw new TimeoutException("No response from server");
    }

    return replyCode;
  }


  private BasicProperties getBasicProperties(Map<String, String> properties) {
    String corrId = UUID.randomUUID().toString();
    return new BasicProperties.Builder()
        .correlationId(corrId)
        .headers(unmodifiableMap(properties))
        .build();
  }
}
