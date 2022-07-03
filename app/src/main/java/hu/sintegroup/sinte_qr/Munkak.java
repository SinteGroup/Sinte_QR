package hu.sintegroup.sinte_qr;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Munkak {

    public Munkak(){

    }

    private String ID;
    private String driveLink;
    private ArrayList<String> cegek;
    private ArrayList<String> telephelyek;
    private ArrayList<String> üzemek;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDriveLink(String driveLink) {
        this.driveLink = driveLink;
    }

    public String getID() {
        return ID;
    }

    public String getDriveLink() {
        return driveLink;
    }

    public void setCegek(ArrayList<String> cegek) {
        this.cegek = cegek;
    }

    public void setTelephelyek(ArrayList<String> telephelyek) {
        this.telephelyek = telephelyek;
    }

    public void setÜzemek(ArrayList<String> üzemek) {
        this.üzemek = üzemek;
    }

    public ArrayList<String> getCegek() {
        return cegek;
    }

    public ArrayList<String> getTelephelyek() {
        return telephelyek;
    }

    public ArrayList<String> getÜzemek() {
        return üzemek;
    }
}
