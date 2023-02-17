package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class adatfelveteli_lap_listview_adapter extends ArrayAdapter<String> {

    ArrayList<String> adatfelveteli_Items;

    public adatfelveteli_lap_listview_adapter(@NonNull Context context, @NonNull ArrayList<String> objects) {
        super(context, R.layout.adatfelvetil_lap_listview_item, objects);
        this.adatfelveteli_Items=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.adatfelvetil_lap_listview_item, parent, false);

        TextView adatfelveteli_mezo=(TextView) convertView.findViewById(R.id.adatfelveti_item_text_view);
        adatfelveteli_mezo.setText(adatfelveteli_Items.get(position));

        return convertView;
    }
}
