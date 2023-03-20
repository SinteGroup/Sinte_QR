package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.internal.TextWatcherAdapter;
import com.google.gson.internal.bind.JsonTreeReader;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class adatfelveteli_lap_listview_adapter extends ArrayAdapter<String> {

    ArrayList<String> adatfelveteli_Items;
    ArrayList<String> adatFelvetliEdittextContent=new ArrayList<>();
    Hashtable<String, String> editTextItems=new Hashtable<>();
    public adatfelveteli_lap_listview_adapter(@NonNull Context context, @NonNull ArrayList<String> objects, Hashtable<String, String> editTextItems) {
        super(context, R.layout.adatfelvetil_lap_listview_item, objects);
        this.adatfelveteli_Items=objects;
        this.editTextItems=editTextItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.adatfelvetil_lap_listview_item, parent, false);

        TextView adatfelveteli_mezo = (TextView) convertView.findViewById(R.id.adatfelveti_item_text_view);
        adatfelveteli_mezo.setText(adatfelveteli_Items.get(position));

        EditText adatFelveteliItem = (EditText) convertView.findViewById(R.id.adatfelveteli_item_editText_view);
        adatFelveteliItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    Log.d("LostFocus", "Locus is lost: "+adatFelveteliItem.getText()+" Elemszám: "+editTextItems.size());
                    if(!String.valueOf(adatFelveteliItem.getText()).isEmpty()){
                        editTextItems.put(String.valueOf(adatfelveteli_mezo.getText()), String.valueOf(adatFelveteliItem.getText()));
                        for (String temp:editTextItems.keySet()) {
                            Log.d("ArrayEdittextItems", " TextView: "+temp+" "+editTextItems.get(temp));
                        }
                    }
                }
            }
        });

        if(!adatFelvetliEdittextContent.isEmpty()) {
            adatFelveteliItem.setText(adatFelvetliEdittextContent.get(position));
        }



        return convertView;
    }
}
