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
import android.widget.TextView;
import android.widget.Toast;

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
        TextView adatFelv=(TextView)view.findViewById(R.id.adatFelveteliLap);
        adatFelv.append(": "+QRReadFragment.firebasePath);

        EditText gepSzama=(EditText)view.findViewById(R.id.gepSzama);
        EditText gepNeve=(EditText)view.findViewById(R.id.gepNeve);
        EditText gepLeltariSzama=(EditText)view.findViewById(R.id.gepLeltariszama);
        EditText gepElhelyezkedese=(EditText)view.findViewById(R.id.gepElhelyezkedes);
        EditText gepFajtaja=(EditText)view.findViewById(R.id.gepFajtaja);
        EditText gepMerete=(EditText)view.findViewById(R.id.gepMerete);
        EditText gepMunkavegzesJellege=(EditText)view.findViewById(R.id.gepMunakvegzesJellegege);
        EditText gepMeghajtasFordulat=(EditText)view.findViewById(R.id.gepMeghajtasFordulat);
        EditText gepMeghajtasKihajtas=(EditText)view.findViewById(R.id.gepMeghajtasKihajtas);
        EditText gepMeghajtasOljatipus=(EditText)view.findViewById(R.id.gepOlajTipus);
        EditText gepHevederMerete=(EditText)view.findViewById(R.id.gepHevedermerete);
        EditText gepDobokTengelyHosszok=(EditText)view.findViewById(R.id.dobokTengelyhosszok);
        EditText gepTengelyhosszokEgyformake=(EditText)view.findViewById(R.id.gepDobokEgyformak);
        EditText gepVezetoGorgo=(EditText)view.findViewById(R.id.gepVewzetoGorooAdatok);
        EditText gepCsapagyazasTengelyen=(EditText)view.findViewById(R.id.gepCsapagyazasaTengelyen);
        EditText gepAlsoCsapagyAdatok=(EditText)view.findViewById(R.id.gepAlsoCsapagyadatok);
        EditText gepCsapagyszam=(EditText)view.findViewById(R.id.gepCsapagyszam);
        EditText gepCsapgyHazszam=(EditText)view.findViewById(R.id.gepCsapagyHazszam);
        EditText gepForgaserzekelo=(EditText)view.findViewById(R.id.gepForgaserzekelo);
        EditText gepOldalfutasErzekelo=(EditText)view.findViewById(R.id.gepOldalfutasErzekelo);
        EditText gepHomero=(EditText)view.findViewById(R.id.gepAllapotFelmeresiLeiras);

        EditText gepKeziQRMegadas=(EditText) view.findViewById(R.id.gepQRSzama);
        

        Button saveButton=(Button) view.findViewById(R.id.gepAdataFelvetel_Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gepek ujGep=new Gepek();

                ujGep.setGepQRSzama(gepKeziQRMegadas.getText().toString());

                ujGep.setGepSzama(gepSzama.getText().toString());
                ujGep.setGepNeve(gepNeve.getText().toString());
                ujGep.setGepLeltariSzama(gepLeltariSzama.getText().toString());
                ujGep.setGepElhelyezkedese(gepElhelyezkedese.getText().toString());
                ujGep.setGepFajtaja(gepFajtaja.getText().toString());
                ujGep.setGepMerete(gepMerete.getText().toString());
                ujGep.setGepMunkavegzesJellege(gepMunkavegzesJellege.getText().toString());
                ujGep.setGepFordulat(gepMeghajtasFordulat.getText().toString());
                ujGep.setGepKihajtas(gepMeghajtasKihajtas.getText().toString());
                ujGep.setGepOlajTipus(gepMeghajtasOljatipus.getText().toString());
                ujGep.setGepHevedermerete(gepHevederMerete.getText().toString());
                ujGep.setGepTengelyHosszok(gepDobokTengelyHosszok.getText().toString());
                ujGep.setGepTengelyhosszokEgyformak(gepTengelyhosszokEgyformake.getText().toString());
                ujGep.setGepCsapagyazasTengelyen(gepCsapagyazasTengelyen.getText().toString());
                ujGep.setGepAlsoCsapagy(gepAlsoCsapagyAdatok.getText().toString());
                ujGep.setGepCsapagyszam(gepCsapagyszam.getText().toString());
                ujGep.setGepCsapagyHazsszam(gepCsapgyHazszam.getText().toString());
                ujGep.setGepCsapagyForgaserzekelo(gepForgaserzekelo.getText().toString());
                ujGep.setGepOldalfutasErzekelo(gepOldalfutasErzekelo.getText().toString());
                ujGep.setGepHomero(gepHomero.getText().toString());

                sinteQRFirebaseHelper helperReader=new sinteQRFirebaseHelper();
                Log.d("ujgep", ujGep.getGepQRSzama());
                QRReadFragment.firebasePath="Felmeresek/"+ujGep.getGepQRSzama();
                if(ujGep.getGepQRSzama().isEmpty()){
                    Toast.makeText(getContext(), "Üres a QR kód mező", Toast.LENGTH_LONG).show();
                }else {
                    helperReader.adatbázisReferencia.child(QRReadFragment.firebasePath).setValue(ujGep);
                }

            }
        });
    }
}