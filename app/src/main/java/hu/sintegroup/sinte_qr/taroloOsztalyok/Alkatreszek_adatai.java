package hu.sintegroup.sinte_qr.taroloOsztalyok;

import java.util.ArrayList;

public class Alkatreszek_adatai {
    private String Oldalfutás_érzékelő;
    private String Homero;

    public  Alkatreszek_adatai(){

    }

    public void setHomero(String homero) {
        Homero = homero;
    }

    public void setOldalfutás_érzékelő(String oldalfutás_érzékelő) {
        Oldalfutás_érzékelő = oldalfutás_érzékelő;
    }

    public String getHomero() {
        return Homero;
    }

    public String getOldalfutás_érzékelő() {
        return Oldalfutás_érzékelő;
    }

    public static class Meghajtas{
        public Meghajtas(){

        }

        private int Fordulat;
        private String Kihajtas;
        private String OlajTipus;

        public void set_Fordulat(int fordulat_Temp){
            this.Fordulat=fordulat_Temp;
        }

        public void set_Kihajtas(String kihajtas){
            this.Kihajtas=kihajtas;
        }

        public void set_OlajTipus(String olajtipus){
            this.OlajTipus=olajtipus;
        }

        public int get_Fordulat(){
            return this.Fordulat;
        }

        public String get_Kihajtas(){
            return this.Kihajtas;
        }

        public String get_OlajTipus(){
            return this.OlajTipus;
        }

    }

    public class Heveder_merete{
        public Heveder_merete(){

        }
        private int Szelessege;
        private String Pontos_tipus;
        private String Kanalosztas;

        public void setSzelessege(int szelessege){
            this.Szelessege=szelessege;
        }

        public void setPontos_tipus(String pontosTipus){
            this.Pontos_tipus=pontosTipus;
        }

        public void setKanalosztas(String kanalosztas){
            this.Kanalosztas=kanalosztas;
        }

        public int getSzelessege(){
            return  this.Szelessege;
        }

        public String getPontos_tipus(){
            return this.Pontos_tipus;
        }

        public String getKanalosztas(){
            return this.Kanalosztas;
        }
    }

    public class Dobok_merete{
        public Dobok_merete(){

        }

        private ArrayList<Integer> Tengelyhosszok;
        private Boolean Egyformak_e;
        private String Vezeto_gorgo;
        private String csapagyazas_tengelyen;

        public void setTengelyHossz(int hossz){
            this.Tengelyhosszok.add(hossz);
        }

        public void setEgyformak_e(Boolean egyformak_e) {
            Egyformak_e = egyformak_e;
        }

        public void setVezeto_gorgo(String vezeto_gorgo) {
            Vezeto_gorgo = vezeto_gorgo;
        }

        public void setCsapagyazas_tengelyen(String csapagyazas_tengelyen) {
            this.csapagyazas_tengelyen = csapagyazas_tengelyen;
        }

        public ArrayList<Integer> getTengelyhosszok() {
            return Tengelyhosszok;
        }

        public Boolean getEgyformak_e() {
            return Egyformak_e;
        }

        public String getCsapagyazas_tengelyen() {
            return csapagyazas_tengelyen;
        }

        public String getVezeto_gorgo() {
            return Vezeto_gorgo;
        }
    }

    public class Egyeb_alkatreszek {

        public Egyeb_alkatreszek(){

        }

        private String Megnevezes;
        private String Meretek;
        private String Eszrevetelek;
        private String Leiras;

        public void setMegnevezes(String megnevezes) {
            Megnevezes = megnevezes;
        }

        public void setEszrevetelek(String eszrevetelek) {
            Eszrevetelek = eszrevetelek;
        }

        public void setLeiras(String leiras) {
            Leiras = leiras;
        }

        public void setMeretek(String meretek) {
            Meretek = meretek;
        }

        public String getEszrevetelek() {
            return Eszrevetelek;
        }

        public String getMegnevezes() {
            return Megnevezes;
        }

        public String getLeiras() {
            return Leiras;
        }

        public String getMeretek() {
            return Meretek;
        }
    }



}
