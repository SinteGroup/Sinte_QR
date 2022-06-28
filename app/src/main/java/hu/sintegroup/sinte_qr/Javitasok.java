package hu.sintegroup.sinte_qr;

import java.util.Date;

public class Javitasok {
    public Javitasok(){

    }

    private Date Datum;
    private String Megnevezes;
    private String Nulla_uzemora_szam;
    private String Eszrevetel;

    public void setDatum(Date datum) {
        Datum = datum;
    }

    public void setMegnevezes(String megnevezes) {
        Megnevezes = megnevezes;
    }

    public void setNulla_uzemora_szam(String nulla_uzemora_szam) {
        Nulla_uzemora_szam = nulla_uzemora_szam;
    }

    public void setEszrevetel(String eszrevetel) {
        Eszrevetel = eszrevetel;
    }

    public Date getDatum() {
        return Datum;
    }

    public String getMegnevezes() {
        return Megnevezes;
    }

    public String getEszrevetel() {
        return Eszrevetel;
    }

    public String getNulla_uzemora_szam() {
        return Nulla_uzemora_szam;
    }
}
