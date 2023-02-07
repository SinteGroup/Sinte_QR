package hu.sintegroup.sinte_qr;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;

import hu.sintegroup.sinte_qr.databinding.FragmentDocMakeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocMakeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocMakeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentDocMakeBinding binding;
    public DocMakeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocMakeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocMakeFragment newInstance(String param1, String param2) {
        DocMakeFragment fragment = new DocMakeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDocMakeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String QrMeresszama=getArguments().getString("QrMeresszama");
        Log.d("QrAdatok", QrMeresszama);
        TextView meresNeve=(TextView) view.findViewById(R.id.meresNeve);
        meresNeve.setText(QrMeresszama);

        ListView QrSqlAdatokListView=(ListView) view.findViewById(R.id.QrSQLadatokListView);
        ArrayList<String> adatok=new ArrayList<>();
        ArrayAdapter QRListViewAdapter=new docmakeLsitviewAdapter(adatok, getContext());

        String QRReadUrl=MainActivity.baseDomain+MainActivity.executePhp+"?method=felmeres&QrSzama="+QrMeresszama;
        RequestQueue listazo_queqe= Volley.newRequestQueue(getContext());
        StringRequest QrReadRequesst=new StringRequest(Request.Method.GET, QRReadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("QrReadRequesst", "Minden OK");
                String[] responseTemp=response.split("<>");
                for (String temp: responseTemp) {
                    if(temp.split(": ").length > 1) {
                        adatok.add(temp);
                        Log.d("QrReadRequesst", temp);
                    }
                }

                QrSqlAdatokListView.setAdapter(QRListViewAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("QrReadRequesst", "Minden szar: "+error.getMessage());
            }
        });
        listazo_queqe.add(QrReadRequesst);

        Button javitasokraButton=(Button) view.findViewById(R.id.JavításButton);
        javitasokraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle qrjvadatok=new Bundle();
                qrjvadatok.putString("qrmeresszama", QrMeresszama);
                NavHostFragment.findNavController(DocMakeFragment.this).navigate(R.id.action_DocMakeFragment_to_JavitasokFragment, qrjvadatok);
            }
        });

        Button adatfelvetelButton=(Button) view.findViewById(R.id.AdatfelvetelButton);
        adatfelvetelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DocMakeFragment.this).navigate(R.id.action_DocMakeFragment_to_AdatfelvetelFragment);
            }
        });

        Button ellenorzoListaButton=(Button) view.findViewById(R.id.EllenorzoListaButton);
        ellenorzoListaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DocMakeFragment.this).navigate(R.id.action_DocMakeFragment_to_CheckListFragment);
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

// Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
          Log.d("ConfChanges", "Landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
          Log.d("ConfChanges", "Portrait");
        }

    }
}