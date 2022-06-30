package hu.sintegroup.sinte_qr;

import java.util.ArrayList;

public class Gepek {

    public Gepek(){

    }

    private ArrayList<String> GepTipusok;
    private ArrayList<String> Berendezesek;
    private ArrayList<Integer> Merete;
    private ArrayList<String> Gep_Munkavegzes_jellege;
    private ArrayList<Alkatreszek_adatai> AlkatreszekAdatai;

    private String ID; //Gepek száma
    private String Neve;
    private String leltari_szama;
    private String Elhelyezkedese;
    private String Fajtaja;

    public void setAlkatreszekAdatai(ArrayList<Alkatreszek_adatai> alkatreszekAdatai) {
        AlkatreszekAdatai = alkatreszekAdatai;
    }

    public void setBerendezesek(ArrayList<String> berendezesek) {
        Berendezesek = berendezesek;
    }

    public void setElhelyezkedese(String elhelyezkedese) {
        Elhelyezkedese = elhelyezkedese;
    }

    public void setFajtaja(String fajtaja) {
        Fajtaja = fajtaja;
    }

    public void setGep_Munkavegzes_jellege(ArrayList<String> gep_Munkavegzes_jellege) {
        Gep_Munkavegzes_jellege = gep_Munkavegzes_jellege;
    }

    public void setGepTipusok(ArrayList<String> gepTipusok) {
        GepTipusok = gepTipusok;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLeltari_szama(String leltari_szama) {
        this.leltari_szama = leltari_szama;
    }

    public void setMerete(ArrayList<Integer> merete) {
        Merete = merete;
    }

    public void setNeve(String neve) {
        Neve = neve;
    }

    public String getElhelyezkedese() {
        return Elhelyezkedese;
    }

    public String getFajtaja() {
        return Fajtaja;
    }

    public String getID() {
        return ID;
    }

    public String getLeltari_szama() {
        return leltari_szama;
    }

    public String getNeve() {
        return Neve;
    }

    public ArrayList<Integer> getMerete() {
        return Merete;
    }

    public ArrayList<String> getBerendezesek() {
        return Berendezesek;
    }

    public ArrayList<String> getGep_Munkavegzes_jellege() {
        return Gep_Munkavegzes_jellege;
    }

    public ArrayList<String> getGepTipusok() {
        return GepTipusok;
    }

    public ArrayList<Alkatreszek_adatai> getAlkatreszekAdatai() {
        return AlkatreszekAdatai;
    }

    public static class Berendezesek{
        public Berendezesek(){

        }
        private ArrayList<String> tipusai;

        public void setTipusai(ArrayList<String> tipusai) {
            this.tipusai = tipusai;
        }

        public ArrayList<String> getTipusai() {
            return tipusai;
        }
    }
}
