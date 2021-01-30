package fxRekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author musaj ja jussil
 * @version 13.4.2020
 * 
 * Tulostusta hoitava luokka
 *
 */
public class TulostusController implements ModalControllerInterface<String>{

    
    @FXML private Button OKnappi;
    
    
    /**
     * Sulkee tulostusikkunan
     */
    @FXML void OK() {
        Stage stage = (Stage) OKnappi.getScene().getWindow();
        stage.close();
    }

    
    /**
     * Tulostaa
     */
    @FXML void tulosta() {
        Dialogs.showMessageDialog("Ei osata viela tulostaa!");
    }

    
    @Override
    public String getResult() {
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
    }

    
    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
    }
    
}
