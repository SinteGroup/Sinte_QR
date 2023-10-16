package hu.sintegroup.sinte_qr.adapterek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Hashtable;

import hu.sintegroup.sinte_qr.R;

public class adatfelveteli_lap_listview_adapter extends ArrayAdapter<String> {

    ArrayList<String> adatfelveteli_Items;
    ArrayList<String> adatFelvetliEdittextContent=new ArrayList<>();
    Hashtable<String, String> editTextItems=new Hashtable<>(); //HashTable-ben a key az adatkategória és a value az adat értéke.
    public adatfelveteli_lap_listview_adapter(@NonNull Context context, @NonNull ArrayList<String> objects, Hashtable<String, String> editTextItems) {
        super(context, R.layout.adatfelvetil_lap_listview_item, objects);
        this.adatfelveteli_Items=objects;
        this.editTextItems=editTextItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.adatfelvetil_lap_listview_item, parent, false);

        TextView adatfelveteli_mezo = convertView.findViewById(R.id.adatfelveti_item_text_view);
        adatfelveteli_mezo.setText(adatfelveteli_Items.get(position));

        EditText adatFelveteliItem = convertView.findViewById(R.id.adatfelveteli_item_editText_view);
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

        adatFelveteliItem.setText(editTextItems.get(adatfelveteli_mezo.getText()));
        return convertView;
    }
}
