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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        EditText tempEditText=(EditText)view.findViewById(R.id.addColumnEditText);

        final String[] responseString = {""};
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://www.weblapp.hu/Proba.php?method=check";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] tempString=response.split("_end_");
                        String[] tempString2=tempString[0].split("<>");
                        Log.d("Volley", "Response is: " + response);
                        for (String temp : tempString2){
                            if(/*temp.contains(filterTag)*/true){
                                Log.d("URLVolley", temp);    //:: kulcs és értéket elválasztó szeparátor
                                //responseString[0] =temp;            // <> kulcs-érték párokat elválasztó szeparátor
                                if(!temp.contains("Datum")) {
                                    checkLista.add(new Javitasok.Check(temp));
                                }

                                ArrayAdapter<Javitasok.Check> checkListViewAdapter=new checkListViewAdapter(checkLista, getContext());
                                final Integer[] i = {0};
                                Button checkAddItemButton=(Button)view.findViewById(R.id.addCheckItemButton);
                                checkAddItemButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if(!String.valueOf(tempEditText.getText()).isEmpty()) {
                                            Javitasok.Check tempCheck = new Javitasok.Check(String.valueOf(tempEditText.getText()));
                                            checkLista.add(tempCheck);

                                            String customColumnUrl="https://www.weblapp.hu/Proba.php?method=insertcheckcolumn&column_name="+String.valueOf(tempEditText.getText());
                                            Log.d("customColumnUrl", customColumnUrl);
                                            StringRequest customColumnStringRequest=new StringRequest(Request.Method.GET, customColumnUrl, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("beagyazottResponseOk", response);
                                                }
                                            }, new  Response.ErrorListener(){

                                                public void onErrorResponse(VolleyError error) {
                                                    Log.e("beagyazottResponseErr", error.getMessage());
                                                }
                                            });
                                            queue.add(customColumnStringRequest);

                                        }else {
                                            Toast.makeText(getContext(), "Üres a név mező.", Toast.LENGTH_LONG).show();
                                        }
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