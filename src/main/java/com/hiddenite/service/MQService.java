package com.hiddenite.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
  private URI rabbitMqUrl;


  public MQService() {
  }

  public void sendMessageToQueue(String queue, String message)
          throws IOException, TimeoutException {
    try {
      rabbitMqUrl = new URI(System.getenv("RABBITMQ_BIGWIG_REST_API_URL"));
    } catch(URISyntaxException e) {
      e.getStackTrace();
    }
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitMqUrl.getHost());
    factory.setPort(rabbitMqUrl.getPort());
    factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));
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

