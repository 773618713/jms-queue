package com.scy.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 创建一个生产者，发送消息
 * @author Scy
 * @date 2018年5月10日
 */
public class AppProduce {
	public static final String url = "tcp://182.254.247.119:61616";
	//public static final String url = "failover://(tcp://182.254.247.119:61616,tcp://182.254.247.119:61616)?initialReconnectDelay=100&rando mize=false";

	public static final String queueName =  "queque-test";
	
	public static void main(String[] args) {
		try {
			//创建连接工厂
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			//创建Connection
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建会话 第一个参数：是否在事物中处理，第二个参数应答模式
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//创建一个目标
			Destination destination = session.createQueue(queueName);
			//创建一个生产者
			MessageProducer producer =  session.createProducer(destination);
			for (int i = 0; i < 100; i++) {
				TextMessage textMessage = session.createTextMessage("testMessage:" + i);
				producer.send(textMessage);
				System.out.println("发送消息:"+textMessage.getText());
			}
			//关闭连接
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
