package rekisteriTietokanta.test;
// Generated by ComTest BEGIN
import java.io.*;
import java.util.Iterator;

import static org.junit.Assert.*;
import org.junit.*;
import rekisteriTietokanta.*;
// Generated by ComTest END
import java.awt.List;

/**
 * Test class made by ComTest
 * @version 2020.04.14 09:43:49 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class ToiminnatTest {



  // Generated by ComTest BEGIN
  /** testLisaa46 */
  @Test
  public void testLisaa46() {    // Toiminnat: 46
    Toiminnat toi = new Toiminnat(); 
    Toiminto t = new Toiminto(), t2 = new Toiminto(); 
    toi.lisaa(t); 
    toi.lisaa(t2); 
    assertEquals("From: Toiminnat line: 51", 2, toi.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta65 
   * @throws SisaltoException when error
   */
  @Test
  public void testLueTiedostosta65() throws SisaltoException {    // Toiminnat: 65
    Toiminnat toiminnat = new Toiminnat(); 
    Toiminto t1 = new Toiminto(), t2 = new Toiminto(); 
    t1.rekisteroi(); 
    t2.rekisteroi(); 
    t1.luoTESTI_Toiminto(); 
    t2.luoTESTI_Toiminto(); 
    String hakemisto = "testirekisteri"; 
    String tiedNimi = hakemisto+"/nimet"; 
    File ftied = new File(tiedNimi + ".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    toiminnat.lueTiedostosta(tiedNimi); 
    fail("Toiminnat: 81 Did not throw SisaltoException");
    } catch(SisaltoException _e_){ _e_.getMessage(); }
    toiminnat.lisaa(t1); 
    toiminnat.lisaa(t2); 
    toiminnat.talleta(); 
    Iterator<Toiminto> i = toiminnat.iterator(); 
    assertEquals("From: Toiminnat line: 86", t1.toString(), i.next().toString()); 
    assertEquals("From: Toiminnat line: 87", t2.toString(), i.next().toString()); 
    toiminnat.lisaa(t2); 
    toiminnat.talleta(); 
    assertEquals("From: Toiminnat line: 90", true, ftied.delete()); 
    assertEquals("From: Toiminnat line: 91", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetLkm179 */
  @Test
  public void testGetLkm179() {    // Toiminnat: 179
    Toiminnat toi = new Toiminnat(); 
    Toiminto t = new Toiminto(), t2 = new Toiminto(); 
    toi.lisaa(t); 
    toi.lisaa(t2); 
    assertEquals("From: Toiminnat line: 184", 2, toi.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaIdnMukaan198 */
  @Test
  public void testAnnaIdnMukaan198() {    // Toiminnat: 198
    Toiminnat toi4 = new Toiminnat(); 
    Toiminto testi1 = new Toiminto(), testi2 = new Toiminto(); 
    testi1.rekisteroi(); 
    testi2.rekisteroi(); 
    toi4.lisaa(testi1); 
    toi4.lisaa(testi2); 
    assertEquals("From: Toiminnat line: 205", null, toi4.annaIdnMukaan(1)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnnaToiminnat294 */
  @Test
  public void testAnnaToiminnat294() {    // Toiminnat: 294
    Toiminnat toi = new Toiminnat(); 
    Toiminto a = new Toiminto(); 
    Toiminto b = new Toiminto(); 
    toi.lisaa(a); 
    toi.lisaa(b); 
    java.util.List<Toiminto> lista = toi.annaToiminnat(); 
    assertEquals("From: Toiminnat line: 301", a, lista.get(0)); 
    assertEquals("From: Toiminnat line: 302", b, lista.get(1)); 
    assertEquals("From: Toiminnat line: 303", 2, lista.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnna321 */
  @Test
  public void testAnna321() {    // Toiminnat: 321
    Toiminnat toi = new Toiminnat(); 
    Toiminto a = new Toiminto(); 
    a.rekisteroi(); 
    Toiminto b = new Toiminto(); 
    b.rekisteroi(); 
    toi.lisaa(a); 
    toi.lisaa(b); 
    assertEquals("From: Toiminnat line: 329", a, toi.anna(1)); 
    assertEquals("From: Toiminnat line: 330", b, toi.anna(2)); 
  } // Generated by ComTest END
}