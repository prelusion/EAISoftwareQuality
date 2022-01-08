package infoborden;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {
	private String consumerName;
	private Infobord infobord;
	private Berichten berichten;
	
	public QueueListener(String consumerName, Infobord infobord, Berichten berichten) {
		this.consumerName = consumerName;
		this.infobord=infobord;
		this.berichten=berichten;
	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
	            TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
//				System.out.println("Consumer("+consumerName+")");
				berichten.nieuwBericht(text);
				infobord.updateBord();
			} else {
	            System.out.println("Consumer("+consumerName+"): Received: " + message);
	        }
		} 	        catch (JMSException e) {
			e.printStackTrace();
    	}
	}
}

