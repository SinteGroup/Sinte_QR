package hu.sintegroup.sinte_qr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Kopoalkatreszek_adatai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Kopoalkatreszek_adatai extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Kopoalkatreszek_adatai() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Kopoalkatreszek_adatai.
     */
    // TODO: Rename and change types and number of parameters
    public static Kopoalkatreszek_adatai newInstance(String param1, String param2) {
        Kopoalkatreszek_adatai fragment = new Kopoalkatreszek_adatai();
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
        return inflater.inflate(R.layout.fragment_kopoalkatreszek_adatai, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        String kopoalkatreszKategoria=getArguments().getString("alkatreszkategioria");
        TextView alkatreszNeveTextView=(TextView) view.findViewById(R.id.kopoalkatreszNeveTextView);
        alkatreszNeveTextView.setText(kopoalkatreszKategoria);

        ListView kopoalkatreszLista=(ListView) view.findViewById(R.id.kopoalkatreszKategoriaListView);
        ArrayList<String> kopoalkatreszItems=new ArrayList<>();
        Kopoalkastreszek_adatai_ListViewAdapter kopoAdapter=new Kopoalkastreszek_adatai_ListViewAdapter(this.getContext(), kopoalkatreszItems);

        RequestQueue kopoalkatresz_listazo_queqe= Volley.newRequestQueue(getContext());
        String kopoalkatreszKategoriaURL="http://www.weblapp.hu/Proba.php?method=kopoalkatreszek&alkatresz="+kopoalkatreszKategoria;

        StringRequest kopoalkatreszRequest=new StringRequest(Request.Method.GET, kopoalkatreszKategoriaURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("kopoResponse", response);
                kopoalkatreszItems.addAll(Arrays.asList(response.split("<>")));
                kopoalkatreszLista.setAdapter(kopoAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kopoResponseErr", error.getMessage());
            }
        });
        kopoalkatresz_listazo_queqe.add(kopoalkatreszRequest);
    }
}