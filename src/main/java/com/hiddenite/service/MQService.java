package com.hiddenite.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Service;

@Service
public class MQService {

  private Channel channel;
  private Connection connection;
  private Consumer consumer;

  public MQService() {
  }

  public void sendMessageToQueue(String queue, String message)
          throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://dmyuipta:WNz9LJE3Dchk9dtnAZEL5jo9v7pETTwO@fish.rmq.cloudamqp.com/dmyuipta");
    connection = factory.newConnection();
    channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, true, null);
    channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");
  }

  public void consume(String queue) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://dmyuipta:WNz9LJE3Dchk9dtnAZEL5jo9v7pETTwO@fish.rmq.cloudamqp.com/dmyuipta");
    connection = factory.newConnection();
    channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, true, null);

    consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(queue, true, consumer);
  }

  public int getQueueMessageCount(String queue) throws IOException {
    AMQP.Queue.DeclareOk ok = channel.queueDeclarePassive(queue);
    return ok.getMessageCount();
  }

  public boolean isConnected() {
    return channel.isOpen();
  }

}

