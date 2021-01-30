package rekisteriTietokanta;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author musaj ja jussil
 * @version 1.4.2020
 *
 */
public class SisaltoException extends Exception{
    
    
    private static final long serialVersionUID = 1L;
    
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
     * @param viesti poikkeuksen viesti
     */
    public SisaltoException(String viesti) {
        super(viesti);
    }
    
    
}
