package com.example.taskrabitmq;

public enum RabbitMQErrorCode {

  OK(200),
  INVALID_EXCHANGE_NAME(311),
  INVALID_QUEUE_NAME(312),
  INVALID_ROUTING_KEY(313),
  INVALID_PAYLOAD_SIZE(320),
  INVALID_PROPERTIES(402),
  INVALID_PROPERTY_VALUE(403),
  INVALID_PROPERTY_NAME(404),
  INVALID_PROPERTY_FLAG(405),
  INVALID_PROPERTY_TYPE(406),
  INTERNAL_ERROR(501);

  private int code;

  RabbitMQErrorCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}

