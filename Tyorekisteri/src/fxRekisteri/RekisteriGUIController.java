package fxRekisteri;


import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import rekisteriTietokanta.Aihealue;
import rekisteriTietokanta.SisaltoException;
import rekisteriTietokanta.Tapahtuma;
import rekisteriTietokanta.Toiminto;
import rekisteriTietokanta.Tyorekisteri;


/**
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 * 
 * Paaikkuna tyorekisterin toiminnassa.
 * 
 */
public class RekisteriGUIController implements Initializable, ModalControllerInterface<String> {
    
    
    @FXML BorderPane paaIkkuna;
    @FXML MenuItem avaaNappi;
    @FXML private StringGrid<?> kalenteri;
    @FXML private BorderPane tulostus;
    @FXML private TextArea kooste;
    @FXML private TextArea koosteAika;
    @FXML private BorderPane Tapahtumat;
    @FXML private TextField HakuKentta;
    @FXML private TextArea haunTulosKentta;
    @FXML private Button muokkaaPainike;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
              initTable();
              setTyorekisteri(new Tyorekisteri());
              alusta();
              muokkaaPainike.setDisable(true);
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
    // PAAIKKUNA
    // --------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Tekee tekstit koosteeseen. EI KAYTETA!
     */
    public void teeKoosteTeksti() {
    String kategoria1 = "Kouluhommat";
      String sisalto1_1 = "Ohjelmointi 2 luento";
      String sisalto2_1 = "Ohjelmointi 2 Paateohjaus";
      String kategoria2 = "Harjoitukset";
      String sisalto1_2 = "Pesapallo harjoitukset";
        
        
      kooste.setText(kategoria1 + "\n" + sisalto1_1 + "\n" + sisalto2_1 + "\n" + "\n" + kategoria2 + "\n" + sisalto1_2);
      kooste.setEditable(false);
    }
    
    
    /**
     * Tekee tekstit koosteen aika-osiosta. EI KAYTETA!
     */
    public void teeKoosteAika() {
        String kategoria1 = "3 h";
          String sisalto1_1 = "1.5 h";
          String sisalto2_1 = "1.5 h";
          String kategoria2 = "2.5 h";
          String sisalto1_2 = "2.5 h";
            
          
          koosteAika.setText(kategoria1 + "\n" + sisalto1_1 + "\n" + sisalto2_1 + "\n" + "\n" + kategoria2 + "\n" + sisalto1_2);
          koosteAika.setEditable(false);
    }
    
    
    /**
     * Alustetaan kalenteri
     */
    public void initTable() {
        String[] otsikot = { "Kello", "Maanantai", "Tiistai", "Keskiviikko", "Torstai", "Perjantai", "Lauantai", "Sunnuntai" };
        kalenteri.initTable(otsikot);
        
        for (int i = 0; i <= 24; i++) {
            
                String a = Integer.toString(i) + ":00";
                String[] rivi = { a, "", "", "", "", "", "" };
                kalenteri.add(rivi);
              
                if (i == 24) {
                    break;
                }
                
                String b = Integer.toString(i) + ":30";
                String[] rivi2 = { b, "", "", "", "", "", "" };
                kalenteri.add(rivi2);
        }
        kalenteri.setEditable(false);
        kalenteri.getSelectionModel().setCellSelectionEnabled(true);
        kalenteri.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
    // MENU - TIEDOSTO valikot
    // --------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Kasitellaan tallennus kasky.
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    /**
     * Kasitellaan kalenterin avaaminen. EI KAYTETA!
     */
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    /**
     * Kasitellaan tulostaminen
     */
    @FXML private void handleTulosta() throws Exception{
        ModalController.showModal(TulostusController.class.getResource("TulostusView.fxml"), "tulostus", null, "");
    }
    
    
    @FXML void handlePoistuminen() {
        Platform.exit();
    }
    
    
    /**
     * Kasitellaan lopetuskasky
     */
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
    // MENU - MUOKKAA valikot
    // --------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Kasitellaan uuden tapahtuman lisaaminen
     */
    @FXML private void handleUusiTapahtuma() throws Exception {
            ModalController.showModal(RekisteriGUIController.class.getResource("TapahtumaIkkuna.fxml"), "Tapahtumat", null, tyorekisteri);
            hae(0);
    }
    
    
    @FXML void handleLisaaTapahtuma() throws Exception {
        handleUusiTapahtuma();
    }
    
    
    /**
     * Kasitellaan tapahtuman muokkaaminen
     */
    @FXML void handleMuokkaa() {
        
        if (getTapahtuma() != null) {
            tyorekisteri.setValittuTapahtuma(getTapahtuma());
            muokkaa = true;
            ModalController.showModal(RekisteriGUIController.class.getResource("TapahtumaIkkuna.fxml"), "Tapahtumat", null, tyorekisteri);
            hae(0);
            muokkaa = false;
            HakuKentta.clear();
            paivanValinta.setValue(null);
        }
        
    }
    
    
    /**
     * Antaa listassa valitun tapahtuman muokkausta/poistamista varten
     */
    private Tapahtuma getTapahtuma() {
        Tapahtuma tap = chooserTapahtumat.getSelectedObject();
        return tap;
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
    // MENU - APUA valikot
    // --------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Kasitellaan avun antaminen
     */
    @FXML private void handleApua() {
        avustus();
    }
    
    
    /**
     * Kasitellaan tietojen antaminen ohjelmasta
     */
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Jarjestelman 7. versio. Tyorekisterin ovat tehneet Musa ja Jussi, Jyvaskylan Yliopiston Ohjelmointi 2 -kurssia varten.", dlg -> {
            dlg.getDialogPane().setPrefWidth(250);
            dlg.getDialogPane().setPrefHeight(150);
          });
    }
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
    // HAKU TOIMINTO
    // --------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Etsii hakua vastaavat tapahtumat
     */
    public void teeHaku() {
       String haunTulos = HakuKentta.getText();
       boolean onkoSama = false;
       
       if (haunTulos.equals("")) {
           hae(0);
       }
       
       else {
           chooserTapahtumat.clear();
           for (int i = 0; i < getTyorekisteri().getTapahtumia(); i++) {
           Tapahtuma tapahtuma = getTyorekisteri().annaTapahtuma(i);
           Toiminto toiminto = getTyorekisteri().annaToiminto(getTyorekisteri().annaTapahtuma(i).getToimintoID());
           if (toiminto.getOtsikko().length() < haunTulos.length()) {
               continue;
           }
               for (int k = 0; k < haunTulos.length(); k++) {
                   if (toiminto.getOtsikko().charAt(k) == haunTulos.charAt(k)) {
                       onkoSama = true;
                   }
                   else {
                       onkoSama = false;
                   }
               }
               if (onkoSama == true) {
                   chooserTapahtumat.add(tapahtuma.annaAloitusPaiva() + " " + tapahtuma.annaAloitusAika() + " " + getTyorekisteri().annaToiminto(tapahtuma.getToimintoID()).getOtsikko(), tapahtuma);
               }
           }
       }
    }

    
    @Override
    public String getResult() {
        return null;
    }

    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    
    // ==================================================================================================================================================
    //===================================================================================================================================================
    
    
    private Tyorekisteri tyorekisteri;
    private Tapahtuma tapahtumaKohdalla;
    @FXML private TextField hakuehto;
    private TextArea areaTapahtuma = new TextArea();
    @FXML private ListChooser<Tapahtuma> chooserTapahtumat;
    @FXML private ScrollPane panelTapahtuma;
    
    
    /**
     * Rekisterin nimi
     */
    public String rekisterinNimi = "Marko";
    
    
    /**
     * Katsotaan onko muokkaaminen hyvaksyttavaa(true) vai ei(false) 
     */
    public static boolean muokkaa = false;
    
    
    /**
     * Tehdaan tarvittavat alustukset
     * Alustetaan tapahtuman kuuntelija
     */
    protected void alusta() {
        panelTapahtuma.setContent(areaTapahtuma);
        areaTapahtuma.setFont(new Font("Courier New", 12));
        panelTapahtuma.setFitToHeight(true);
        
        chooserTapahtumat.clear();
        chooserTapahtumat.addSelectionListener(e -> naytaTapahtuma());
    }
    
    
    /**
     * EI KAYTETA
     * Annetaan tyorekisterille nimi esim. "Tyorekisteri - Marko"
     * @param title rekisterin nimi
     */
    @SuppressWarnings("unused")
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Alustaa rekisterin lukemalla haetun tiedoston nimen
     * @param nimi annettu nimi
     * @return palauttaa null jos homma toimii, antaa virheen jos jokin menee vikaan
     */
    protected String lueTiedosto(String nimi) {
        rekisterinNimi = nimi;
        try {
            getTyorekisteri().lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SisaltoException e) {
            hae(0);
            String virhe = e.getMessage();
            if(virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
     * Tietojen tallennus
     * return null jos onnistuu, muuten virhe tekstina
     */
    private String tallenna() {
        try {
            getTyorekisteri().talleta();
            return null;
        } catch (SisaltoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
            return e.getMessage();
        }
    }

    
    /**
    * Nayttaa listasta valitun tapahtuman tiedot
    */
    protected void naytaTapahtuma() {
        tapahtumaKohdalla = chooserTapahtumat.getSelectedObject();
    
        if (tapahtumaKohdalla == null) return;
    
        areaTapahtuma.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaTapahtuma)) {
             tulosta(os,tapahtumaKohdalla);
        }
    }
    
    
    @FXML void valittuTapahtuma() {
        muokkaaPainike.setDisable(false);
    }
    
    
    /**
    * Hakee tapahtuman tiedot listaan
    * @param id tapahtuman id, joka aktivoidaan haun jälkeen
    */
    public void hae(int id) {
        chooserTapahtumat.clear();
    
        int index = 0;
        for (int i = 0; i < getTyorekisteri().getTapahtumia(); i++) {
            Tapahtuma tapahtuma = getTyorekisteri().annaTapahtuma(i);
            if (tapahtuma.getId() == id) index = i;
            chooserTapahtumat.add(tapahtuma.annaAloitusPaiva() + " " + tapahtuma.annaAloitusAika() + " " + getTyorekisteri().annaToiminto(tapahtuma.getToimintoID()).getOtsikko(), tapahtuma);
        }
        chooserTapahtumat.setSelectedIndex(index); // tasta tulee muutosviesti joka nayttaa tapahtuman
    }
    
    
    /**
     * Hakee toiminnon ID:n
     * @return palauttaa toimintojen maaran listassa int arvona
     */
    public int getToimintoID() {      //tyorekisteriin metodi joka kysyy minkä mittanen lista on toiminnoilla ja aihealueilla --> hakee niiden väliltä 0 ja alkioiden lukumäärä
        int lkm = getTyorekisteri().getToimintoja();
         int nxt;
         
         if (lkm == 1) {
             nxt = 1;
         }
         else {
             Random r = new Random();
             nxt = r.nextInt(lkm - 1) + 1;
         }
        
         return nxt;
    }
    
    
    /**
     * Hakee aihealueiden ID:n
     * @return palauttaa aihealueiden maaran listassa int arvona
     */
    public int getAihealueidenID() {
        int lkm = getTyorekisteri().getAihealueita();
        int nxt;
        
        if (lkm == 1) {
            nxt = 1;
        }
        else {
            Random r = new Random();
            nxt = r.nextInt(lkm - 1) + 1;
        }
        
        return nxt;
    }
    
    
    /**
     * Aikaisemmissa ht:n versioissa luotiin testi tapahtuma. EI KAYTOSSA!
     */
    protected void uusiTapahtuma() {
        Tapahtuma uusi = new Tapahtuma();
        uusi.luoTestiTapahtuma(getToimintoID(), getAihealueidenID());
        getTyorekisteri().lisaa(uusi);
        hae(uusi.getId());
    }
    
    
    /**
     * Aikaisemmissa ht:n versioissa luotiin testi otsikko. EI KAYTOSSA!
     */
    public void uusiToiminto() {
        Toiminto toi = new Toiminto();
        toi.rekisteroi();
        toi.luoTESTI_Toiminto();
        getTyorekisteri().lisaa(toi);
    }
    
    
    /**
     * Aikaisemmissa ht:n versioissa luotiin testi kategoria. EI KAYTOSSA!
     */
    public void uusiAihealue() {
        Aihealue uusi = new Aihealue();
        uusi.rekisteroi();
        uusi.asetaKategoria();
        
        try  {
            getTyorekisteri().lisaa(uusi);
        } catch (SisaltoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
    }
    

    /**
     * Asetetaan tyorekisteri ja naytetaan tapahtumat
     * @param tyorekisteri Tyorekisteri jota kaytetaan tassa kayttoliittymassa
     */
    public void setTyorekisteri(Tyorekisteri tyorekisteri) {
        this.tyorekisteri = tyorekisteri;
        naytaTapahtuma();
    }
    
    
    /**
     * Naytetaan ohjelman suunnitelma erillisessa selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/loppujjy2");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    

    /**
     * Tulostaa tapahtuman tiedot
     * @param os outputstream
     * @param tapahtuma tulostettava tapahtuma
     */
    public void tulosta(PrintStream os, final Tapahtuma tapahtuma) {
        os.print("Nimi: ");
        
        Toiminto toiminto = getTyorekisteri().tapahtumanToiminto(tapahtuma.getToimintoID());
        if (toiminto != null) {
            toiminto.tulosta(os);
        }
        os.print("Kategoria: ");
        
        Aihealue aihealue = getTyorekisteri().tapahtumanAihealue(tapahtuma.getAihealueID());
        if (aihealue != null) {
            aihealue.tulosta(os);
        }
        
        tapahtuma.tulosta(os);
    }
    
    
    /**
     * Tulostaa listassa olevat tapahtumat tekstialueeseen
     * @param tekstiTapahtuma teksti johon tulostetaan
     */
    public void tulostaValitut(TextArea tekstiTapahtuma) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(tekstiTapahtuma)) {
            os.println(" Tulostetaan kaikki tapahtumat");

            for (int i = 0; i < getTyorekisteri().getTapahtumia(); i++) {
                Tapahtuma tapahtuma = getTyorekisteri().annaTapahtuma(i);
                tulosta(os, tapahtuma);
                os.println("\n\n");
            }
        }
    }

    
    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }

    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return false
     */
    public boolean voiSulkea() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Tallennus");
        alert.setHeaderText(null);
        alert.setContentText("Tallennetaanko ennen sulkemista?");
        
        ButtonType kylla = new ButtonType("Kylla", ButtonData.OK_DONE);
        ButtonType ei = new ButtonType("Ei", ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(kylla, ei);
        
        Optional<ButtonType> tulos = alert.showAndWait();
        if (tulos.get() == kylla) {
            Dialogs.showMessageDialog("Tallennetaan"); 
            tallenna();
        }
        return true;
    }
    
    
    /**
     * Kysytaaan tiedoston nimi ja luetaan se
     * @return true
     */
    public boolean avaa() {
        String uusinimi = RekisterinNimiController.kysyNimi(null, rekisterinNimi); //tassa kutsutaan reksiterinNimiControlleria...
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }


    /**
     * @return palauttaa kaytossa olevan tyorekisterin
     */
    public Tyorekisteri getTyorekisteri() {
        return tyorekisteri;
    }
    
    
    @FXML private DatePicker paivanValinta;
    
    
    /**
     * Hakee viikon
     * @return viikon
     */
    private int getViikko() {
        LocalDate pv = paivanValinta.getValue();
        Locale l = Locale.UK;
        int viikko = pv.get(WeekFields.of(l).weekOfYear()); // Kun muokataan tapahtumaa asetetaan tapahtumaikkunan sulkemisen 
        return viikko;                                      // yhteydessa DatePickerin arvokis null (NullPointException tulee riviltä 223)
    }
    
    
    /**
     * Antaa ajan paivan valintaan
     */
    @FXML void aikaOn() {
        
        for (int i = 1; i < 48; i++) {
            for (int j = 1; j < 7; j++) {
                kalenteri.set("", i, j);
            }
        }
        int viikko = getViikko();
        getTapahtumienViikot(viikko);
    }
    
    
    /** 
     * @param viikko kysyinen viikko
     */
    private void getTapahtumienViikot(int viikko) {
        for (int i = 0; i < tyorekisteri.getTapahtumia(); i++) {
            if (viikko == tyorekisteri.annaTapahtuma(i).annaViikkoNumero()) {
                asetaPaiva(tyorekisteri.annaTapahtuma(i));
            }
        }
    }
    
    
    /**
     * @param tapahtuma tapahtuman tiedot kuten aihealueenID, toiminnonID, paivat, kellonajat, viikonnumero
     */
    private void asetaPaiva(Tapahtuma tapahtuma) {
        for (int i = 0; i < 48; i++) {
            String rivi = kalenteri.get(i, 0);
            StringBuilder sb = new StringBuilder(rivi);
            String alku = Mjonot.erota(sb, ':');
            
            StringBuilder sb2 = new StringBuilder(tapahtuma.annaAloitusAika());
            String alku2 = Mjonot.erota(sb2, ':');
            String loppu2 = sb.toString();

            if (alku.equals(alku2)) {
                if (Integer.parseInt(loppu2) < 30) {
                    kalenteri.set(tyorekisteri.annaIdnMukaan(tapahtuma.getAihealueID()).getKategoria(), i, tapahtuma.annaViikonPaivaINT()); //<-- ei osaa laittaa sunnuntaille
                    break;
                }
                if (Integer.parseInt(loppu2) >= 30) {
                    kalenteri.set(tyorekisteri.annaIdnMukaan(tapahtuma.getAihealueID()).getKategoria(), i+1, tapahtuma.annaViikonPaivaINT());
                    break;
                }
            }
        }
    }
    
}