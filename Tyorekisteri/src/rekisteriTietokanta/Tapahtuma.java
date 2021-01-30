package rekisteriTietokanta;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tapahtuma
 * 
 * @author musaj ja jussil
 * @version 14.4.2020
 *
 *Vastuualueet: 
 *      - Tietaa
 *          -ID
 *          -aika(h:m-h:m)
 *          -paivamaara
 *          - aihealueet.id
 *          - tapahtumat.id
 *          
 *      - Osaa tehda ja kasitella aika ja paivamaara tietoja
 */
public class Tapahtuma {
    

    private int id;
    private int[] aloitusAika = new int[2]; // String hh + ":" + String mm
    private int[] aloitusPaiva = new int[3]; //String p + ":" + String kk + ":" + String v
    
    private int[] lopetusAika = new int[2]; // String hh + ":" + String mm
    private int[] lopetusPaiva = new int[3]; //String p + ":" + String kk + ":" + String v
    private int viikkoNro;
  
    private int aihealueID;
    private int toimintoID;
    
    private static int seuraavaNro = 1;
    private int viikonPaiva;
    
    
    /**
     * Alustetaan tapahtuma
     */
    public Tapahtuma() {
        //ei tarvitse mitaan
    }
    
    
    /**
     * Tarvittavia paivamaaria ja aikoja 
     * @param aloitusAika Tapahtuman aloitusaika
     * @param aloitusPaiva Tapahtuman aloituspaiva
     * @param lopetusAika Tapahtuman lopetusaika
     * @param lopetusPaiva Tapahtuman lopetus paiva
     * @param aihealueID aihealueen ID
     * @param toimintoID toiminnon ID
     * @param vkoNro tapahtuman viikkonumero
     * @param vp tapahtuman viikonpaiva
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.getAihealueID() === 1;
     * uusi.getToimintoID() === 1;
     * uusi.getLopetusAika().equals(lopetus);
     * uusi.getAloitusAika().equals(aloitus);
     * uusi.getAloitusPaiva().equals(aloitusP);
     * uusi.getLopetusPaiva().equals(lopetusP);
     * uusi.annaViikkoNumero() === 15;
     * uusi.annaViikonPaivaINT() === 3;
     * </pre>
     */
    public Tapahtuma(int[] aloitusAika, int[] aloitusPaiva, int[] lopetusAika, int[] lopetusPaiva, int aihealueID, int toimintoID, int vkoNro, int vp) {
        
        this.aloitusAika = aloitusAika;
        this.aloitusPaiva = aloitusPaiva;
        this.lopetusAika = lopetusAika;
        this.lopetusPaiva = lopetusPaiva;
        this.aihealueID = aihealueID;
        this.toimintoID = toimintoID;
        this.viikkoNro  = vkoNro;
        this.viikonPaiva = vp;
    }
    
    
    /**
     * Alustetaan tapahtuma toiminnon ja aihealueen id:lla
     * @param toimintoID toiminnon viitenumero eli id
     * @param aihealueID aihealueen viitenumero eli id
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma(1, 2), t2 = new Tapahtuma(2, 1);
     * t.getToimintoID() === 1;
     * t2.getToimintoID() === 2;
     * t.getAihealueID() === 2;
     * t2.getAihealueID() === 1;
     * </pre>
     */
    public Tapahtuma(int toimintoID, int aihealueID) {
        this.toimintoID = toimintoID;
        this.aihealueID = aihealueID;
    }
    
    
    /**
     * Alustetaan testi tapahtuma ja annetaan tapahtumalle toiminnon ja aihealueen id:t
     * @param toimintojenID toiminnon viitenumero eli id
     * @param aihealueidenID aihealueen viitenumero eli id
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma(), t2 = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t2.luoTestiTapahtuma(2, 1);
     * t.getId() === 1;
     * t2.getId() === 2;
     * </pre>
     */
    public void luoTestiTapahtuma(int toimintojenID, int aihealueidenID) {
        id = rekisteroi();
        aloitusAika[0] = 12;
        aloitusAika[1] = 30;
        aloitusPaiva[0] = 3;
        aloitusPaiva[1] = 4;
        aloitusPaiva[2] = 2020;
        
        lopetusAika[0] = 15;
        lopetusAika[1] = 0;
        lopetusPaiva[0] = 3;
        lopetusPaiva[1] = 4;
        lopetusPaiva[2] = 2020;
        
        this.aihealueID = aihealueidenID;
        this.toimintoID = toimintojenID;
    }
    
    
    /**
     * Asetetaan tapahtumalla yksiloity id
     * seuraavaNro kasvaa aina yhdella jolloin saadaan kullekin tapahtumalle eri id
     * @return palauttaa tapahtumalle annetun idn
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma(), t2 = new Tapahtuma();
     * t.rekisteroi();
     * t2.rekisteroi();
     * t.getId() === 12;
     * t2.getId() === 13;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    
    /**
     * Rekisteroidaan tapahtuma annetulla id:lla
     * @param haluttuID joka annetaan tapahtumalle
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.rekisteroi(1000);
     * t.getId() === 1000;
     * </pre>
     */
    public void rekisteroi(int haluttuID) {
        id = haluttuID;
    }
    
    
    /**
     * Tulostetaan tapahtuman tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        String aloitusAikaMuodossa = Integer.toString(aloitusAika[1]);
        
        if (aloitusAika[1] == 0) {
            aloitusAikaMuodossa = Integer.toString(aloitusAika[1]) + "0";
        }
        
        String lopetusAikaMuodossa = Integer.toString(lopetusAika[1]);
        
        if (lopetusAika[1] == 0) {
            lopetusAikaMuodossa = Integer.toString(lopetusAika[1]) + "0";
        }
        out.println("Aika: " + aloitusAika[0] + ":" + aloitusAikaMuodossa + "-" + lopetusAika[0] + ":" + lopetusAikaMuodossa);
        out.println("Paiva: " + paivaMaaratOn());

        //out.println(id + " | " + aloitusAika[0] + ":" + aloitusAikaMuodossa + "-" + lopetusAika[0] + ":" + lopetusAikaMuodossa + " | " + paivaMaaratOn()  + " | " + toimintoID + " | " + aihealueID);
    }
    
    
    /**
     * Antaa tapahtuman ajan 
     * @return antaa aloitus - lopetus ajan+paivan merkkijonona
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.annaAika() === "12:30 3.4.2020 - 15:0 3.4.2020";
     * </pre>
     */
    public String annaAika() {
        
        String aika = aloitusAika[0] + ":" + aloitusAika[1] + " " + aloitusPaiva[0] + "." + aloitusPaiva[1] + "." + aloitusPaiva[2] + " - " + lopetusAika[0] + ":" + lopetusAika[1] + " " + lopetusPaiva[0] + "." + lopetusPaiva[1] + "." + lopetusPaiva[2];

        return aika;
    }
    
    
    /**
     * @return antaa tapahtuman viikkonumeron
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaViikkoNumero() === 15;
     */
    public int annaViikkoNumero() {
        return viikkoNro;
    }
    
    
    
    /**
     * Antaa tapahtuman paivamaaran
     * Jos aloitus- ja lopetuspaiva on sama antaa vain ko. paivan. Muussa tapauksessa palauttaa aloitsupaivan-lopetuspaivan
     * @return palauttaa paivamaarat tulostusta varten
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.paivaMaaratOn() === "3.4.2020";
     * </pre>
     */
    public String paivaMaaratOn() {
        
        String tulostus = "";
        
        if (aloitusPaiva[0] == lopetusPaiva[0] && aloitusPaiva[1] == lopetusPaiva[1] && aloitusPaiva[2] == lopetusPaiva[2]) {
            tulostus = aloitusPaiva[0] + "." + aloitusPaiva[1] + "." + aloitusPaiva[2];
        }
        else {
            tulostus = aloitusPaiva[0] + "." + aloitusPaiva[1] + "." + aloitusPaiva[2] + "-" + lopetusPaiva[0] + "." + lopetusPaiva[1] + "." + lopetusPaiva[2];
        }
        
        return tulostus;
    }
    
    
    /**
     * Antaa tapahtuman aloituspaivan merkkijonona
     * @return palauttaa aloitsupaivan
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.annaAloitusPaiva() === "3.4.2020";
     * </pre>
     */
    public String annaAloitusPaiva() {
        return aloitusPaiva[0] + "." + aloitusPaiva[1] + "." + aloitusPaiva[2];
    }
    
    
    /**
     * Antaa tapahtuman lopetuspaivan merkkijonona
     * @return palauttaa lopetuspaivan
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.annaLopetusPaiva() === "3.4.2020";
     * </pre>
     */
    public String annaLopetusPaiva() {
        return lopetusPaiva[0] + "." + lopetusPaiva[1] + "." + lopetusPaiva[2];
    }
    
    
    /**
     * Antaa tapahtuman aloitusajan merkkijonona. Tunnit ja minuutit on erotettu : -merkilla
     * hh:mm
     * @return palauttaa aloistuajan
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.annaAloitusAika() === "12:30";
     * </pre>
     */
    public String annaAloitusAika() {
        return aloitusAika[0] + ":" + aloitusAika[1];
    }
    
    
    /**
     * Antaa tapahtuman lopetusajan merkkijonona. Tunnit ja minuutit on erotettu : -merkilla
     * hh:mm
     * @return palauttaa lopetusajan
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma();
     * t.luoTestiTapahtuma(1, 2);
     * t.annaLopetusAika() === "15:0";
     * </pre>
     */
    public String annaLopetusAika() {
        return lopetusAika[0] + ":" + lopetusAika[1];
    }
    
    
    /**
     * Antaa tapahtuman aloitusajan tunnit
     * @return tunnit stringina
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaAloitusAikaHH() === "15";
     * </pre>
     */
    public String annaAloitusAikaHH() {
        return Integer.toString(aloitusAika[0]);
    }
    
    
    /**
     * Antaa tapahtyman aloitusajan minuutit
     * @return minuutit stringina
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaAloitusAikaMM() === "10";
     * </pre>
     */
    public String annaAloitusAikaMM() {
        return Integer.toString(aloitusAika[1]);
    }
    
    
    /**
     * Antaa lopetusajan tunnit
     * @return tunnit stringina
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaLopetusAikaHH() === "18";
     * </pre>
     */
    public String annaLopetusAikaHH() {
        return Integer.toString(lopetusAika[0]);
    }
    
    
    /**
     * Antaa lopetusajan minuutit
     * @return minuutit stringina
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaLopetusAikaMM() === "0";
     * </pre>
     */
    public String annaLopetusAikaMM() {
        return Integer.toString(lopetusAika[1]);
    }
    
    
    /**
     * Ei käytetä viimesimmässä versiossa
     * @return local data
     */
    public LocalDate annaAloitusDate() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        String pv = aloitusPaiva[0] + "." + aloitusPaiva[1] + "." + aloitusPaiva[2];
        LocalDate ld = LocalDate.parse(pv, formatter);
        return ld;
    }
    
    
    /**
     * Tulostetaan tapahtuman tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * @return palauttaa tapahtuman id:n
     * <pre name="test">
     * Tapahtuma a = new Tapahtuma();
     * a.rekisteroi();
     * a.getId() === 5;
     * </pre>
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Palauttaa aloitusajasta muodostetun int taulukon
     * @return palauttaa aloitus ajan
     * @example
     * <pre name="test">
     * Tapahtuma a = new Tapahtuma();
     * a.luoTestiTapahtuma(1, 2);
     * int[] k = a.getAloitusAika();
     * k[0] === 12;
     * k[1] === 30;
     * </pre>
     */
    public int[] getAloitusAika() {
        return aloitusAika;
    }
    
    
    /**
     * Palauttaa lopetusajasta muodostetun int taulukon
     * @return palauttaa lopetus ajan
     * @example
     * <pre name="test">
     * Tapahtuma a = new Tapahtuma();
     * a.luoTestiTapahtuma(1, 2);
     * int[] k = a.getLopetusAika();
     * k[0] === 15;
     * k[1] === 0;
     * </pre>
     */
    public int[] getLopetusAika() {
        return lopetusAika;
    }
    
    
    /**
     * Palauttaa aloituspaivan muodostetun int taulukon
     * @return palauttaa aloituspäivän
     * @example
     * <pre name="test">
     * Tapahtuma a = new Tapahtuma();
     * a.luoTestiTapahtuma(1, 2);
     * int[] b = a.getAloitusPaiva(); 
     * b[0] === 3;
     * b[1] === 4;
     * b[2] === 2020;
     * </pre>
     */
    public int[] getAloitusPaiva() {
        return aloitusPaiva;
    }
    
    
    /**
     * Palauttaa lopetuspaivasta muodostetun int taulukon
     * @return palauttaa lopetuspäivän
     * @example
     * <pre name="test">
     * Tapahtuma a = new Tapahtuma();
     * a.luoTestiTapahtuma(1, 2);
     * int[] t = a.getLopetusPaiva();
     * t[0] === 3;
     * t[1] === 4;
     * t[2] === 2020;
     * </pre>
     */
    public int[] getLopetusPaiva() {
        if (aloitusPaiva[0] == lopetusPaiva[0] && aloitusPaiva[1] == lopetusPaiva[1] && aloitusPaiva[2] == lopetusPaiva[2]) {
            getAloitusPaiva(); //ei palauteta voisi olla myos ratkaisu
        }
        
        return lopetusPaiva;
    }
    
    
    /**
     * @return palauttaa toiminnon id:n
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma(1, 2), t2 = new Tapahtuma(2, 1);
     * t.getToimintoID() === 1;
     * t2.getToimintoID() === 2;
     * </pre>
     */
    public int getToimintoID() {
        return this.toimintoID;
    }
    
    
    /**
     * @return palauttaa aihealueen id:n
     * @example
     * <pre name="test">
     * Tapahtuma t = new Tapahtuma(1, 2), t2 = new Tapahtuma(2, 1);
     * t.getAihealueID() === 2;
     * t2.getAihealueID() === 1;
     * </pre>
     */
    public int getAihealueID() {
        return this.aihealueID;
    }
    
    
    /**
     * Palauttaa tapahtuman tiedot merkkijonoja jonka voi tallentaa tiedostoon
     * return tapahtuman tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.parse("   1 |  15:10-18:00  |  12.4.2020 | 13.4.2020 | 1 | 1 | 15 | 3");
     * uusi.toString().startsWith("1|15:10-18:00|12.4.2020-13.4.2020|1|1|15|3") === false;
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
                getId() + "|" +
                annaAloitusAika() + "-" +
                annaLopetusAika() +"|" +
                annaAloitusPaiva() + "-" +
                annaLopetusPaiva() + "|" +
                annaViikkoNumero() + "|" +
                annaViikonPaiva() + "|" +
                toimintoID + "|" +
                aihealueID;
                
    }
    
    /**
     * @return palauttaa viikonpaivan merkkijonona
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaViikonPaiva() === "3";
     * </pre>
     */
    public String annaViikonPaiva() {
        return Integer.toString(viikonPaiva);
    }

    
    /**
     * @return palauttaa viikonpaivan int muodossa
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.annaViikonPaivaINT() === 3;
     * </pre>
     */
    public int annaViikonPaivaINT() {
        return viikonPaiva;
    }
    

    /**
     * Selvittää jäsenen tiedot | erotellusta merkkijonosta
     * @param rivi josta jäsenen tiedot otetaan 
     * 
     * @example
     * <pre name="test">
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * uusi.parse("   1 |  15:10-18:00  |  12.4.2020 | 13.4.2020 | 1 | 1 | 15 | 3");
     * uusi.getId() === 1;
     * uusi.toString().startsWith("1|15:10-18:00|12.4.2020-13.4.2020|1|1|15|3") === false;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        id = (Mjonot.erota(sb, '|', id));
        
        if (id > (seuraavaNro - 1)) {
            seuraavaNro = (id + 1);
        }
        
        String aloitusAikai = (Mjonot.erota(sb, '-', ""));
        StringBuffer sb2 = new StringBuffer(aloitusAikai);

        aloitusAika[0] = (Mjonot.erota(sb2, ':', aloitusAika[0]));
        aloitusAika[1] = (Mjonot.erota(sb2, ' ', aloitusAika[1]));
        
        String lopetusAikai = (Mjonot.erota(sb, '|', ""));
        StringBuffer sb3 = new StringBuffer(lopetusAikai);
        lopetusAika[0] = (Mjonot.erota(sb3, ':', lopetusAika[0]));
        lopetusAika[1] = (Mjonot.erota(sb3, ' ', lopetusAika[1]));
        
        String aloitusPaivai = (Mjonot.erota(sb, '-', ""));
        StringBuffer sb4 = new StringBuffer(aloitusPaivai);
        aloitusPaiva[0] = (Mjonot.erota(sb4, '.', aloitusPaiva[0]));
        aloitusPaiva[1] = (Mjonot.erota(sb4, '.', aloitusPaiva[1]));
        aloitusPaiva[2] = (Mjonot.erota(sb4, ' ', aloitusPaiva[2]));
        
        String lopetusPaivai = (Mjonot.erota(sb, '|', ""));
        StringBuffer sb5 = new StringBuffer(lopetusPaivai);
        lopetusPaiva[0] = (Mjonot.erota(sb5, '.', lopetusPaiva[0]));
        lopetusPaiva[1] = (Mjonot.erota(sb5, '.', lopetusPaiva[1]));
        lopetusPaiva[2] = (Mjonot.erota(sb5, ' ', lopetusPaiva[2]));
        
        
        String viikkoNRO = (Mjonot.erota(sb, '|', ""));
        viikkoNro = Integer.parseInt(viikkoNRO);
        
        String viikonPv = (Mjonot.erota(sb, '|', ""));
        viikonPaiva = Integer.parseInt(viikonPv);
        
        toimintoID = (Mjonot.erota(sb, '|', toimintoID));
        aihealueID = (Mjonot.erota(sb, '|', aihealueID));
    }
    
    
    /**
     * Testiohjelma tapahtumalle
     * @param args ei kayteta
     */
    public static void main(String[] args) {
        Tapahtuma tapahtuma = new Tapahtuma();
        tapahtuma.luoTestiTapahtuma(2, 3);
        tapahtuma.parse("   1 |  12:30  | 15:00  |  3.4.2020 | 2 | 3");
        tapahtuma.tulosta(System.out);
        int[] s = tapahtuma.getLopetusPaiva();
        for (int i = 0; i < s.length; i++) {
            System.out.println(s[i]);
        }
        
    }
    
    
}
