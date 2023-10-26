package hu.sintegroup.sinte_qr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

import hu.sintegroup.sinte_qr.adapterek.adatfelveteli_lap_listview_adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdatfelvetelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdatfelvetelFragment extends Fragment {

    ArrayList<String> adatfelveteli_mezok=new ArrayList<>();
    Hashtable<String, String> edittextItems=new Hashtable<>();

    ListView adatfelveteli_listView=null;
    adatfelveteli_lap_listview_adapter adatfelveteliAdapter=null;


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adatfelvetel, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        adatfelveteli_listView = view.findViewById(R.id.adatfelvetelilap_Listview);
        adatfelveteliAdapter = new adatfelveteli_lap_listview_adapter(getContext(), adatfelveteli_mezok, edittextItems);

        String columns_2 ="";
        String values_2="";

        Button SqlBeButton= view.findViewById(R.id.AdatbázisbaMentesButton);
        SqlBeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adatfelveteliAdapter.notifyDataSetChanged();
                try {
                    Set<String> keys=edittextItems.keySet();
                    for (String temp:keys) {
                        //Log.d("AdatokLekérdezése", "Felvett adatok: "+temp+": "+edittextItems.get(temp)); //Felolvasni a halmazt egy stringbe és azt írni a linkbe. Így lesz automatikus.
                        columns_2.concat("'"+temp+"="+edittextItems.get(temp)+"', ");
                        values_2.concat("'"+edittextItems.get(temp)+"', "); //Érték
                        Log.d("InsertColumn_2", columns_2);
                    }
                }catch (Exception f){
                    Log.e("AdatokfelvételiHiba", f.getMessage());
                }

                String insertDataUrl="http://www.weblapp.hu/Proba.php?method=insertData&column="+columns_2+"&values="+values_2; //SQL insert stringet előállítani
                Log.d("InsertURL", insertDataUrl);

                RequestQueue insertDataQueqe= Volley.newRequestQueue(getContext());
                StringRequest insertDataStringRequest=new StringRequest(Request.Method.GET, insertDataUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("insertDataLog", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("insertDataLogError", error.getMessage());
                    }
                });
                insertDataQueqe.add(insertDataStringRequest);

            }
        });

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