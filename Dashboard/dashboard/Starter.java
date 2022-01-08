package dashboard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Starter {

	public static void main(String[] args) {
		Starter main = new Starter();
		main.startDashboard();
	}
	
	void startDashboard() {
		Application application = new Dashboard();
		Platform.startup(() -> {
				try {
					application.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
}
