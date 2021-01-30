package rekisteriTietokanta;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tyorekisterin aihealue
 * 
 * @author musaj ja jussil
 * @version 13.4.2020
 * Vastuualueet:
 *  - tietaa
 *      - id
 *      - kategoria
 *  - osaa tehda ja kasitella kategoria tietoa
 */
public class Aihealue {
    
    
    private int id;
    private String kategoria = "";
    private static int testiNumero;
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan aihealue. Testeja varten.
     */
    public Aihealue() {
        //
    }
    
    /**
     * Alustetaan aihealue.
     * @param kategoria annettu kategoria
     */
    public Aihealue(String kategoria) {
        this.kategoria = kategoria;
    }
    
    /**
     * Palauttaa haetun Aihealueen id:n
     * @return palauttaa ID:n
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
     * kouluhommat.rekisteroi();
     * kouluhommat2.rekisteroi();
     * kouluhommat.asetaKategoria();
     * kouluhommat.getID() === 3;
     * kouluhommat2.getID() === 4; 
     * </pre>
     */
    public int getID() {
        return this.id;
    }
    
    
    /**
     * Palauttaa haetun Aihealueen kategorian
     * @return Aihealueen kategoria
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue();
     * kouluhommat.asetaKategoria();
     * kouluhommat.getKategoria() === "Kouluhommat";
     * </pre>
     */
    public String getKategoria() {
        return kategoria;
    }
    
    
    /**
     * Rekisteroi aihealueen antamalla sille yksiloidyn id:n. seuraavaNro kasvaa yhdella.
     * @return palauttaa asetetun ID:n
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue();
     * kouluhommat.rekisteroi();
     * kouluhommat.getID() === 2;
     * </pre>
     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    
    /**
     * Rekisteroi aihealueen annetulla id:lla.
     * @param numero annettu id
     * @return palauttaa asetetun ID:n
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue();
     * kouluhommat.rekisteroi(2);
     * kouluhommat.getID() === 2;
     * </pre>
     */
    public int rekisteroi(int numero) {
        id = numero;
        return id;
    }
    
    
    /**
     * Apumetodi jolla saadan taytettya testiarvot aihealueelle.
     * @param numero yksiloiva testinumero
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue();
     * kouluhommat.rekisteroi();
     * kouluhommat.asetaKategoria();
     * kouluhommat.getKategoria() === "Kouluhommat";
     * </pre>
     */
    public void asetaKategoria(@SuppressWarnings("unused") int numero) { 
        kategoria = "Kouluhommat";
    }
    
    
    /**
     * Apumetodi jolla saadan taytettya testiarvot aihealueelle.
     * Testinumeroa kasvatetaan aina yhdella, jotta kahdella kategorialla ei voi ole samoja tietoja
     * @example
     * <pre name="test">
     * Aihealue kouluhommat = new Aihealue();
     * kouluhommat.rekisteroi();
     * kouluhommat.asetaKategoria();
     * kouluhommat.getKategoria() === "Kouluhommat";
     * </pre>
     */
    public void asetaKategoria() {
        testiNumero++;
        asetaKategoria(testiNumero);
    }
    
    
    /**
     * Tulostetaan kategorian tiedot
     * @param out tietovirta johon tulostetaan
     * </pre
     */
    public void tulosta (PrintStream out) {
        out.print(String.format("%s", getKategoria()) + "\n");
        
    }
    
    
    /**
     * Tulostetaan kategorian tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Palauttaa aihealue tiedot merkkijonoja jonka voi tallentaa tiedostoon
     * return aihealue tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Aihealue aihealue = new Aihealue();
     * aihealue.parse("   1 |  Kouluhommat");
     * aihealue.toString().startsWith("1|Kouluhommat") === false;
     * </pre>
     */
    @Override
    public String toString() {
        return "" +
                getID() + "|" +
                getKategoria();
    }
    
    
    /**
     * Selvittaa aihealueen tiedot | erotellusta merkkijonosta
     * @param rivi josta aihealueen tiedot otetaan
     * @example
     * <pre name="test">
     * Aihealue aihealue = new Aihealue();
     * aihealue.parse("   1 |   Kouluhommat");
     * aihealue.getID() === 1;
     * aihealue.toString().startsWith("1|Kouluhommat") === false;
     * </pre>
     */
    public void parse(String rivi) {
        
        StringBuffer sb = new StringBuffer(rivi);
        id = (Mjonot.erota(sb, '|', id));
        
        if (id > (seuraavaNro - 1)) {
            seuraavaNro = (id + 1);
        }
        
        kategoria = (Mjonot.erota(sb, ' ', ""));
        
    }
    

    /**
     * Testiohjelma aihealueelle
     * @param args ei kayteta
     */
    public static void main(String args[]) {
        
        Aihealue kouluhommat = new Aihealue(), kouluhommat2 = new Aihealue();
        kouluhommat.rekisteroi();
        kouluhommat2.rekisteroi();
        
        kouluhommat.asetaKategoria();
        kouluhommat.tulosta(System.out);
        
        kouluhommat2.asetaKategoria();
        kouluhommat2.tulosta(System.out);
        
    }
    
    
}
