package hu.sintegroup.sinte_qr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.checkerframework.checker.units.qual.A;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdatfelvetelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdatfelvetelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdatfelvetelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdatfelvetelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdatfelvetelFragment newInstance(String param1, String param2) {
        AdatfelvetelFragment fragment = new AdatfelvetelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adatfelvetel, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Gepek ujGep=new Gepek();

        EditText gep_szama=(EditText) view.findViewById(R.id.gepSzamaEditText);
        ujGep.setID(gep_szama.getText().toString());

        EditText gep_Neve=(EditText) view.findViewById(R.id.gepNeveEditText);
        ujGep.setNeve(gep_Neve.getText().toString());

        EditText leltari_szam=(EditText) view.findViewById(R.id.gepLeltariSzama);
        ujGep.setLeltari_szama(leltari_szam.getText().toString());

        EditText gep_fajtaja=(EditText) view.findViewById(R.id.gepFajtajaEditText);
        ujGep.setFajtaja(gep_fajtaja.getText().toString());

        EditText gep_merete=(EditText) view.findViewById(R.id.gepMereteEditText);
        ujGep.setMerete(gep_merete.getText().toString());

        EditText gepElhelyezkedese=(EditText) view.findViewById(R.id.geElhelyezkedeseEditText);
        ujGep.setElhelyezkedese(gepElhelyezkedese.getText().toString());

        Button saveButton=(Button) view.findViewById(R.id.gepAdataFelvetel_Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinteQRFirebaseHelper helperReaer=new sinteQRFirebaseHelper();
                helperReaer.adatbázisReferencia.child("Felmeresek/"+QRReadFragment.firebasePath).setValue(ujGep);
                Log.e("Adatok", ujGep.getNeve());
            }
        });
    }
}