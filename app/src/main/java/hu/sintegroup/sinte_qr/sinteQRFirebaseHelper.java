package hu.sintegroup.sinte_qr;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class sinteQRFirebaseHelper {
    FirebaseDatabase adatbazis=null;
    DatabaseReference adatbázisReferencia =null;
    Gepek alapBeallitasGepek;

    public sinteQRFirebaseHelper(){
        if(adatbazis==null || adatbázisReferencia==null){
            adatbazis=FirebaseDatabase.getInstance();
            adatbázisReferencia=adatbazis.getReference();
            //setDefaultData();
        }
    }

    public void setDefaultData() {

        adatbázisReferencia.child("Alapbeállítások").setValue(alapBeallitasGepek);
        // adatbázisReferencia.child("Alapbeállítások").child("Berendezesek: ").setValue(alapBeallitasokBerendezesek);

    }
}
