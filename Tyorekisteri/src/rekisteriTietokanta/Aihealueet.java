package rekisteriTietokanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tyorekisterin aihealueet
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 *  Vastuualueet:
 *      - pitaa ylla tietoa aihealueista, eli osaa lisata ja poistaa aihealueita
 *      - lukee ja kirjoittaa aihealueet tiedostoon
 *      - osaa etsia ja lajitella
 *   
 *   Avustajat:
 *      - aihealue
 *      
 *      HUOM! Aihealueita ei poisteta, siksi koska se sekoittaisi indeksoinnin ja lisaksi ne tapahtumat jotka kayttavat ko. aihealuetta pitaisi poistaa myos
 */
public class Aihealueet {
    
    
    private static final int MAX_AIHEALUEITA = 0;
    private int lkm = 0;
    private Aihealue sisalto[] = new Aihealue[MAX_AIHEALUEITA];
    
    /**
     * Aihealueen kokonimi
     */
    public String kokoNimi = "";
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "Marko/aihealueet";
    
    
    /**
     * Oletusmuodostaja
     */
    public Aihealueet() {
        //
    }
    
    
    /**
     * @return palauttaa aihealueet taulukossa
     * @example
     * <pre name="test">
     * Aihealue a1 = new Aihealue(), a2 = new Aihealue();
     * Aihealueet aih = new Aihealueet();
     * aih.lisaa(a1);
     * aih.lisaa(a2);
     * Aihealue[] testi1 = aih.annaAihealueet();
     * testi1[0] === a1;
     * testi1[1] === a2;
     * </pre>
     */
    public Aihealue[] annaAihealueet() {
        return sisalto;
    }
    
    
    /**
     * @return palauttaa listan aihealueista
     * @example
     * <pre name="test">
     * Aihealue a3 = new Aihealue(), a4 = new Aihealue();
     * Aihealueet aih2 = new Aihealueet();
     * aih2.lisaa(a3);
     * aih2.lisaa(a4);
     * List<Aihealue> testi2 = aih2.annaAihealueetListassa();
     * testi2.get(0) === a3;     
     * testi2.get(1) === a4;
     * testi2.size() === 2;
     * </pre>
     */
    public List<Aihealue> annaAihealueetListassa() {
        
        List<Aihealue> l = new ArrayList<Aihealue>();
        
        for (int i = 0; i < sisalto.length; i++) {
            l.add(sisalto[i]);
        }
        
        return l;
    }
    
    /**
     * Lisaa uuden aihealueen tietorakenteeseen. Ottaa aihealueen omistukseensa.
     * @param aihealue lisattavan aihealueen viite. 
     * @example
     * <pre name="test">
     * Aihealueet aihealueet = new Aihealueet();
     * Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
     * aihealueet.getLkm() === 0;
     * aihealueet.lisaa(kouluhommat); aihealueet.getLkm() === 1;
     * aihealueet.lisaa(kouluhommat2); aihealueet.getLkm() === 2;
     * aihealueet.lisaa(kouluhommat); aihealueet.getLkm() === 3;
     * aihealueet.anna(0) === kouluhommat;
     * aihealueet.anna(1) === kouluhommat2;
     * aihealueet.anna(2) === kouluhommat;
     * aihealueet.anna(1) == kouluhommat === false;
     * aihealueet.anna(1) == kouluhommat2 === true;
     * aihealueet.lisaa(kouluhommat); aihealueet.getLkm() === 4;
     * aihealueet.lisaa(kouluhommat); aihealueet.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Aihealue aihealue) {
        if (lkm >= sisalto.length) {
            kasvataTaulukonKokoa();
        }
        sisalto[lkm] = aihealue;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Taulukon koon kasvattaminen, kun raja menee yli
     * Taulukkoa kasvatetaan aina yhdellä
     */
    private void kasvataTaulukonKokoa() {
        sisalto = Arrays.copyOf(sisalto, sisalto.length+1);
    }
    

    /**
     * Palauttaa viitteen i:teen aihealueeseen
     * @param i monennenko aihealueen viite halutaan
     * @return viite aihealueeseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     * @example
     * <pre name="test">
     * Aihealueet aihealueet = new Aihealueet();
     * Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
     * aihealueet.lisaa(kouluhommat);
     * aihealueet.lisaa(kouluhommat2);
     * aihealueet.anna(0) === kouluhommat;
     * aihealueet.anna(1) === kouluhommat2;
     * aihealueet.anna(1) == kouluhommat === false;
     * aihealueet.anna(1) == kouluhommat2 === true;
     * </pre>
     */
    public Aihealue anna(int i) throws IndexOutOfBoundsException {
        
        if (i < 0 || lkm < i) {
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        }
        
        return sisalto[i];
    }
    
    
    /**
     * Aihealue ID:n mukaan
     * @param id jonka mukaan aihealue etsitaan
     * @return aihealue jonka id sama kuin annettu id. Jos ko. aihealuetta ei loydy palauttaa null.
     * @example
     * <pre name="test">
     * Aihealueet aihealueet = new Aihealueet();
     * Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
     * kouluhommat.rekisteroi();
     * kouluhommat2.rekisteroi();
     * aihealueet.lisaa(kouluhommat);
     * aihealueet.lisaa(kouluhommat2);
     * aihealueet.annaIdnMukaan(3) === kouluhommat;
     * aihealueet.annaIdnMukaan(4) === kouluhommat2;
     * </pre>
     */
    public Aihealue annaIdnMukaan(int id) {

        for (int i = 0; i < lkm; i++) {
            
            if (id == sisalto[i].getID()) {
                return sisalto[i];
            }
        }
        
        return null;
    }
    
    
    /**
     * Lukee tapahtumien tiedostosta.
     * @param tied tiedoston hakemisto
     * @throws SisaltoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SisaltoException
     * #import java.io.*;
     * 
     * Aihealueet aihealueet = new Aihealueet();
     * Aihealue t1 = new Aihealue(), t2 = new Aihealue();
     * t1.asetaKategoria();
     * t1.rekisteroi();
     * t2.asetaKategoria();
     * t2.rekisteroi();
     * String hakemisto = "testirekisteri";
     * String tiedNimi = hakemisto+"/nimet";
     * File ftied = new File(tiedNimi + ".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * aihealueet.lueTiedostosta(tiedNimi); #THROWS SisaltoException
     * File ftied2 = new File(tiedNimi + ".dat");
     * aihealueet.lisaa(t1);
     * aihealueet.lisaa(t2);
     * aihealueet.talleta();
     * aihealueet.anna(0) === t1;
     * aihealueet.anna(1).toString() === t2.toString();
     * aihealueet.lisaa(t2);
     * aihealueet.talleta();
     * ftied2.delete() === true;
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
                Aihealue aihealue = new Aihealue();
                aihealue.parse(rivi);
                lisaa(aihealue);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SisaltoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
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
     * Tallentaa aihealueen tiedostoon
     * Tiedoston muoto esim:
     * 
     * 3
     * 1|Kouluhommat1
     * 2|Kouluhommat2
     * 3|Kouluhommat3
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
            fo.println(sisalto.length);
            for (Aihealue aihealue : sisalto) {
                if (aihealue != null) {
                    fo.println(aihealue.toString());
                }
            }
        } catch (FileNotFoundException ex) {
            throw new SisaltoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SisaltoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelma");
        }
        
        muutettu = false;
    }
    
    
    /**
     * Palauttaa kokonimen
     * @return palauttaa tyorekisterin koko nimen
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa Aihealueiden lukumaaran
     * @return aihealueiden lukumaaran
     * @example
     * <pre name="test">
     * Aihealueet aihealueet = new Aihealueet();
     * Aihealue t1 = new Aihealue(), t2 = new Aihealue(), t3 = new Aihealue();
     * aihealueet.lisaa(t1);
     * aihealueet.lisaa(t2);
     * aihealueet.lisaa(t3);
     * aihealueet.getLkm() === 3;
     * Aihealue t4 = new Aihealue();
     * aihealueet.lisaa(t4);
     * aihealueet.getLkm() === 4;
     * </pre>
     */
    public int getLkm() {
        return lkm;
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
     * Testiohjelma aihealueille
     * @param args ei kayteta
     */
    public static void main(String args[]) {
       
        Aihealueet aihealueet = new Aihealueet();
        
        Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
        
        kouluhommat.rekisteroi();
        kouluhommat.asetaKategoria();
        kouluhommat2.rekisteroi();
        kouluhommat2.asetaKategoria();
        
        aihealueet.lisaa(kouluhommat);
        aihealueet.lisaa(kouluhommat2);
        
        System.out.println(aihealueet.getTiedostonPerusNimi());
        
        
        System.out.println("============================== Aihealueet testi ==============================");
        
        for (int i = 0; i < aihealueet.getLkm(); i++) {
            Aihealue aihealue = aihealueet.anna(i);
            System.out.println("Aihealueen numero on: " + i);
            aihealue.tulosta(System.out);
        }
    }
    
    
}
