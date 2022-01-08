package tijdtools;

import java.io.IOException;
import com.thoughtworks.xstream.XStream;

public class TijdFuncties {
	private Tijd startTijd;
	private Tijd simulatorTijd;
	private Tijd verschil;
	private int interval;
	private int syncInterval;
	private int syncCounter;
	
    public void initSimulatorTijden(int interval, int syncInterval){
    	simulatorTijd=new Tijd(0,0,0);
    	startTijd=getCentralTime();
    	verschil=berekenVerschil(startTijd,simulatorTijd);
    	this.interval=interval;
    	this.syncCounter=syncInterval;
    	this.syncInterval=syncInterval;
    }

    public String getSimulatorWeergaveTijd(){
    	Tijd simulatorWeergaveTijd= simulatorTijd.copyTijd();
    	simulatorWeergaveTijd.increment(verschil);
    	return simulatorWeergaveTijd.toString();
    }
    
    public int getCounter(){
    	return calculateCounter(simulatorTijd);
    }
    
    public int getTijdCounter(){
    	return calculateCounter(simulatorTijd)+calculateCounter(verschil);    	
    }
    
    public void simulatorStep() throws InterruptedException{
		Thread.sleep(interval);
		simulatorTijd.increment(new Tijd(0,0,1));
		syncCounter--;
		if (syncCounter==0){
			syncCounter=syncInterval;
			synchroniseTijd();
		}
    }
    
    private int calculateCounter(Tijd tijd){
    	return tijd.getUur()*3600+tijd.getMinuut()*60+tijd.getSeconde();
    }
    
    private Tijd berekenVerschil(Tijd reverentieTijd, Tijd werkTijd){
    	int urenVerschil = reverentieTijd.getUur()-werkTijd.getUur();
    	int minutenVerschil = reverentieTijd.getMinuut()-werkTijd.getMinuut();
    	int secondenVerschil = reverentieTijd.getSeconde()-werkTijd.getSeconde();
    	if (secondenVerschil<0){
    		minutenVerschil--;
    		secondenVerschil+=60;
    	}
    	if (minutenVerschil<0){
    		urenVerschil--;
    		minutenVerschil+=60;
    	}
    	return new Tijd(urenVerschil, minutenVerschil, secondenVerschil);
    }
    
    private void synchroniseTijd(){
    	Tijd huidigeTijd = getCentralTime();
    	System.out.println("De werkelijke tijd is nu: "+ huidigeTijd.toString());
    	Tijd verwachtteSimulatorTijd = simulatorTijd.copyTijd();
    	verwachtteSimulatorTijd.increment(verschil);
    	Tijd delay = berekenVerschil(huidigeTijd, verwachtteSimulatorTijd);
    	verschil.increment(delay);
    }
    
    private Tijd getCentralTime()
    {
    	try {
    		HTTPFuncties httpFuncties = new HTTPFuncties();
			String result = httpFuncties.executeGet("xml");
	        XStream xstream = new XStream();
	        xstream.alias("Tijd", Tijd.class);
	        Tijd tijd=(Tijd)xstream.fromXML(result);
	        return tijd;

    	} catch (IOException e) {
			e.printStackTrace();
			return new Tijd(0,0,0);
		}
    }
}
