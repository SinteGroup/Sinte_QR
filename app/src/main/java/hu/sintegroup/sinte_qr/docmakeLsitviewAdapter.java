package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.apache.commons.net.ftp.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class docmakeLsitviewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> adatok=new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public docmakeLsitviewAdapter(ArrayList<String> adatok, Context context, Fragment fragment) {
        super(context, R.layout.qr_lista_listview_item, adatok);
        this.adatok=adatok;
        this.context=context;
        this.fragment=fragment;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.qr_lista_listview_item, parent, false);

        String[] itemString=adatok.get(position).split(":");

        LinearLayout slinContainer=(LinearLayout)convertView.findViewById(R.id.listViewContainer);

        LinearLayout lin=(LinearLayout) convertView.findViewById(R.id.listviewItemContainer);

        TextView idTextView=new TextView(this.getContext());
        idTextView.setText(itemString[0]);
        idTextView.setTextSize(20);
        idTextView.setMinWidth(500);
        lin.addView(idTextView);

        if(!(itemString[0].equals("Kopóalkatrész"))) {

            EditText itemEditText = new EditText(this.getContext());
            itemEditText.setText(itemString[1]);
            itemEditText.setTextSize(20);
            itemEditText.setMinWidth(400);
            lin.addView(itemEditText);

        }else {
            generalas(slinContainer, adatok, position);
        }

        Button dokFeltöltésButton=new Button(this.getContext());
        dokFeltöltésButton.setText("Feltöltés");
        dokFeltöltésButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FTPClient doksiUploadClient=new FTPClient();
                    doksiUploadClient.connect(InetAddress.getByName("ftp.weblapp.hu"), 21);
                    doksiUploadClient.login("qr_ftp@weblapp.hu", "Ez66karakter");
                    doksiUploadClient.enterLocalPassiveMode();
                    doksiUploadClient.setFileType(FTP.BINARY_FILE_TYPE);
                    Log.d("FTPClient", doksiUploadClient.getStatus());
                }catch (Exception f){
                    Log.d("FTPClient", f.getMessage());
                }
            }
        });
        lin.addView(dokFeltöltésButton);
        return convertView;
    }

    public void generalas(LinearLayout linSuperContainer, ArrayList<String> adatok, int position){

        LinearLayout linTempLocal=new LinearLayout(getContext());
        linTempLocal.setOrientation(LinearLayout.HORIZONTAL);

        EditText itemEditText = new EditText(getContext());
        itemEditText.setText(adatok.get(position).split(":")[0]);
        itemEditText.setTextSize(20);
        itemEditText.setMaxWidth(400);
        linTempLocal.addView(itemEditText);

        String[] spinnerItems={"Csapágyak","Csapágyházak","Simmeringek","Meghajtólánc","Szállítólánc","Villanymotor","Hajtómű","Serlegkanál","Heveder","Szenzorok","Egyéb"};
        Spinner itemSpinner=new Spinner(getContext());
        itemSpinner.setMinimumHeight(250);
        ArrayAdapter spinnerItemsArrayadapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,spinnerItems);
        itemSpinner.setAdapter(spinnerItemsArrayadapter);
        linTempLocal.addView(itemSpinner);

        Log.d("ClickBelül", "Click");

        Button spinneritemButton=new Button(this.getContext());
        spinneritemButton.setText("Bővebben");
        spinneritemButton.setMinWidth(250);
        spinneritemButton.setMinimumHeight(150);
        spinneritemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generalas(linSuperContainer, adatok, position);
                Bundle kopoalkatreszKategoria=new Bundle();
                kopoalkatreszKategoria.putString("alkatreszkategioria", itemSpinner.getSelectedItem().toString());
                NavHostFragment.findNavController(fragment).navigate(R.id.action_DocMakeFragment_to_KopoalkatreszekFragment, kopoalkatreszKategoria);
                Log.d("ItemId", itemSpinner.getSelectedItem().toString());
            }
        });
        linTempLocal.addView(spinneritemButton);
        linSuperContainer.addView(linTempLocal);
    }
}
