package infoborden;

import java.io.IOException;
import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;
public class Berichten {

	private HashMap<String,Integer> laatsteBericht = new HashMap<String,Integer>();
	private HashMap<String,JSONBericht> infoBordRegels = new HashMap<String,JSONBericht>();
	private int hashValue;
	private boolean refresh;
	private String[] infoTekstRegels;
	
	public void nieuwBericht(String incoming) {
		try {
			JSONBericht bericht = new ObjectMapper().readValue(incoming, JSONBericht.class);
	    	String busID = bericht.getBusID();
	    	Integer tijd = bericht.getTijd();
	    	if (!laatsteBericht.containsKey(busID) || laatsteBericht.get(busID)<=tijd){
	    		laatsteBericht.put(busID, tijd);
	    		if (bericht.getAankomsttijd()==0){
	    			infoBordRegels.remove(busID);
	    		} else {
	    			infoBordRegels.put(busID, bericht);
	    		}
	    	}
	    	setRegels();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setRegels(){
		String[] infoTekst={"--1--","--2--","--3--","--4--","leeg"};
		int[] aankomsttijden=new int[5];
		int totaalTijden=0;
		int aantalRegels = 0;
		if(!infoBordRegels.isEmpty()){
			for(String busID: infoBordRegels.keySet()){
				JSONBericht regel = infoBordRegels.get(busID);
				int dezeTijd=regel.getAankomsttijd();
				String dezeTekst=regel.getInfoRegel();
				int plaats=aantalRegels;
				for(int i=aantalRegels;i>0;i--){
					if(dezeTijd<aankomsttijden[i-1]){
						aankomsttijden[i]=aankomsttijden[i-1];
						infoTekst[i]=infoTekst[i-1];
						plaats=i-1;
					}
				}
				aankomsttijden[plaats]=dezeTijd;
				infoTekst[plaats]=dezeTekst;
				if(aantalRegels<4){
					aantalRegels++;
				}
			}
		}
		refresh = checkRepaint(aantalRegels, aankomsttijden);
		infoTekstRegels = infoTekst;
	}
	
	private boolean checkRepaint(int aantalRegels, int[] aankomsttijden){
		int totaalTijden=0;
		for(int i=0; i<aantalRegels;i++){
			totaalTijden+=aankomsttijden[i];
		}
		if(hashValue!=totaalTijden){
			hashValue=totaalTijden;
			return true;
		}
		return false;
	}
	
	public boolean hetBordMoetVerverst() {
		return refresh;
	}
	
	public String[] repaintInfoBordValues(){
		return infoTekstRegels;
	}
}
