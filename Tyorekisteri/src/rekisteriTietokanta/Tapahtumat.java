package rekisteriTietokanta;

import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tyorekisterin tapahtumat
 * 
 * @author musaj ja jussil
 * @version 14.4.2020
 * Vastuualueet: 
 *
 *      - Pitaa ylla tietoa tapahtumista, eli osaa lisata ja poistaa tapahtumia
 *      - Lukee ja kirjoittaa tapahtumat tiedostoon
 *      - Osaa etsia ja lajitella
 */
public class Tapahtumat implements Iterable<Tapahtuma>{
    
    
    private final ArrayList<Tapahtuma> alkiot = new ArrayList<Tapahtuma>();
    
    
    /**
     * Tapahtuman kokonimi
     */
    public String kokoNimi = "";
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "Marko/tapahtumat";
    
    //private static int MAX_TAPAHTUMIA = 10;
    //private int lkm = 0;
    //private Tapahtuma alkiott[] = new Tapahtuma[MAX_TAPAHTUMIA];
    
    
    /**
     * Alustaa tapahtumat
     */
    public Tapahtumat() {
        // ei tarvita viela
    }
    
    
    /**
     * Lisaa uuden tapahtuman tietorakenteeseen. Ottaa tapahtuman omistukseen.
     * @param tapahtuma lisattava tapahtuma
     * @example
     * <pre name="test">
     * Tapahtumat tap = new Tapahtumat();
     * Tapahtuma t = new Tapahtuma(), t1 = new Tapahtuma();
     * tap.lisaa(t);
     * tap.lisaa(t1);
     * tap.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Tapahtuma tapahtuma) {
        alkiot.add(tapahtuma);
        muutettu = true;
    }
    
    
    /**
     * Lukee tapahtumien tiedostosta.
     * @param tied tiedoston hakemisto
     * @throws SisaltoException jos lukeminen epaonnistuu
     * @example
     * <pre name="test">
     * #THROWS SisaltoException
     * #import java.io.*;
     * #import rekisteriTietokanta.*;
     * 
     * Tapahtumat tapahtumat = new Tapahtumat();
     * Tapahtuma t1 = new Tapahtuma(), t2 = new Tapahtuma();
     * t1.luoTestiTapahtuma(1, 2);
     * t2.luoTestiTapahtuma(2, 1);
     * String hakemisto = "testirekisteri";
     * String tiedNimi = hakemisto+"/nimet";
     * File ftied = new File(tiedNimi + ".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * tapahtumat.lueTiedostosta(tiedNimi); #THROWS SisaltoException
     * tapahtumat.lisaa(t1);
     * tapahtumat.lisaa(t2);
     * tapahtumat.talleta();
     * tapahtumat = new Tapahtumat();
     * tapahtumat.lueTiedostosta(tiedNimi);
     * Iterator<Tapahtuma> i = tapahtumat.iterator();
     * i.next().toString() === t1.toString();
     * i.next().toString() === t2.toString();
     * tapahtumat.lisaa(t2);
     * tapahtumat.talleta();
     * ftied.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SisaltoException {
        
        setTiedostonPerusNimi(tied);
        
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            
            kokoNimi = fi.readLine();
            if (kokoNimi == null) throw new SisaltoException("Tyorekisteri nimi puuttuu");
            
            String rivi = fi.readLine();
            if(rivi == null) throw new SisaltoException("Koko puuttuu");
            
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Tapahtuma tapahtuma = new Tapahtuma();
                tapahtuma.parse(rivi);
                lisaa(tapahtuma);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SisaltoException("Luodaan uusi kansio nimelta " + haeNimi() + "."); // Tama tulee kun tiedostoa ei ole olemassa
        } catch (IOException e) {
            throw new SisaltoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisesta tiedostosta
     * @throws SisaltoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SisaltoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa tapahtuman tiedostoon
     * Tiedoston muoto:
     * 
     * 2
     * 1|12:30-15:0|3.4.2020-3.4.2020|1|1
     * 2|12:30-15:0|3.4.2020-3.4.2020|1|1
     * 
     * @throws SisaltoException jos talletus epaonnistuu
     */
    public void talleta() throws SisaltoException {

        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintWriter fo = new PrintWriter (new FileWriter ( ftied.getCanonicalPath()))) {
            fo.println(getKokoNimi());
            fo.println(alkiot.size());
            for (Tapahtuma tapahtuma : this) {
                fo.println(tapahtuma.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SisaltoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SisaltoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelma");
        }
        
        muutettu = false;
    }
    
    
    /**
     * @return palauttaa tyorekisterin koko nimen
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa tapahtumien lukumaaran
     * @return tapahtumien lukumaara
     * @example
     * <pre name="test">
     * Tapahtumat tap = new Tapahtumat();
     * Tapahtuma t = new Tapahtuma(), t1 = new Tapahtuma();
     * tap.lisaa(t);
     * tap.lisaa(t1);
     * tap.getLkm() === 2;
     * </pre>
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota kaytetaan tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    
    /**
     * Haetaan pelkka nimi
     * @return palauttaa nimen
     * @example
     * <pre name="test">
     * #import fi.jyu.mit.ohj2.Mjonot;
     * StringBuilder sb = new StringBuilder("Kake/tapahtumat.dat");
     * String nimi = Mjonot.erota(sb,'/');
     * nimi === "Kake";
     * </pre>
     */
    public String haeNimi() {
        StringBuilder sb = new StringBuilder(getTiedostonPerusNimi());
        String nimi = Mjonot.erota(sb, '/');
        return nimi;
    }

    
    /**
     * Palauttaa tiedoston nimen, jota kaytetaan tallennukseen
     * @return tallennustiedoston perusnimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /**
      * Iteraattori kaikkien tapahtumien lapikaymiseen
      * @return tapahtumaiteraattori
     */
    @Override
    public Iterator<Tapahtuma> iterator() {
        return alkiot.iterator();
    }
    
    
     /**
      * Haetaan kaikki tapahtumat
      * @return tietorakenne jossa viiteet loydetteyihin tapahtumiin
      * @example
      * <pre name="test">
      * Tapahtumat tap = new Tapahtumat();
      * Tapahtuma t = new Tapahtuma(), t1 = new Tapahtuma();
      * tap.lisaa(t);
      * tap.lisaa(t1);
      * List<Tapahtuma> lista = tap.annaTapahtumat();
      * lista.get(0) === t;
      * lista.get(1) === t1;
      * </pre>
      */
    public List<Tapahtuma> annaTapahtumat() {
    
        List<Tapahtuma> loydetyt = new ArrayList<Tapahtuma>();
        for(Tapahtuma tapahtuma : alkiot) {
            loydetyt.add(tapahtuma);
        }
       
        return loydetyt;
    }
    
    
    /**
     * @param i indeksi
     * @return tapahtumaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     * @example
      * <pre name="test">
      * #import java.util.*;
      * 
      *  Tapahtumat tapahtumat = new Tapahtumat();
      *  Tapahtuma tanaan = new Tapahtuma(); tanaan.luoTestiTapahtuma(1, 2); tapahtumat.lisaa(tanaan); 
      *  Tapahtuma huomenna = new Tapahtuma(); huomenna.luoTestiTapahtuma(2, 3); tapahtumat.lisaa(huomenna);
      *  Tapahtuma keskiviikko = new Tapahtuma(); keskiviikko.luoTestiTapahtuma(3, 1); tapahtumat.lisaa(keskiviikko);
      *  
      *  tapahtumat.anna(0) === tanaan;
      *  tapahtumat.anna(2) === keskiviikko;
      *  
      * </pre>
     */
    public Tapahtuma anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || getLkm() <= i) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Testiohjelma tapahtumille
     * @param args ei kayteta
     */
    public static void main(String[] args) {
        
        Tapahtumat tapahtumat = new Tapahtumat();
        Tapahtuma pitsi1 = new Tapahtuma();
        pitsi1.luoTestiTapahtuma(2, 3);
        Tapahtuma pitsi2 = new Tapahtuma();
        pitsi2.luoTestiTapahtuma(1, 2);
        Tapahtuma pitsi3 = new Tapahtuma();
        pitsi3.luoTestiTapahtuma(3, 1);
        Tapahtuma pitsi4 = new Tapahtuma();
        pitsi4.luoTestiTapahtuma(1, 1);
        
        tapahtumat.lisaa(pitsi1);
        tapahtumat.lisaa(pitsi2);
        tapahtumat.lisaa(pitsi3);
        tapahtumat.lisaa(pitsi4);
        
        System.out.println("======================================== Tapahtumat testi ===============================================");
        
        List<Tapahtuma> tapahtumat2 = tapahtumat.annaTapahtumat();
        
        for (Tapahtuma tapahtuma : tapahtumat2) {
            tapahtuma.tulosta(System.out);
        }
        
    }


    /**
     * Muokataan annettua tapahtumaa tallettamalla muokatun tapahtuman tiedot vanhan paalle
     * @param tapahtuma jota muokataan
     * @example
     * <pre name="test">
     * Tapahtumat tap = new Tapahtumat();
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * tap.lisaa(uusi);
     * uusi.getLopetusPaiva().equals(lopetusP);
     * 
     * int[] aloitus2 = { 15, 10 };
     * int[] aloitusP2 = { 12, 4, 2020 };
     * int[] lopetus2 = { 18, 0 };
     * int[] lopetusP2 = { 14, 4, 2020 };
     * Tapahtuma uusi2 = new Tapahtuma(aloitus2, aloitusP2, lopetus2, lopetusP2, 1, 1, 15, 3);
     * tap.korvaa(uusi2);
     * uusi.getLopetusPaiva().equals(lopetusP2);
     * </pre>
     */
    public void korvaa(Tapahtuma tapahtuma) {
        int id = tapahtuma.getId();

        for (int i = 0; i < alkiot.size(); i++) {
            if (alkiot.get(i).getId() == id) {
                alkiot.set(i, tapahtuma);
                muutettu = true;
                return;
            }
        }
        
    }


    /**
     * Poiston hoito
     * @param id annettu id
     * @return 1 kun on hoidettu
     * @example
     * <pre name="test">
     * Tapahtumat tap = new Tapahtumat();
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma uusi = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * tap.lisaa(uusi);
     *
     * int poistettiinko = tap.poista(1);
     * poistettiinko === 1;
     * </pre>
     */
    public int poista(int id) {

        if (id < 0) return 0;
        ArrayList<Tapahtuma> tmp = new ArrayList<Tapahtuma>();
        
        
        for (int i = 0; i < alkiot.size(); i++) {
            if (alkiot.get(i).getId() == id) continue;
            tmp.add(alkiot.get(i));
        }
        alkiot.clear();
        
        for (int i = 0; i < tmp.size(); i++) {
            alkiot.add(tmp.get(i));
        }
        muutettu = true;
        return 1;
    }
    
}
