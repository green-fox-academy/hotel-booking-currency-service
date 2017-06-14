/*
package com.hiddenite.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Service
public class MQService {

  Channel channel;
  Connection connection;

  public MQService() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    this.connection = factory.newConnection();
    this.channel = connection.createChannel();
  }

  public void sendMessageToQueue(String queue, String message)
          throws IOException, TimeoutException {
    channel.queueDeclare(queue, false, false, true, null);
    channel.basicPublish("", queue, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");
    channel.close();
    connection.close();
  }

  public int getQueueMessageCount(String queue) throws IOException {
    AMQP.Queue.DeclareOk ok = channel.queueDeclarePassive(queue);
    return ok.getMessageCount();
  }

  public boolean isConnected() {
    return channel.isOpen();

  }

}
*/
