package fxRekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 * 
 * Kysytaan tyorekisterin nimi.
 *
 */
public class RekisterinNimiController implements ModalControllerInterface<String>{

    
    @FXML private TextField textVastaus;
    private String vastaus = null;
  
      
    @FXML private void handleOK() {
          vastaus = textVastaus.getText();
          ModalController.closeStage(textVastaus);
    }
 
     
    @FXML private void handleCancel() {
         ModalController.closeStage(textVastaus);
    }
  
  
    @Override
    public String getResult() {
          return vastaus;
    }
  
      
    @Override
    public void setDefault(String oletus) {
          textVastaus.setText(oletus);
    }
  
      
    /**
     * Mita tehdaan kun dialogi on naytetty
     */
    @Override
    public void handleShown() {
         textVastaus.requestFocus();
    }
      
      
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mita nimea naytetaan oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
               RekisterinNimiController.class.getResource("RekisterinNimiView.fxml"), "Tyorekisteri", modalityStage, oletus);
    }
    
}