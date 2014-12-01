package Mallit;

import java.sql.Timestamp;

/**
 *
 * @author leo
 */
public class HoitoOhje {
    private int id;
    private Timestamp lisaysajankohta;
    private String ohje;
    
    public HoitoOhje(int id, Timestamp lisaysajankohta, String ohje) {
        this.id = id;
        this.lisaysajankohta = lisaysajankohta;
        this.ohje = ohje;
    }
    
    public HoitoOhje() {}
    
    public int getId() {
        return this.id;
    }
    
    public Timestamp getLisaysajankohta() {
        return this.lisaysajankohta;
    }
    
    public String getOhje() {
        return this.ohje;
    }
    
    public void setId(int uusiId) {
        this.id = uusiId;
    }
    
    public void setLisaysajankohta(Timestamp uusiLisaysajankohta) {
        this.lisaysajankohta = uusiLisaysajankohta;
    }
    
    public void setOhje(String uusiOhje) {
        this.ohje = uusiOhje;
    }
}
