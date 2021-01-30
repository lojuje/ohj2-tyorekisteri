package rekisteriTietokanta;


import java.io.File;
import java.util.List;


/**
 * Tyorekisteri-luokka, joka huolehtii aihealueista. Paaosin kaikki metodit ovat vain "valittajametodeja" aihealueisiin.
 * @author musaj ja jussil
 * @version 14.4.2020
 *
 */
public class Tyorekisteri {
    
    
    private Aihealueet aihealueet = new Aihealueet();
    private Tapahtumat tapahtumat = new Tapahtumat();
    private Toiminnat toiminnat = new Toiminnat();
    
    
    /**
     * Tapahtuma joka valittuna
     */
    public Tapahtuma valittuTapahtuma;
    
    
    /**
     * Palauttaa tapahtumien maaaran
     * @return tapahtumien maara
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Tapahtuma t = new Tapahtuma(), t2 = new Tapahtuma();
     * tyo.lisaa(t);
     * tyo.lisaa(t2);
     * tyo.getTapahtumia() === 2;
     * </pre>
     */
    public int getTapahtumia() {
        return tapahtumat.getLkm();
    }
    
    
    /**
     * Palauttaa aihealueiden maaran
     * @return aihealueiden maara
     * @example
     * <pre name="test">
     * #THROWS SisaltoException
     * 
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * tyo.getAihealueita() === 2;
     * </pre>
     */
    public int getAihealueita() {
        return aihealueet.getLkm();
    }
    
    
    /**
     * Palauttaa aihealueiden maaran
     * @return aihealueiden maara
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * tyo.lisaa(t);
     * tyo.lisaa(t2);
     * tyo.getToimintoja() === 2;
     * </pre>
     */
    public int getToimintoja() {
        return toiminnat.getLkm();
    }
    
    
    /**
     * Poistaa tapahtuman tiedot.
     * @param tapahtuma joka poistetaan
     * @return montako aihealuetta poistettiin
     * Tyorekisteri tyo = new Tyorekisteri();
     * Tapahtuma a = new Tapahtuma(), a2 = new Tapahtuma();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * int poistettiinko = tyo.poista(a);
     * poistettiinko === 1;
     */
    public int poista(Tapahtuma tapahtuma) {
        
        if (tapahtuma == null) return 0;
        int ret = tapahtumat.poista(tapahtuma.getId());
        return ret;
    }
    
    
    /**
     * Lisaa Tyorekisterii uuden aihealueen
     * @param aihealue lisättävä aihealue
     * @throws SisaltoException jos lisaysta ei voida tehda
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * tyo.annaAihealue(0) === a;
     * </pre>
     */
    public void lisaa(Aihealue aihealue) throws SisaltoException {
        aihealueet.lisaa(aihealue);
    }
    
    
    /**
     * Lisataan uusi tapahtuma tyorekisteriin
     * @param tapahtuma lisattava tapahtuma
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Tapahtuma t = new Tapahtuma(), t2 = new Tapahtuma();
     * tyo.lisaa(t);
     * tyo.lisaa(t2);
     * tyo.getTapahtumia() === 2;
     * </pre>
     */
    public void lisaa(Tapahtuma tapahtuma) {
        tapahtumat.lisaa(tapahtuma);
    }
    
    
    /**
     * Lisataan uusi toiminto tyorekisteriin
     * @param toiminto lisättävä toiminto
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Toiminto t = new Toiminto(), t2 = new Toiminto();
     * tyo.lisaa(t);
     * tyo.lisaa(t2);
     * tyo.getToimintoja() === 2;
     * </pre>
     */
    public void lisaa(Toiminto toiminto) {
        toiminnat.lisaa(toiminto);
    }
    
    
    /**
     * Palauttaa i:n aihealueen
     * @param i indeksi jonka mukaan haetaan aihealue
     * @return viite i:n aihealueeseen
     * @throws IndexOutOfBoundsException jos i vaarin
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * tyo.annaAihealue(0) === a;
     * </pre>
     */
    public Aihealue annaAihealue(int i) throws IndexOutOfBoundsException {
        return aihealueet.anna(i);
    }
    
    /**
     * Palauttaa taulukon aihealueista
     * @return palauttaa kaikki aihealueet taulukossa
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * List<Aihealue> uusi = tyo.annaAihealueet();
     * uusi.size() === 2;
     * uusi.get(0) === a;
     * </pre>
     */
    public List<Aihealue> annaAihealueet() {
        return aihealueet.annaAihealueetListassa();
    }
    
    
    /**
     * @param id jonka mukaan annetaan aihealue
     * @return aihealue jolla haettu id
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * a.rekisteroi();
     * a.asetaKategoria();
     * tyo.lisaa(a);
     * tyo.tapahtumanAihealue(1) === null;
     * </pre>
     */
    public Aihealue tapahtumanAihealue(int id) {
        return aihealueet.annaIdnMukaan(id);
    }
    
    
    /**
     * @param id jonka mukaan annetaan toiminto
     * @return toiminto jolla haettu id
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Toiminto t = new Toiminto();
     * t.rekisteroi();
     * t.luoTESTI_Toiminto();
     * tyo.lisaa(t);
     * tyo.tapahtumanToiminto(1) === null;
     * </pre>
     */
    public Toiminto tapahtumanToiminto(int id) {
        return toiminnat.annaIdnMukaan(id);
    }
    
    
    /**
     * Palauttaa i:n tapahtuman
     * @param i jonka tapahtuma haetaan
     * @return tietorakenne jossa viitteet loydettyihin tapahtumiin
     * @throws IndexOutOfBoundsException jos i vaarin
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Tapahtumat tap = new Tapahtumat();
     * Tapahtuma t = new Tapahtuma();
     * t.rekisteroi();
     * t.luoTestiTapahtuma(1, 2);
     * tap.lisaa(t);
     * tyo.lisaa(t);
     * tyo.annaTapahtuma(0) === t;
     * </pre>
     */
    public Tapahtuma annaTapahtuma(int i) throws IndexOutOfBoundsException {
        return tapahtumat.anna(i);
    }
    
    
    /**
     * @param id jonka mukaan haetaan
     * @return palauttaa id:ta vastaavan tapahtuman, jos ei loydy palauttaa null
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Toiminto a = new Toiminto(), a2 = new Toiminto();
     * a.rekisteroi();
     * a2.rekisteroi();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * tyo.annaToiminto(1) === a;
     * </pre>
     */
    public Toiminto annaToiminto(int id) {
        return toiminnat.anna(id);
    }
    
    
    /**
     * Palauttaa listan tapahtumista
     * @return palauttaa kaikki tapahtumat
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Tapahtuma a = new Tapahtuma(), a2 = new Tapahtuma();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * List<Tapahtuma> uusi = tyo.annaTapahtumat();
     * uusi.size() === 2;
     * uusi.get(1) === a2;
     * </pre>
     */
    public List<Tapahtuma> annaTapahtumat() {
        return tapahtumat.annaTapahtumat();
    }
    
    
    /**
     * Haetaan kaikki toiminnot
     * @param tapahtuma jonka toiminto haetaan
     * @return tietorakenne jossa viitteet loydettyihin toimintoihin
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * 
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma tapaht = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * 
     * Toiminto a = new Toiminto();
     * a.rekisteroi();
     * tyo.lisaa(a);
     * List<Toiminto> uusi = tyo.annaToiminnat(tapaht);
     * uusi.size() === 1;
     * uusi.get(0) === a;
     * </pre>
     */
    public List<Toiminto> annaToiminnat(Tapahtuma tapahtuma) { //<--- palauttaa yksittaisen toiminnon...tarvii vieda alaspain
        return toiminnat.annaToiminnat(tapahtuma.getToimintoID());
    }
    
    
    /**
     * Palauttaa kaikki tallennetut toiminnot.
     * @return lista toiminnoista
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Toiminto a = new Toiminto(), a2 = new Toiminto();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * List<Toiminto> uusi = tyo.annaToiminnat();
     * uusi.size() === 2;
     * uusi.get(1) === a2;
     * </pre>
     */
    public List<Toiminto> annaToiminnat() {
        return toiminnat.annaToiminnat();
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi tiedostolle asetettava nimi
     */
    public void setTiedosto(String nimi) {
        
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        tapahtumat.setTiedostonPerusNimi(hakemistonNimi + "tapahtumat");
        toiminnat.setTiedostonPerusNimi(hakemistonNimi + "toiminnat");
        aihealueet.setTiedostonPerusNimi(hakemistonNimi + "aihealueet");
        
    }
    
    
    /**
     * Lukee Tyorekisterin tiedot tiedostosta
     * @param nimi jota kaytetaan lukemisessa
     * @throws SisaltoException jos lukeminen epaonnistuu
     * @example
     * <pre name="test">
     * #THROWS SisaltoException
     * #import java.io.*;
     * #import java.util.*;
     * 
     * Tyorekisteri tyorekisteri = new Tyorekisteri();
     * 
     * Aihealue a1 = new Aihealue(); a1.rekisteroi(); a1.asetaKategoria();
     * Aihealue a2 = new Aihealue(); a2.rekisteroi(); a2.asetaKategoria();
     * 
     * Toiminto toi1 = new Toiminto(); toi1.rekisteroi(); toi1.luoTESTI_Toiminto();
     * Toiminto toi2 = new Toiminto(); toi2.rekisteroi(); toi2.luoTESTI_Toiminto();
     * 
     * Tapahtuma t1 = new Tapahtuma(); t1.luoTestiTapahtuma(1, 1);
     * Tapahtuma t2 = new Tapahtuma(); t2.luoTestiTapahtuma(2, 2);
     * 
     * String hakemisto = "testirekisteri";
     * File dir = new File(hakemisto);
     * File ftied = new File(hakemisto+"/tapahtumat.dat");
     * File fhtied = new File(hakemisto+"/toiminnat.dat");
     * File fatied = new File(hakemisto+"/aihealueet.dat");
     * dir.mkdir();
     * ftied.delete();
     * fhtied.delete();
     * fatied.delete();
     * tyorekisteri.lueTiedostosta(hakemisto); #THROWS SisaltoException
     * tyorekisteri.lisaa(a1);
     * tyorekisteri.lisaa(a2);
     * tyorekisteri.lisaa(toi1);
     * tyorekisteri.lisaa(toi2);
     * tyorekisteri.lisaa(t1);
     * tyorekisteri.lisaa(t2);
     * tyorekisteri.talleta();
     * tyorekisteri = new Tyorekisteri();
     * tyorekisteri.lueTiedostosta(hakemisto);
     * Collection<Tapahtuma> kaikki = tyorekisteri.annaTapahtumat();
     * List<Toiminto> loytyneet = tyorekisteri.annaToiminnat(t1);
     * tyorekisteri.talleta();
     * ftied.delete() === true;
     * fhtied.delete() === true;
     * fatied.delete() === true;
     * File fbak = new File(hakemisto+"/tapahtumat.bak");
     * fbak.delete() === false;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SisaltoException {
        
        tapahtumat = new Tapahtumat(); //jos luetaan olemassa olevaa niin helpon tyhjentaa nama
        toiminnat = new Toiminnat();
        aihealueet = new Aihealueet();
        
        setTiedosto(nimi);
        tapahtumat.lueTiedostosta();
        toiminnat.lueTiedostosta();
        aihealueet.lueTiedostosta();
        
    }
    
    
    /**
     * Tallettaa Tyorekisterin tiedot tiedostoon
     * @throws SisaltoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SisaltoException {
        
        String virhe = "";
        
        try {
            tapahtumat.talleta();
        } catch (SisaltoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            toiminnat.talleta();
        } catch (SisaltoException ex) {
            virhe += ex.getMessage();
        }
        
        try {
            aihealueet.talleta();
        } catch (SisaltoException ex) {
            virhe += ex.getMessage();
        }
        
        if (!"".equals(virhe)) throw new SisaltoException(virhe);

    }
    
    
    /**
     * Hakee tapahtuman
     * @return palauttaa valitun tapahtuman
     */
    public Tapahtuma getValittuTapahtuma() {
        return valittuTapahtuma;
    }
    
    
    /**
     * Alustaa valitun tapahtuman
     * @param tap Tapahtuma
     */
    public void setValittuTapahtuma(Tapahtuma tap) {
        this.valittuTapahtuma = tap;
    }
    
    
    /**
     * Testiohjelma Tyorekisterista
     * @param args ei kayteta
     * @throws SisaltoException e
     */
    public static void main(String args[]) throws SisaltoException {
        Tyorekisteri tyorekisteri = new Tyorekisteri();
        
        try {
            
            Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
            kouluhommat.rekisteroi();
            kouluhommat.asetaKategoria();
            kouluhommat2.rekisteroi();
            kouluhommat2.asetaKategoria();
            
            tyorekisteri.lisaa(kouluhommat);
            tyorekisteri.lisaa(kouluhommat2);
            
            int id1 = 1;
            int id2 = 2;
            
            
            Toiminto pitsi1 = new Toiminto(id1); pitsi1.luoTESTI_Toiminto(); tyorekisteri.lisaa(pitsi1);
            Toiminto pitsi2 = new Toiminto(id2); pitsi2.luoTESTI_Toiminto(); tyorekisteri.lisaa(pitsi2);
            
            int id3 = kouluhommat.getID();
            int id4 = pitsi1.getId();
            int id5 = kouluhommat2.getID();
            int id6 = pitsi2.getId();
            
            Tapahtuma toiminto1 = new Tapahtuma(id3, id4); toiminto1.luoTestiTapahtuma(id3, id4); tyorekisteri.lisaa(toiminto1);
            Tapahtuma toiminto2 = new Tapahtuma(id5, id6); toiminto2.luoTestiTapahtuma(id5, id6); tyorekisteri.lisaa(toiminto2);
            
            System.out.println("=========================== Työrekisterin testi =====================================");
            
                //List<Tapahtuma> tapahtumat = tyorekisteri.annaTapahtuma(1);
                for (int i = 0; i < tyorekisteri.getTapahtumia(); i++) {
                    Tapahtuma tapahtuma = tyorekisteri.annaTapahtuma(i);
                System.out.println("Tapahtuma on paikassa: " + i);
                tapahtuma.tulosta(System.out);
                    Aihealue aihealue = tyorekisteri.annaAihealue(1);
                    System.out.println("Aihealue on paikassa: " + i);
                    aihealue.tulosta(System.out);

                List<Toiminto> loytyneet3 = tyorekisteri.annaToiminnat(tapahtuma);
                List<Toiminto> loytyneet4 = tyorekisteri.annaToiminnat(tapahtuma);
                for (Toiminto toiminto : loytyneet3) {
                    toiminto.tulosta(System.out);
                }
                for (Toiminto toiminto : loytyneet4) {
                    toiminto.tulosta(System.out);
                }
            }
    } catch (SisaltoException ex) {
        System.out.println(ex.getMessage());
    }
  }


    /**
     * Korvaa tapahtuman tietoja
     * @param tapahtuma annettu tapahtuma
     * Tyorekisteri tyo = new Tyorekisteri();
     * 
     * int[] aloitus = { 15, 10 };
     * int[] aloitusP = { 12, 4, 2020 };
     * int[] lopetus = { 18, 0 };
     * int[] lopetusP = { 13, 4, 2020 };
     * Tapahtuma tapaht = new Tapahtuma(aloitus, aloitusP, lopetus, lopetusP, 1, 1, 15, 3);
     * tapaht.rekisteroi();
     * tyo.lisaa(tapaht);
     * 
     * int[] aloitus2 = { 15, 10 };
     * int[] aloitusP2 = { 12, 4, 2020 };
     * int[] lopetus2 = { 18, 0 };
     * int[] lopetusP2 = { 18, 4, 2020 };
     * Tapahtuma tapaht2 = new Tapahtuma(aloitus2, aloitusP2, lopetus2, lopetusP2, 1, 1, 15, 3);
     * tapaht2.rekisteroi(1);
     * tyo.korvaa(tapaht2);
     */
    public void korvaa(Tapahtuma tapahtuma) {
        tapahtumat.korvaa(tapahtuma);
        
    }


    /**
     * @param id haetun aihealueen id
     * @return id:n mukaan loydetty aihealue
     * @example
     * <pre name="test">
     * Tyorekisteri tyo = new Tyorekisteri();
     * Aihealue a = new Aihealue(), a2 = new Aihealue();
     * a.rekisteroi();
     * a2.rekisteroi();
     * tyo.lisaa(a);
     * tyo.lisaa(a2);
     * 
     * tyo.annaIdnMukaan(1) === a;
     * </pre>
     */
    public Aihealue annaIdnMukaan(int id) {
        return aihealueet.annaIdnMukaan(id);
    }
     
}