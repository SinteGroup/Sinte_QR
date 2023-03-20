package hu.sintegroup.sinte_qr;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

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

        ListView adatfelveteli_listView=(ListView) view.findViewById(R.id.adatfelvetelilap_Listview);
        ArrayList<String> adatfelveteli_mezok=new ArrayList<>();
        Hashtable<String, String> edittextItems=new Hashtable<>();
        adatfelveteli_lap_listview_adapter adatfelveteliAdapter=new adatfelveteli_lap_listview_adapter(getContext(), adatfelveteli_mezok, edittextItems);

        RequestQueue adatfelveteli_listazo_queqe= Volley.newRequestQueue(getContext());
        String adatfelveteli_lap_URL="http://www.weblapp.hu/Proba.php?method=adatfelveteli_lap";

        StringRequest adatfelveteli_lap_req=new StringRequest(Request.Method.GET, adatfelveteli_lap_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adatfelveteli_mezok.addAll(Arrays.asList(response.split("<>")));
                adatfelveteli_listView.setAdapter(adatfelveteliAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        adatfelveteli_listazo_queqe.add(adatfelveteli_lap_req);

    }
}