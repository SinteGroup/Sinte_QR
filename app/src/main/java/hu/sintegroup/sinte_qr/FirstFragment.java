package hu.sintegroup.sinte_qr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

/*import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;*/

import java.util.HashMap;

import hu.sintegroup.sinte_qr.helpers.sinteQRFirebaseHelper;
import hu.sintegroup.sinte_qr.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    TextView firstText=null;
    Spinner kodeSpin=null;
    HashMap datasnapshotHashMap =null;
    ArrayAdapter spinnerArrayAdapter =null;
    sinteQRFirebaseHelper helper=new sinteQRFirebaseHelper();

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstText= view.findViewById(R.id.initTextview);
        kodeSpin= view.findViewById(R.id.codeSpinner);
        kodeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("SelectedItem", String.valueOf(datasnapshotHashMap.get(spinnerArrayAdapter.getItem(i).toString())));
                String adat=String.valueOf(datasnapshotHashMap.get(spinnerArrayAdapter.getItem(i).toString())).replace("{" , "").replace("}", "").replace("=",": ");
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
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_QRReadFragment);
            }
        });

        /*binding.newManualItemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Date datum=new Date();
                    QRReadFragment.firebasePath="Felmeresek/Kezimegadas_"+ datum.getTime();

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_AdatfelvetelFragment);
            }
        });*/

        /*binding.checkListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_CheckListFragment);
            }
        });*/
    }

    public void getData(String child){

        /*helper.adatbázisReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    datasnapshotHashMap = (HashMap) dataSnapshot.child(child).getValue();
                    spinnerArrayAdapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, datasnapshotHashMap.keySet().toArray());
                    kodeSpin.setAdapter(spinnerArrayAdapter);
                }catch (Exception h){
                    Log.d("onDataChange", h.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}