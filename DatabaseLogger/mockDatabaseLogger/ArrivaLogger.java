package mockDatabaseLogger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.thoughtworks.xstream.XStream;

public class ArrivaLogger implements Runnable{

	@Override
	public void run() {

		try {
			ActiveMQConnectionFactory connectionFactory = 
					new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("ARRIVALOGGER");
			MessageConsumer consumer = session.createConsumer(destination);
			boolean newMessage=true;
			int aantalBerichten=0;
			int aantalETAs=0;
			while (newMessage) {
				Message message = consumer.receive(2000);
				newMessage=false;
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					newMessage=true;
					XStream xstream = new XStream();
					xstream.alias("Bericht", Bericht.class);
					xstream.alias("ETA", ETA.class);
					Bericht bericht=(Bericht)xstream.fromXML(text);
					aantalBerichten++;
					aantalETAs+=bericht.ETAs.size();
				} else {
					System.out.println("Received: " + message);
				}            	
			}
			consumer.close();
			session.close();
			connection.close();
			System.out.println(aantalBerichten + " berichten met " + aantalETAs + " ETAs verwerkt.");
		} catch (Exception e) {
			System.out.println("Caught: " + e);
			e.printStackTrace();
		}
	}
}
