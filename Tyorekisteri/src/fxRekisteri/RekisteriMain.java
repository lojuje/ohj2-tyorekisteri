package fxRekisteri;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import rekisteriTietokanta.Tyorekisteri;


/**
 * @author musaj ja jussil
 * @version 13.4.2020
 *
 * Paaohjelma Tyorekisterin kaynnistamiseksi
 */
public class RekisteriMain extends Application {
    
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("RekisteriGUIView.fxml"));
		    final Pane root = (Pane)ldr.load();
		    final RekisteriGUIController tyorekisteriCtrl = (RekisteriGUIController)ldr.getController();
		    final Scene scene = new Scene(root);
		    scene.getStylesheets().add(getClass().getResource("rekisteri.css").toExternalForm());
		    primaryStage.setScene(scene);
		    primaryStage.setTitle("Tyorekisteri");
		    primaryStage.setOnCloseRequest((event) -> {
		        if (!tyorekisteriCtrl.voiSulkea() ) event.consume();
		    });
		    
		    Tyorekisteri tyorekisteri = new Tyorekisteri();
	        tyorekisteriCtrl.setTyorekisteri(tyorekisteri);
	        primaryStage.show();
	        Application.Parameters params = getParameters();
	        
	        if ( params.getRaw().size() > 0 ) {
	            tyorekisteriCtrl.lueTiedosto(params.getRaw().get(0));
	        }
	        else if ( !tyorekisteriCtrl.avaa() ) Platform.exit();
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
	
	
	/**
	 * Kaynnistaa kayttoliittyman
	 * @param args komentorivin parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
