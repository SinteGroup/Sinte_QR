package hu.sintegroup.sinte_qr;

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

public class docmakeLsitviewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> adatok=new ArrayList<>();

    public docmakeLsitviewAdapter(ArrayList<String> adatok, Context context) {
        super(context, R.layout.qr_lista_listview_item, adatok);
        this.adatok=adatok;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.qr_lista_listview_item, parent, false);

        EditText qrListTempText=(EditText) convertView.findViewById(R.id.qr_lista_data);
        TextView qrListCategoryTempText=(TextView)convertView.findViewById(R.id.qr_lista_category_name_textview);

        try {
            String[] tempAdatok = adatok.get(position).split(": ");
            qrListCategoryTempText.setText(tempAdatok[0]);
            qrListTempText.setText(tempAdatok[1]);
        }catch (Exception f){
            Log.d("Err", f.getMessage());
        }
        return convertView;
    }
}
