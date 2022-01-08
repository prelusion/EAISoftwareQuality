package bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import tijdtools.TijdFuncties;

public class Runner implements Runnable {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	private static int syncInterval=5;

	private static void addBus(int starttijd, Bus bus){
		ArrayList<Bus> bussen = new ArrayList<Bus>();
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}

	private static int startBussen(int tijd){
		for (Bus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}

	public static void moveBussen(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			boolean eindpuntBereikt = bus.move();
			if (eindpuntBereikt) {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}

	public static int initBussen(){
		Bus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
		Bus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, 1);
		Bus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1);
		Bus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
		Bus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
		Bus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, 1);
		Bus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, 1);
		Bus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1);
		Bus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
		Bus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
		Bus bus11=new Bus(Lijnen.LIJN8, Bedrijven.QBUZZ, 1);
		Bus bus12=new Bus(Lijnen.LIJN8, Bedrijven.QBUZZ, 1);
		Bus bus13=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1);
		Bus bus14=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1);
		Bus bus15=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1);
		addBus(3, bus1);
		addBus(5, bus2);
		addBus(4, bus3);
		addBus(6, bus4);	
		addBus(3, bus5);
		addBus(5, bus6);
		addBus(4, bus7); 
		addBus(6, bus8);	
		addBus(12, bus9); 
		addBus(10, bus10);	
		addBus(3, bus11);
		addBus(5, bus12);
		addBus(14, bus13);
		addBus(16, bus14);	
		addBus(13, bus15);
		Bus bus21=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
		Bus bus22=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, -1);
		Bus bus23=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1);
		Bus bus24=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
		Bus bus25=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
		Bus bus26=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, -1);
		Bus bus27=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, -1);
		Bus bus28=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1);
		Bus bus29=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
		Bus bus30=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
		Bus bus31=new Bus(Lijnen.LIJN8, Bedrijven.QBUZZ, -1);
		Bus bus32=new Bus(Lijnen.LIJN8, Bedrijven.QBUZZ, -1);
		Bus bus33=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1);
		Bus bus34=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1);
		Bus bus35=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1);
		addBus(3, bus21);
		addBus(5, bus22);
		addBus(4, bus23);
		addBus(6, bus24);	
		addBus(3, bus25);
		addBus(5, bus26);
		addBus(4, bus27); 
		addBus(6, bus28);	
		addBus(12, bus29); 
		addBus(10, bus30);	
		addBus(3, bus31);
		addBus(5, bus32);
		addBus(14, bus33);
		addBus(16, bus34);	
		addBus(13, bus35);
		return Collections.min(busStart.keySet());
	}

//	@Override
//	public void run() {
//		int tijd=0;
//		int volgende = initBussen();
//		while ((volgende>=0) || !actieveBussen.isEmpty()) {
//			System.out.println("De tijd is:" + tijd);
//			volgende = (tijd==volgende) ? startBussen(tijd) : volgende;
//			moveBussen(tijd);
//			sendETAs(tijd);
//			try {
//				Thread.sleep(interval);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			tijd++;
//		}
//	}
//	Om de tijdsynchronisatie te gebruiken moet de onderstaande run() gebruikt worden
//
	@Override
	public void run() {
		int tijd=0;
		int counter=0;
		TijdFuncties tijdFuncties = new TijdFuncties();
		tijdFuncties.initSimulatorTijden(interval,syncInterval);
		int volgende = initBussen();
		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			counter=tijdFuncties.getCounter();
			tijd=tijdFuncties.getTijdCounter();
			System.out.println("De tijd is:" + tijdFuncties.getSimulatorWeergaveTijd());
			volgende = (counter==volgende) ? startBussen(counter) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			try {
				tijdFuncties.simulatorStep();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
