package rekisteriTietokanta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Tyorekisterin toiminnat
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 * Vastuualueet:
 *
 *        - Lukee ja kirjoittaa toiminnat tiedostoon
 *        - Osaa etsia ja lajitella
 *        
 *        HUOM! Toimintoja ei poisteta, siksi koska se sekoittaisi indeksoinnin ja lisaksi ne tapahtumat jotka kayttavat ko. toimintaa pitaisi poistaa myos
 */
public class Toiminnat implements Iterable<Toiminto> {
    
    
   // private String tiedostonNimi = "";
    private final Collection<Toiminto> alkiot = new ArrayList<Toiminto>();
    
    /**
     * Koko nimi
     */
    public String kokoNimi = "";
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "Marko/toiminnat";
    
    
    /**
     * Toimintojen alustaminen
     */
    public Toiminnat() {
        //Toistaiseksi ei tarvitse
    }
    
    
    /**
     * Lisaa uuden toiminnon tietorakenteeseen. Ottaa toiminnon omistukseensa.
     * @param toiminto joka lisataan
     * @example
     * <pre name="test">
     * Toiminnat toi = new Toiminnat();
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * toi.lisaa(t);
     * toi.lisaa(t2);
     * toi.getLkm() === 2;
     * </pre>
     */
    public void lisaa(Toiminto toiminto) {
        alkiot.add(toiminto);
        muutettu = true;
    }
    
    
    /**
     * Lukee toimintojen tiedostosta.
     * @param tied tiedoston hakemisto
     * @throws SisaltoException jos lukeminen epaonnistuu
     * @example
     * <pre name="test">
     * #THROWS SisaltoException
     * #import java.io.*;
     * 
     * Toiminnat toiminnat = new Toiminnat();
     * Toiminto t1 = new Toiminto(), t2 = new Toiminto();
     * t1.rekisteroi();
     * t2.rekisteroi();
     * t1.luoTESTI_Toiminto();
     * t2.luoTESTI_Toiminto();
     * String hakemisto = "testirekisteri";
     * String tiedNimi = hakemisto+"/nimet";
     * File ftied = new File(tiedNimi + ".dat");
     * File dir = new File(hakemisto);
     * dir.mkdir();
     * ftied.delete();
     * toiminnat.lueTiedostosta(tiedNimi); #THROWS SisaltoException
     * toiminnat.lisaa(t1);
     * toiminnat.lisaa(t2);
     * toiminnat.talleta();
     * Iterator<Toiminto> i = toiminnat.iterator();
     * i.next().toString() === t1.toString();
     * i.next().toString() === t2.toString();
     * toiminnat.lisaa(t2);
     * toiminnat.talleta();
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
                Toiminto toiminto = new Toiminto();
                toiminto.parse(rivi);
                lisaa(toiminto);
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
     * Tallentaa toiminnot tiedostoon
     * Tiedoston muoto esim:
     * 
     * 2
     * 1|Ohjelmointi
     * 2|Harrastus
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
            for (Toiminto toiminto : this) {
                fo.println(toiminto.toString());
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
     * Palauttaa toimintojen lukumaaran
     * @return toimintojen lukumaara
     * @example
     * <pre name="test">
     * Toiminnat toi = new Toiminnat();
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * toi.lisaa(t);
     * toi.lisaa(t2);
     * toi.getLkm() === 2;
     * </pre>
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Hakee toiminnon ID:n mukaan
     * Jos toimintoa ei loydy palauttaa null
     * @param id jonka mukaan etsitaan toiminto
     * @return palauttaa id:tä vastaavan toiminnon. Jos ei loydy palauttaa null;
     * @example
     * <pre name="test">
     * Toiminnat toi4 = new Toiminnat();
     * Toiminto testi1 = new Toiminto(), testi2 = new Toiminto();
     * testi1.rekisteroi();
     * testi2.rekisteroi();
     * toi4.lisaa(testi1);
     * toi4.lisaa(testi2);
     * toi4.annaIdnMukaan(1) === null;
     * </pre>
     */
    public Toiminto annaIdnMukaan(int id) {
       
        for (Toiminto toiminto : alkiot) {
            if (toiminto.getId() == id) {
                return toiminto;
            }
        }
        
        return null;
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
     * Iteraattori toimintojen lapikaynnille
     * @return toimintoiteraattori
     */
    @Override
    public Iterator<Toiminto> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan kaikki toiminnot
     * @param id toiminnon id
     * @return tietorakenne jossa viitteet loydettyihin toimintoihin
     * @example
     * <pre>
     * Toiminnat toi = new Toiminnat();
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * t.rekisteroi();
     * t2.rekisteroi();
     * toi.lisaa(t);
     * toi.lisaa(t2);
     * java.util.List<Toiminto> lista = toi.annaToiminnat(1);
     * lista.get(0) === t;
     * </pre>
     */
    public List<Toiminto> annaToiminnat(int id) {
        
        List<Toiminto> loydetyt = new ArrayList<Toiminto>();
        for(Toiminto toiminto : alkiot) {
            if (toiminto.getId() == id) loydetyt.add(toiminto);
        }
        return loydetyt;
    }
    
    
    /**
     * @return palauttaa listan toiminnoista
     * @example
     * <pre name="test">
     * Toiminnat toi = new Toiminnat();
     * Toiminto a = new Toiminto();
     * Toiminto b = new Toiminto();
     * toi.lisaa(a);
     * toi.lisaa(b);
     * List<Toiminto> lista = toi.annaToiminnat();
     * lista.get(0) === a;
     * lista.get(1) === b;
     * lista.size() === 2;
     * </pre>
     */
    public List<Toiminto> annaToiminnat() {
        
        List<Toiminto> loydetyt = new ArrayList<Toiminto>();
        for(Toiminto toiminto : alkiot) {
            if (toiminto.equals(null)) return loydetyt;
            loydetyt.add(toiminto);
        }
        return loydetyt;
    }
    
    
    /**
     * @param id jonka mukaan etsitaan
     * @return palauttaa tapahtuman jonka id sama kuin haetun, jos ei loydy palauttaa null
     * @example
     * <pre name="test">
     * Toiminnat toi = new Toiminnat();
     * Toiminto a = new Toiminto();
     * a.rekisteroi();
     * Toiminto b = new Toiminto();
     * b.rekisteroi();
     * toi.lisaa(a);
     * toi.lisaa(b);
     * toi.anna(1) === a;
     * toi.anna(2) === b;
     * </pre>
     */
    public Toiminto anna(int id) {
        for (Toiminto toiminto : alkiot) {
            if (toiminto.getId() == id) {
                return toiminto;
            }
        }
        return null;
    }
    
    
    /**
     * Testiohjelma tapahtumille
     * @param args ei kayteta
     */
    public static void main(String[] args) {
        
        Toiminnat toiminnat = new Toiminnat();
        Toiminto pitsi1 = new Toiminto();
        pitsi1.luoTESTI_Toiminto();
        pitsi1.rekisteroi();
        Toiminto pitsi2 = new Toiminto();
        pitsi2.luoTESTI_Toiminto();
        pitsi2.rekisteroi();
        Toiminto pitsi3 = new Toiminto();
        pitsi3.luoTESTI_Toiminto();
        pitsi3.rekisteroi();
        Toiminto pitsi4 = new Toiminto();
        pitsi4.luoTESTI_Toiminto();
        pitsi4.rekisteroi();
        
        toiminnat.lisaa(pitsi1);
        toiminnat.lisaa(pitsi2);
        toiminnat.lisaa(pitsi3);
        toiminnat.lisaa(pitsi4);
        
        System.out.println("======================================== Toiminnat testi ===============================================");
        
        List<Toiminto> toiminnat2 = toiminnat.annaToiminnat(2);
        
        for (Toiminto toiminto : toiminnat2) {
            System.out.println(toiminto.getId() + " ");
            toiminto.tulosta(System.out);
        }   
    }
    
}
