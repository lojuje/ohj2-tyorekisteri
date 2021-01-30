package rekisteriTietokanta;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Toiminto luokka
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 *
 *Vastuualueet: 
 *      (- ei tieda tyorekisterista, eika kayttoliittymasta)
 *      - tietaa toiminnon kentat
 *          -id
 *          -otsikko

 *      - osaa tarkistaa tietyn kentan oikeellisuuden(syntaksin)
 *      - osaa muuttaa 1|Ohjelmointi - merkkijonon tapahtuman tiedoiksi
 *      - osaa antaa merkkijonon i:n kentan tiedot
 *      - osaa laittaa merkkijonon i:neksi kentaksi
 */
public class Toiminto {

    
    private int id;
    @SuppressWarnings("unused") //ei ole setteria niin tarvitaan suppressi
    private int tapahtumanID;
    private String otsikko;
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan toiminto. Alustetaan halutulla otsikolla
     * @param otsikko annettu otsikko
     * @example
     * <pre name="test">
     * Toiminto toi = new Toiminto("Kissan ruokkiminen");
     * toi.getOtsikko() === "Kissan ruokkiminen";
     * </pre>
     */
    public Toiminto(String otsikko) {
        this.otsikko = otsikko;
    }
    
    /**
     * Alustetaan toiminto. Testin luontia varten
     */
    public Toiminto() {
        //
    }
    
    
    /**
     * Alustetaan toiminto annetulla id:lla
     * @param nro numero
     * @example
     * <pre name="test">
     * Toiminto t = new Toiminto(10), t2 = new Toiminto(200);
     * t.getId() === 10;
     * t2.getId() === 200;
     * </pre>
     */
    public Toiminto(int nro) {
        this.id = nro;
    }
    
    
    /**
     * Luodaan testi toiminto default arvoilla
     * @example
     * <pre name="test">
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * t.rekisteroi();
     * t2.rekisteroi();
     * t.luoTESTI_Toiminto();
     * t2.luoTESTI_Toiminto();
     * t.getId() === 4;
     * t2.getId() === 5;
     * t.getOtsikko() === "Ohjelmointi 2 luento";
     * t2.getOtsikko() === "Ohjelmointi 2 luento";
     * </pre>
     */
    public void luoTESTI_Toiminto() {
        otsikko = "Ohjelmointi 2 luento";
    }
    
    
    /**
     * Luodaan testi toiminto default arvoilla
     * @param tapahtumaID tapahtuman id johon toiminto liittyy
     * @example
     * <pre name="test">
     *  { new Toiminto($tapahtumanID);}
     *
     *     $tapahtumanID  | $otsikko  
     *  ----------------------------
     *     13             | "Ohjelmointi 2 luento"  
     *     12             | "Ohjelmointi 2 luento" 
     */
    public void luoTESTI_Toiminto(int tapahtumaID) {

        tapahtumanID = tapahtumaID;
        otsikko = "Ohjelmointi 2 luento";
    }
    
    
    /**
     * Tulostetaan toiminnon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(otsikko);
    }
    
    
    /**
     * Tulostetaan henkilon tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Yksiloidaan id käyttämalla staattista seuraavaa numeroa
     * @return palauttaa idn
     * @example
     * <pre name="test">
     * Toiminto testi = new Toiminto(), testi2 = new Toiminto();
     * testi.rekisteroi();
     * testi2.rekisteroi();
     * testi.getId() === 6;
     * testi2.getId() === 7;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    
    /**
     * Yksiloidaan id käyttämalla annettua numeroa
     * @param numero joka yksiloi
     * @return palauttaa idn
     * @example
     * <pre name="test">
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * t.rekisteroi(100);
     * t2.rekisteroi(500);
     * t.getId() === 100;
     * t2.getId() === 500;
     * </pre>
     */
    public int rekisteroi(int numero) {
        id = numero;
        return id;
    }
    
    
    /**
     * Hakee ID:n
     * @return palauttaa id:n
     * <pre name="test">
     * Toiminto uusi = new Toiminto(3);
     * uusi.getId() === 3;
     * Toiminto uusi2 = new Toiminto(1);
     * uusi2.getId() === 1;
     * Toiminto uusi3 = new Toiminto();
     * uusi3.rekisteroi();
     * uusi3.getId() === 2;
     * Toiminto uusi4 = new Toiminto();
     * uusi4.rekisteroi();
     * uusi4.getId() === 3;
     * </pre>
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Hakee otsikon
     * @return palauttaa otsikon
     * <pre name="test">
     * Toiminto uusi = new Toiminto();
     * uusi.getOtsikko() === null;
     * Toiminto uusi2 = new Toiminto();
     * uusi2.rekisteroi();
     * uusi2.luoTESTI_Toiminto();
     * uusi2.getOtsikko() === "Ohjelmointi 2 luento";
     * </pre>
     */
    public String getOtsikko() {
        return otsikko;
    }
    
    
    /**
     * Palauttaa toiminnon tiedot merkkijonoja jonka voi tallentaa tiedostoon
     * return toiminnon tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Toiminto toiminto = new Toiminto();
     * toiminto.parse("   1 |  Ohjelmointi 2 luento");
     * toiminto.toString().startsWith("1|Ohjelmointi 2 luento") === false; //enemmän kuin 3 kenttaa ja siksi loppuun |
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
                getId() + "|" +
                getOtsikko(); 
    }
    
    
    /**
     * Selvittaa toiminnon tiedot | erotellusta merkkijonosta
     * @param rivi josta toiminto tiedot otetaan
     * @example
     * <pre name="test">
     * Toiminto toiminto = new Toiminto();
     * toiminto.parse("   1 |   Ohjelmointi 2 luento");
     * toiminto.getId() === 1;
     * toiminto.toString().startsWith("1|Ohjelmointi 2 luento") === false;
     * </pre>
     */
    public void parse(String rivi) {
        
        StringBuffer sb = new StringBuffer(rivi);
        id = (Mjonot.erota(sb, '|', id));
        
        if (id > (seuraavaNro - 1)) {
            seuraavaNro = (id + 1);
        }
        
        otsikko = sb.toString();
        
    }
    
    
    /**
     * Testiohjelma toiminnolle
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Toiminto toiminto = new Toiminto();
        toiminto.luoTESTI_Toiminto();
        toiminto.tulosta(System.out);
    }
    
    
}
