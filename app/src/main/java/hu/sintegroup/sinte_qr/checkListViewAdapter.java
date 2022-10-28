package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class checkListViewAdapter extends ArrayAdapter<Javitasok.Check> {

    private ArrayList<Javitasok.Check> adatok=new ArrayList<>();

    public checkListViewAdapter(ArrayList<Javitasok.Check> elemek, Context context) {
        super(context, R.layout.checklistitem, elemek);
        this.adatok=elemek;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

            Javitasok.Check tempCheck=adatok.get(position);
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.checklistitem, parent, false);

            TextView elemCime=(TextView) convertView.findViewById(R.id.checkListItemId);
            elemCime.setText(tempCheck.checkName);

            Button checkItemOkButton=(Button) convertView.findViewById(R.id.checkOkButton);
            checkItemOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkButton", "OK: "+position);
                }
            });

            Button checkItemRepairButton=(Button) convertView.findViewById(R.id.checkRepairButton);
            checkItemRepairButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkButton", "Repair: "+position);
                }
            });

        return convertView;
    }

    }
