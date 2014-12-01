package Mallit;

import java.sql.Timestamp;

/**
 *
 * @author leo
 */
public class Potilasraportti extends Potilastieto {

    public Potilasraportti(int id, Timestamp lisaysajankohta, String raportti) {
        super(id, lisaysajankohta, raportti);
    }

    public Potilasraportti() {}
    
}
