package hu.sintegroup.sinte_qr;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sinteQRFirebaseHelper {
    FirebaseDatabase adatbazis=null;
    DatabaseReference adatbázisReferencia =null;

    public sinteQRFirebaseHelper(){
        if(adatbazis==null || adatbázisReferencia==null){
            adatbazis=FirebaseDatabase.getInstance();
            adatbázisReferencia=adatbazis.getReference();
        }
    }

    public void setData(String data) {
        Alkatreszek_adatai.Meghajtas nn=new Alkatreszek_adatai.Meghajtas();
        nn.set_Fordulat(1000);
        nn.set_Kihajtas("Sima");
        nn.set_OlajTipus("10W40");

        adatbázisReferencia.child("Teszt").child("Alkatrészek adatai: ").setValue(nn);
    }

}
