package fxRekisteri;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import rekisteriTietokanta.*;

/**
 * @author musaj ja jussil
 * @version 13.4.2020
 * 
 * Tapahtumien kirjaamista varten oleva ohjelma
 *
 */
public class TapahtumaController implements ModalControllerInterface<Tyorekisteri> {
   
    
    @FXML private BorderPane Tapahtumat;
    @FXML private TextField kategorianNimi;
    @FXML private TextField otsikonNimi;
    @FXML private ListChooser<Aihealue> chooserKategoriat;
    @FXML private ListChooser<Toiminto> chooserOtsikot;
    @FXML private TextField aloitusAikaHH;
    @FXML private TextField aloitusAikaMM;
    @FXML private TextField lopetusAikaHH;
    @FXML private TextField lopetusAikaMM;
    @FXML private DatePicker dateAlkaa;
    @FXML private DatePicker dateLoppuu;
    @FXML private Button poistaNappi;
    private Tyorekisteri tyorekisteri;
    private int[] aloitusPaiva;
    private int[] lopetusPaiva;
    
    
    /**
     * Kasitellaan tallennuskasky
     */
    @FXML private void handleTallennaTapahtuma() {
        LocalDate alkamisPaiva = dateAlkaa.getValue(); //muodossa vv-kk-pv
        LocalDate paattymisPaiva = dateLoppuu.getValue(); //muodossa vv-kk-pv
        
        if (alkamisPaiva != null && paattymisPaiva != null) {
            this.aloitusPaiva = aloitsuPaivaTaulukkoon(alkamisPaiva);
            this.lopetusPaiva = lopetusPaivaTaulukkoon(paattymisPaiva);
            tallennaTapahtuma();
        }
        else {
            Dialogs.showMessageDialog("Ei tallenneta! Tarkista alkamis ja paattymispaivat.");
        }
    }
    
    
    /**
     * Aloitus paiva taulukko
     * @param alkamisPaiva tapahtuman aloitus paiva
     * @return taulukon jossa aloitus paiva pilkottu: pv, kk, vv
     */
    public int[] aloitsuPaivaTaulukkoon(LocalDate alkamisPaiva) {
        int[] t = new int[3];
        t[0] = alkamisPaiva.getDayOfMonth();
        t[1] = alkamisPaiva.getMonthValue();
        t[2] = alkamisPaiva.getYear();
        
        return t;
    }
    
    
    /**
     * Lopetus paiva taulukko
     * @param lopetusPaivaa tapahtuman lopetus paiva
     * @return taulukon jossa lopetus paiva pilkottu: pv, kk, vv
     */
    public int[] lopetusPaivaTaulukkoon(LocalDate lopetusPaivaa) {
        
        int[] t = new int[3];
        
        t[0] = lopetusPaivaa.getDayOfMonth();
        t[1] = lopetusPaivaa.getMonthValue();
        t[2] = lopetusPaivaa.getYear();
        
        return t;
    }

    
    /**
     * Tietojen tallennus
     */
    private void tallennaTapahtuma() {
        if (RekisteriGUIController.muokkaa == true) {
            Tapahtuma muokattavaTap = getResult().valittuTapahtuma;
            muokattavaTap = new Tapahtuma(getAloitusAika(), aloitusPaiva, getLopetusAika(), lopetusPaiva, getAihealueenID(), getToimintoID(), getVkoNro(), getViikonPv());
            muokattavaTap.rekisteroi(getResult().valittuTapahtuma.getId());
            getResult().korvaa(muokattavaTap);
            Dialogs.showMessageDialog("Tapahtuma muokattu");
            return;
        }
        
        if (getAloitusAika() != null && getLopetusAika() != null && chooserKategoriat.getSelectedObject() != null && chooserOtsikot.getSelectedObject() != null ) {
            Tapahtuma tap = new Tapahtuma(getAloitusAika(), aloitusPaiva, getLopetusAika(), lopetusPaiva, getAihealueenID(), getToimintoID(), getVkoNro(), getViikonPv());
            tap.rekisteroi();
            tyorekisteri.lisaa(tap);
            Dialogs.showMessageDialog("Uusi tapahtuma tallennettu");
        }
        else {
            Dialogs.showMessageDialog("Ei tallenneta! Tayta kaikkia kentat tallentaaksesi tapahtuman.");
        }   
    }
    
    
    /**
     * Hakee viikonpaivan
     * @return palauttaa viikonpaivan numerona 
     * Esim. tiistai palauttaisi 2
     */
    private int getViikonPv() {
        LocalDate pv = dateAlkaa.getValue();
        DayOfWeek viikonPaiva = pv.getDayOfWeek();
        int viikonPv = viikonPaiva.getValue();
        return viikonPv;
    }


    /**
     * Hakee viikonnumeron
     * @return palauttaa viikonnumeron
     * Esim. paivamaaralta 14.4.2020 palautettaisiin viikonnumero 16
     */
    private int getVkoNro() {
        LocalDate pv = dateAlkaa.getValue();
        Locale l = Locale.UK;
        int viikko = pv.get(WeekFields.of(l).weekOfYear());
        return viikko;
    }


    /**
     * Hoitaa uuden kategorian lisaamisen
     * @throws SisaltoException e
     */
    @FXML void handleLisaaKategoria() throws SisaltoException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Uuden kategorian tallentaminen");
        dialog.setTitle("Uusi kategoria");
        dialog.setContentText("Nimi:");
        Optional<String> vastaus = dialog.showAndWait();
        String uusiNimi = vastaus.get();
        tallennaKategoria(uusiNimi);
    }

    
    /**
     * Hoitaa uuden otsikon lisaamisen
     */
    @FXML void handleLisaaOtsikko() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Uuden otsikon tallentaminen");
        dialog.setTitle("Uusi otsikko");
        dialog.setContentText("Nimi:");
        Optional<String> vastaus = dialog.showAndWait();
        String uusiNimi = vastaus.get();
        tallennaOtsikko(uusiNimi);
    }
    
    
    /**
     * Alustetaan kategoriat kategorioiden listaan
     */
    private void alustaKategoriat() {
        chooserKategoriat.clear();
       
        List<Aihealue> aihealueet = (tyorekisteri.annaAihealueet());
        
        for (int i = 0; i < aihealueet.size(); i++) {
            chooserKategoriat.add(aihealueet.get(i).getKategoria(), aihealueet.get(i));
        }
    }
    
    
    /**
     * Alustetaan otsikot otsikkojen listaan
     */
    private void alustaOtsikot() {
        chooserOtsikot.clear();

        List<Toiminto> toiminnot = (tyorekisteri.annaToiminnat());
            
        for (int i = 0; i < toiminnot.size(); i++) {
            chooserOtsikot.add(toiminnot.get(i).getOtsikko(), toiminnot.get(i));
        }   
    }
    
    
    /**
     * Hoitaa uuden otsikon tallennuksen
     * @param otsikko annettu otsikon nimi
     */
    public void tallennaOtsikko(String otsikko) {
        boolean loytyyko = false;
        
        List<Toiminto> otsikot = tyorekisteri.annaToiminnat();
        
        for (int i = 0; i < otsikot.size(); i++) {
            if (otsikot.get(i).getOtsikko().equals(otsikko)) {
                loytyyko = true;
                break;
            }
        }
        
        if (otsikko.equals("") || otsikko.charAt(0) == ' ') {
            Dialogs.showMessageDialog("Nimi ei kelpaa. Ei tallenneta!");
        }
        else if (loytyyko == true) {
            Dialogs.showMessageDialog("Nimi on jo otsikoissa. Ei tallenneta!");
        }
        else {
            Dialogs.showMessageDialog("Tallennetaan uusi otsikko: \n" + otsikko);
            Toiminto toi = new Toiminto(otsikko);
            toi.rekisteroi();
            tyorekisteri.lisaa(toi);
            alustaOtsikot();
        }
    }
    
    
    /**
     *
     * Hoitaa uuden kategorian tallennuksen
     * @param kategoria annettu kategorian nimi
     * @throws SisaltoException e
     */
    public void tallennaKategoria(String kategoria) throws SisaltoException {
        boolean loytyyko = false;
        
        List<Aihealue> kategoriat = tyorekisteri.annaAihealueet();
        
        for(int i = 0; i < kategoriat.size(); i++) {
            if (kategoriat.get(i).getKategoria().equals(kategoria)) {
                loytyyko = true;
                break;
            }
        }
        
        if(kategoria.equals("") || kategoria.equals(" ")) {
            Dialogs.showMessageDialog("Nimi ei kelpaa. Ei tallenneta!");
        }
        else if(loytyyko == true) {
            Dialogs.showMessageDialog("Nimi on jo kategorioissa. Ei tallenneta!");
        }
        else {
            Dialogs.showMessageDialog("Tallennetaan uusi kategoria: \n" + kategoria);
            Aihealue aih = new Aihealue(kategoria);
            aih.rekisteroi();
            tyorekisteri.lisaa(aih);
            alustaKategoriat();
        }
    }
    
    
    /**
     * Kasittelee aloitus ajan tuntien nayttamista
     */
    @FXML void handleAikaHH() {
        int aloitus;
        
        try {
            aloitus = Integer.parseInt(aloitusAikaHH.getText());
        } catch (NumberFormatException nfe) {
            aloitusAikaHH.setText("");
            return;
        }
        
        if (aloitus < 0 || aloitus > 23) {
            aloitusAikaHH.setText(""); //tassa voisi olla myos etta muutetaan kentta punaiseksi
        }
    }
    
    
    /**
     * Kasittelee lopetus ajan tuntien nayttamista
     */
    @FXML void handleAikaHH2() {
        int lopetus;
        
        try {
            lopetus = Integer.parseInt(lopetusAikaHH.getText());
        } catch (NumberFormatException nfe) {
            lopetusAikaHH.setText("");
            return;
        }
        
        if (lopetus < 0 || lopetus > 23) {
            lopetusAikaHH.setText("");
        }
    }
    
    
    /**
     * Kasittelee aloitus ajan minuuttien nayttamista
     */
    @FXML void handleAikaMM() {
        int aloitus;
        
        try {
            aloitus = Integer.parseInt(aloitusAikaMM.getText());
        } catch (NumberFormatException nfe) {
            aloitusAikaMM.setText("");
            return;
        }
        
        if (aloitus < 0 || aloitus > 59) {
            aloitusAikaMM.setText("");
        }
    }
    
    
    /**
     * Kasittelee lopetus ajan minuuttien nayttamista
     */
    @FXML void handleAikaMM2() {
        int lopetus;
        
        try {
            lopetus = Integer.parseInt(lopetusAikaMM.getText());
        } catch (NumberFormatException nfe) {
            lopetusAikaMM.setText("");
            return;
        }
        
        if (lopetus < 0 || lopetus > 59) {
            lopetusAikaMM.setText("");
        }
    }
    
    
    /**
     * Antaa aloitus ajan taulukossa
     * @return taulukko aloitus ajoista
     */
    private int[] getAloitusAika() {
        if (aloitusAikaHH.getText().equals("") || aloitusAikaMM.getText().equals("")) {
            return null;
        }
        
        int[] t = new int[2];
        t[0] = Integer.parseInt(aloitusAikaHH.getText());
        t[1] = Integer.parseInt(aloitusAikaMM.getText());
        
        return t;
    }
    
    
    /**
     * Antaa lopetus ajan taulukossa
     * @return taulukko lopetus ajoista
     */
    private int[] getLopetusAika() {
        if (lopetusAikaHH.getText().equals("") || lopetusAikaMM.getText().equals("")) {
            return null;
        }
        
        int[] t = new int[2];
        t[0] = Integer.parseInt(lopetusAikaHH.getText());
        t[1] = Integer.parseInt(lopetusAikaMM.getText());
        
        return t;
    }
    
    
    /**
     * Antaa aihealueen id:n
     * @return aihealueen id
     */
    private int getAihealueenID() {
        Aihealue aihealueKohdalla = chooserKategoriat.getSelectedObject();
        return aihealueKohdalla.getID();
    }
    
    
    /**
     * Antaa toiminnon id:n
     * @return toiminnon id
     */
    private int getToimintoID() {
        Toiminto toimintoKohdalla = chooserOtsikot.getSelectedObject();
        return toimintoKohdalla.getId();
    }
    

    /**
     * Poistaa tapahtuman
     */
    @FXML private void handlePoistaTapahtuma() {
        poistaTapahtuma();
    }
    
    
    /**
     * Tapahtuman poistaminen
     */
    private void poistaTapahtuma() {
        getResult().poista(getResult().valittuTapahtuma);
        Dialogs.showMessageDialog("Tapahtuma poistettu.");
    }
    
    
    /**
     * Asettaa tapahtuman tiedot valmiiksi tapahtuman muokkausikkunaan
     * @param valittuTapahtuma tapahtuma jonka mukaan tiedot asetetaan
     */
    private void asetaTapahtuma(Tapahtuma valittuTapahtuma) {
        int[] aPaivat = valittuTapahtuma.getAloitusPaiva();
        int[] lPaivat = valittuTapahtuma.getLopetusPaiva();
        
        aloitusAikaHH.setText(valittuTapahtuma.annaAloitusAikaHH());;
        aloitusAikaMM.setText(valittuTapahtuma.annaAloitusAikaMM());
        lopetusAikaHH.setText(valittuTapahtuma.annaLopetusAikaHH());
        lopetusAikaMM.setText(valittuTapahtuma.annaLopetusAikaMM());
        dateAlkaa.setValue(LocalDate.of(aPaivat[2], aPaivat[1], aPaivat[0]));
        dateLoppuu.setValue(LocalDate.of(lPaivat[2], lPaivat[1], lPaivat[0]));
        chooserKategoriat.setSelectedIndex(valittuTapahtuma.getAihealueID() - 1);
        chooserOtsikot.setSelectedIndex(valittuTapahtuma.getToimintoID() - 1);
    }

    

    /**
     * N‰ytt‰‰ tapahtumaikkunan
     */
    @Override
    public void handleShown() {
        alustaKategoriat();
        alustaOtsikot();
        
        if(RekisteriGUIController.muokkaa == true) {
            asetaTapahtuma(getResult().getValittuTapahtuma());
            poistaNappi.setOpacity(1);
            poistaNappi.setDisable(false);
        }
        else {
            poistaNappi.setDisable(true);
        }
    }


    @Override
    public Tyorekisteri getResult() {
        return tyorekisteri;
    }

    
    @Override
    public void setDefault(Tyorekisteri oletus) {
        tyorekisteri = oletus;
    }
    
}
