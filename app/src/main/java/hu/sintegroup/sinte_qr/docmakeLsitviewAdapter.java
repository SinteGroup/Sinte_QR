package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

        String[] itemString=adatok.get(position).split(":");

        LinearLayout slinContainer=(LinearLayout)convertView.findViewById(R.id.listViewContainer);
        slinContainer.setHorizontalGravity(Gravity.CENTER);

        LinearLayout lin=(LinearLayout) convertView.findViewById(R.id.listviewItemContainer);

        TextView idTextView=new TextView(this.getContext());
        idTextView.setText(itemString[0]);
        idTextView.setTextSize(20);
        idTextView.setMaxWidth(500);
        lin.addView(idTextView);

        if(!(itemString[0].equals("Csapágy száma"))) {

            EditText itemEditText = new EditText(this.getContext());
            itemEditText.setText(itemString[1]);
            itemEditText.setTextSize(20);
            itemEditText.setMaxWidth(400);
            lin.addView(itemEditText);

        }else {
            generalas(slinContainer, adatok, position);
        }
        return convertView;
    }

    public void generalas(LinearLayout linSuperContainer, ArrayList<String> adatok, int position){

        LinearLayout linTempLocal=new LinearLayout(getContext());
        linTempLocal.setOrientation(LinearLayout.HORIZONTAL);
        linTempLocal.setHorizontalGravity(Gravity.CENTER);

        EditText itemEditText = new EditText(getContext());
        itemEditText.setText(adatok.get(position).split(":")[0]);
        itemEditText.setTextSize(20);
        itemEditText.setMaxWidth(400);
        linTempLocal.addView(itemEditText);

        String[] spinnerItems={"fdssfs", "fdsfdssfs", "gfdsgfdgfdgfd"};
        Spinner itemSpinner=new Spinner(getContext());
        itemSpinner.setMinimumHeight(150);
        ArrayAdapter spinnerItemsArrayadapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,spinnerItems);
        itemSpinner.setAdapter(spinnerItemsArrayadapter);
        linTempLocal.addView(itemSpinner);

        Log.d("ClickBelül", "Click");

        Button spinneritemButton=new Button(this.getContext());
        spinneritemButton.setText("Bővebben");
        spinneritemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generalas(linSuperContainer, adatok, position);
                Log.d("ItemId", adatok.get(position).split(":")[0]);
                Log.d("ItemContent", adatok.get(position).split(":")[1]);
            }
        });
        linTempLocal.addView(spinneritemButton);
        linSuperContainer.addView(linTempLocal);
    }
}
