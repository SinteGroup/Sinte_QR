package hu.sintegroup.sinte_qr;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class CheckListFragment extends Fragment {

    private String filterTag="2022-000-131";

    public CheckListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView checkListView=(ListView) view.findViewById(R.id.checkLista);
        ArrayList<Javitasok.Check> checkLista=new ArrayList<>();

        final String[] responseString = {""};
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://www.weblapp.hu/Proba.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volley", "Response is: " + response.contains(filterTag));
                        String[] tempString=response.split("_end_");
                        for (String temp : tempString){
                            if(temp.contains(filterTag)){
                                Log.d("URLVolley", temp);    //:: kulcs és értéket elválasztó szeparátor
                                responseString[0] =temp;            // <> kulcs-érték párokat elválasztó szeparátor
                                                                    //Iderakni az összes módosítót :(

                                checkLista.add(new Javitasok.Check("Mukodik"));
                                checkLista.add(new Javitasok.Check("Mukodik"));
                                checkLista.add(new Javitasok.Check("Mukodik"));

                                ArrayAdapter<Javitasok.Check> checkListViewAdapter=new checkListViewAdapter(checkLista, getContext());
                                final Integer[] i = {0};
                                Button checkAddItemButton=(Button)view.findViewById(R.id.addCheckItemButton);
                                checkAddItemButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        i[0] +=1;
                                        Javitasok.Check tempCheck=new Javitasok.Check("Elem "+i[0]);
                                        checkLista.add(tempCheck);
                                        checkListViewAdapter.notifyDataSetChanged();
                                    }
                                });
                                checkListView.setAdapter(checkListViewAdapter);

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "That didn't work!"+" "+error.networkResponse);
            }
        });
        queue.add(stringRequest);
    }
}