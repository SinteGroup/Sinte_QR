package hu.sintegroup.sinte_qr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import hu.sintegroup.sinte_qr.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    TextView firstText=null;
    Spinner kodeSpin=null;
    HashMap hh=null;
    ArrayAdapter aa=null;
    sinteQRFirebaseHelper helper=new sinteQRFirebaseHelper();

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstText=(TextView) view.findViewById(R.id.initTextview);
        kodeSpin=(Spinner) view.findViewById(R.id.codeSpinner);
        kodeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("SelectedItem", String.valueOf(hh.get(aa.getItem(i).toString())));
                String adat=String.valueOf(hh.get(aa.getItem(i).toString())).replace("{" , "").replace("}", "").replace("=",": ");
                String[] adatSorok=adat.split(",");
                //firstText.setText(String.valueOf(hh.get(aa.getItem(i).toString())));
                String kimenet="";
                for (String temp:adatSorok){
                    Log.d("adatSorok", temp);
                    kimenet+=(temp+"\n");
                }
                firstText.setText(kimenet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            getData("Felmeresek");
        }catch (Exception g) {
            Log.d("getData", g.getMessage());
        }
        binding.newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_QRReaderFragment);
            }
        });

        binding.newManualItemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Date datum=new Date();
                    QRReadFragment.firebasePath="Felmeresek/Kezimegadas_"+ datum.getTime();

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_AdatfelvetelFragment);
            }
        });
    }

    public void getData(String child){

        helper.adatbázisReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    hh= (HashMap) dataSnapshot.child(child).getValue();
                    aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,hh.keySet().toArray());
                    kodeSpin.setAdapter(aa);
                }catch (Exception h){
                    Log.d("onDataChange", h.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}