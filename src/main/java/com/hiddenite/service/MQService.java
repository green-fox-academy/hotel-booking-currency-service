package com.hiddenite.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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

  public MQService() {
  }

  public void sendMessageToQueue(String queue, String message)
          throws IOException, TimeoutException, URISyntaxException, KeyManagementException, NoSuchAlgorithmException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("dmyuipta");
    factory.setPassword("WNz9LJE3Dchk9dtnAZEL5jo9v7pETTwO");
    factory.setHost("fish.rmq.cloudamqp.com");
    factory.setUri(new URI("amqp://dmyuipta:WNz9LJE3Dchk9dtnAZEL5jo9v7pETTwO@fish.rmq.cloudamqp.com/dmyuipta"));
//    factory.setHost("localhost");
    connection = factory.newConnection();
    channel = connection.createChannel();
    channel.queueDeclare(queue, false, false, true, null);
    channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");
//    channel.close();
//    connection.close();
  }

  public int getQueueMessageCount(String queue) throws IOException {
    AMQP.Queue.DeclareOk ok = channel.queueDeclarePassive(queue);
    return ok.getMessageCount();
  }

  public boolean isConnected() {
    return channel.isOpen();
  }

}

