package com.hiddenite.service;

import com.google.gson.Gson;
import com.hiddenite.model.Event;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@Service
public class MQService {

  private ConnectionFactory factory;
  private Connection connection;
  private Channel channel;
  private Consumer consumer;

  public MQService() throws NoSuchAlgorithmException,
          KeyManagementException,
          URISyntaxException, IOException, TimeoutException {
    this.factory = new ConnectionFactory();
    this.factory.setUri(System.getenv("CURRENCY_MQ_URI"));
    connection = this.factory.newConnection();
  }

  public void sendMessageToQueue(String queue, String message)
          throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    channel = connection.createChannel();
    Event event = new Event(message);
    String eventToJson = new Gson().toJson(event);
    channel.basicPublish("", queue, null, eventToJson.getBytes());
    System.out.println(" [x] Sent '" + eventToJson + "'");
  }

  public void consume(String queue) throws Exception {
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

