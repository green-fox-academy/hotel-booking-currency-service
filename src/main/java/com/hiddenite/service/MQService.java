//package com.hiddenite.service;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.util.concurrent.TimeoutException;
//
//import com.rabbitmq.client.AMQP;
//import org.springframework.stereotype.Service;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.Channel;
//
//@Service
//public class MQService {
//
//  Channel channel;
//  Connection connection;
//
//  public MQService() {
//  }
//
//  public void sendMessageToQueue(String queue, String message)
//          throws IOException, TimeoutException {
//    ConnectionFactory factory = new ConnectionFactory();
////    factory.setUsername("");
////    factory.setPassword("");
//    factory.setHost("localhost");
////    factory.setUri(new URI(""));
//
//
//
//
//
//
//    connection = factory.newConnection();
//    channel = connection.createChannel();
//    channel.queueDeclare(queue, false, false, true, null);
//    channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
//    System.out.println(" [x] Sent '" + message + "'");
////    channel.close();
////    connection.close();
//  }
//
//  public int getQueueMessageCount(String queue) throws IOException {
//    AMQP.Queue.DeclareOk ok = channel.queueDeclarePassive(queue);
//    return ok.getMessageCount();
//  }
//
//  public boolean isConnected() {
//    return channel.isOpen();
//  }
//
//}
//
