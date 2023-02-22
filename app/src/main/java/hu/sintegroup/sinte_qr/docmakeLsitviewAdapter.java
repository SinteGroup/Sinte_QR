package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

public class docmakeLsitviewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> adatok=new ArrayList<>();
    private Context context;
    private Fragment fragment;
    private SinteQRFTPModel SinteQRFtpCliens;
    private ListView popUpFTPListView;

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

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        Boolean focusable=true;

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
                popUpFTPListView=(ListView)view.findViewById(R.id.FtpFileList);

                SinteAsync as=new SinteAsync();
                as.execute();
                View popUpView=inflater.inflate(R.layout.ftp_upload_popup_window, parent, false);
                PopupWindow repairMessagePopUp=new PopupWindow(popUpView, width, height, focusable);
                repairMessagePopUp.showAtLocation(view, Gravity.CENTER, 0, 0);
                /**/
            }
        });
        lin.addView(dokFeltöltésButton);
        return convertView;
    }

    private class SinteAsync extends AsyncTask<Void, String, Boolean> {

        protected Boolean doInBackground(Void... voids) {
            Log.d("FTPFut", "Fut");
            /*SinteQRFtpCliens=new SinteQRFTPModel();
            FtpPopupListViewAdapter adapter=new FtpPopupListViewAdapter(getContext(), R.layout.ftp_upload_popup_window_listview_item,SinteQRFtpCliens.createSinteFTPFileList(getContext()));
            popUpFTPListView.setAdapter(adapter);*/

            return true;
        }

        protected void onProgressUpdate(String... values){
            super.onProgressUpdate();
            Log.d("FTPUpdate", values[0]);
        }

        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            super.onPostExecute(result);
            Log.d("FTPMost", "Most"+" "+result);
        }
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
