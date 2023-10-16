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

import hu.sintegroup.sinte_qr.R;

public class Kopoalkastreszek_adatai_ListViewAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> resource;

    public Kopoalkastreszek_adatai_ListViewAdapter(@NonNull Context context, ArrayList<String> resource) {
        super(context, R.layout.fragment_kopoalkatreszek_adatai_listview_item ,resource);
        this.resource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.fragment_kopoalkatreszek_adatai_listview_item, parent, false);

        TextView itemsText= convertView.findViewById(R.id.kopoalkatresz_item_text_view);
        itemsText.setText(resource.get(position));

        EditText itemsEditText= convertView.findViewById(R.id.kopoalkatresz_item_editText);
        itemsEditText.setHint(resource.get(position));

        return convertView;
    }
}
