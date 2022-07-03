package hu.sintegroup.sinte_qr;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import io.opencensus.metrics.export.Summary;

public class sinteQRFirebaseHelper {
    FirebaseDatabase adatbazis=null;
    DatabaseReference adatbázisReferencia =null;
    Gepek alapBeallitasGepek;
    Gepek.Berendezesek alapBeallitasokBerendezesek;

    public sinteQRFirebaseHelper(){
        if(adatbazis==null || adatbázisReferencia==null){
            adatbazis=FirebaseDatabase.getInstance();
            adatbázisReferencia=adatbazis.getReference();
        }

        if(adatbazis!=null && adatbázisReferencia!=null){

            alapBeallitasGepek =new Gepek();  //Gépek beépített listája
            ArrayList<String> gepTipusokTomb=new ArrayList<>();
            gepTipusokTomb.add("Betároló gépek");
            gepTipusokTomb.add("Gyártósor");
            gepTipusokTomb.add("Kitároló gépek");
            gepTipusokTomb.add("Anyagmozgató gép");
            gepTipusokTomb.add("Tároló eszközök");
            gepTipusokTomb.add("Feldolgozó gépek");
            gepTipusokTomb.add("Folyadékbedolgozó eszközök");
            gepTipusokTomb.add("Mérő eszközök, mérlegek");
            gepTipusokTomb.add("Fluidszállítás eszközei");
            gepTipusokTomb.add("Sűrítettlevegő ellátás");
            gepTipusokTomb.add("Adagoló eszközök");
            gepTipusokTomb.add("Anyagtovábbítás eszközei");
            gepTipusokTomb.add("Egyebek");
            alapBeallitasGepek.setGepTipusok(gepTipusokTomb);

            ArrayList<String> tempMukavegzesJellege=new ArrayList<>();
            tempMukavegzesJellege.add("Folyamatos");
            tempMukavegzesJellege.add("Közepes");
            tempMukavegzesJellege.add("Kevés");
            alapBeallitasGepek.setGep_Munkavegzes_jellege(tempMukavegzesJellege);

            ArrayList<String> tempBerendezesek=new ArrayList<>();  //Berendezések beépített listája
            tempBerendezesek.add("Tolózár");
            tempBerendezesek.add("Váltó");
            alapBeallitasokBerendezesek=new Gepek.Berendezesek();
            alapBeallitasokBerendezesek.setTipusai(tempBerendezesek);
            setDefaultData();
        }
    }

    public void setDefaultData() {

        adatbázisReferencia.child("Alapbeállítások").setValue(alapBeallitasGepek);
        adatbázisReferencia.child("Alapbeállítások").child("Berendezesek: ").setValue(alapBeallitasokBerendezesek);
        adatbázisReferencia.child("Felmérés").setValue("");


    }

    public DataSnapshot getData(String child) throws Exception{

        DataSnapshot returnSnapshot=null;
        adatbázisReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Get_gepTipusok", String.valueOf(dataSnapshot.child("Alapbeállítások/gepTipusok").getValue().toString()));
                Log.d("Get_gep_Munkavegzes", String.valueOf(dataSnapshot.child("Alapbeállítások/gep_Munkavegzes_jellege").getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return returnSnapshot;

    }

}
