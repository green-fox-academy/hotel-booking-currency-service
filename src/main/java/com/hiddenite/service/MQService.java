package com.hiddenite.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Service
public class MQService {

  public void sendMessageToQueue(String queue, String message)
      throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(queue, false, false, false, null);
    channel.basicPublish("", queue, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();


  }



}
