package infoborden;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tijdtools.InfobordTijdFuncties;

public class Infobord extends Application{
	private String titel = "Bushalte XX in richting YY";
	private Text tijdRegel = new Text("00:00:00");
	private Text infoRegel1 = new Text("De eestevolgende bus");
	private Text infoRegel2 = new Text("De tweede bus");
	private Text infoRegel3 = new Text("De derde bus");
	private Text infoRegel4 = new Text("De vierde bus");
	private String halte;
	private String richting;
	private Berichten berichten;
	
	public Infobord(String halte, String richting) {
		this.titel = "Bushalte " + halte + " in richting " + richting;
		this.halte=halte;
		this.richting=richting;
		this.berichten=new Berichten();
	}
	
	public void verwerkBericht() {
		if (berichten.hetBordMoetVerverst()) {
			String[] infoTekstRegels = berichten.repaintInfoBordValues();
//			Deze code hoort bij opdracht 3
			InfobordTijdFuncties tijdfuncties = new InfobordTijdFuncties();
			String tijd = tijdfuncties.getCentralTime().toString();
			tijdRegel.setText(tijd);
			infoRegel1.setText(infoTekstRegels[0]);
			infoRegel2.setText(infoTekstRegels[1]);
			infoRegel3.setText(infoTekstRegels[2]);
			infoRegel4.setText(infoTekstRegels[3]);
		};
	}

	public void updateBord() {
		Runnable updater = new Runnable() {
			@Override
			public void run() {
				verwerkBericht();	
			}
		};
		Platform.runLater(updater);
	}

	@Override
	public void start(Stage primaryStage) {
		String selector = "halteNaam = '" + halte + "' AND richting = '" + richting + "'";
		thread(new ListenerStarter(selector, this, berichten),false);
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER_LEFT);
		pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		pane.setHgap(5.5);
		pane.setVgap(5.5);

		// Place nodes in the pane
		pane.add(new Label("Voor het laatst bijgewerkt op :"), 0, 0); 
		pane.add(tijdRegel, 1, 0); 
		pane.add(new Label("1:"), 0, 1); 
		pane.add(infoRegel1, 1, 1);
		pane.add(new Label("2:"), 0, 2); 
		pane.add(infoRegel2, 1, 2);
		pane.add(new Label("3:"), 0, 3); 
		pane.add(infoRegel3, 1, 3);
		pane.add(new Label("4:"), 0, 4); 
		pane.add(infoRegel4, 1, 4);
		// Create a scene and place it in the stage
		Scene scene = new Scene(pane,500,150);
		primaryStage.setTitle(titel); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}