package hu.sintegroup.sinte_qr;

import android.annotation.SuppressLint;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

            RequestQueue queue = Volley.newRequestQueue(getContext());

            Button checkItemOkButton=(Button) convertView.findViewById(R.id.checkOkButton);
            checkItemOkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OkButton", "OK: "+position);
                    String CheckOkButtonUrl="http://www.weblapp.hu/Proba.php?method=insert&column="+String.valueOf(elemCime.getText())+"&values=Ok";
                    StringRequest CheckOkButtonRequest=new StringRequest(Request.Method.GET, CheckOkButtonUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("CheckOkNyugta", response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("CheckOkNyugtaErr", error.getMessage());
                        }
                    });
                    queue.add(CheckOkButtonRequest);
                }
            });

            Button checkItemRepairButton=(Button) convertView.findViewById(R.id.checkRepairButton);
            checkItemRepairButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("RepairButton", "Repair: "+position);

                    View popUpViiew=inflater.inflate(R.layout.repair_popup_window, null);

                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    Boolean focusable=true;

                    PopupWindow repairMessagePopUp=new PopupWindow(popUpViiew, width, height, focusable);
                    repairMessagePopUp.showAtLocation(view, Gravity.CENTER, 0, 0);

                    EditText repairMessage=(EditText)popUpViiew.findViewById(R.id.getRepairMessageEditText);
                    RequestQueue queueRepair=Volley.newRequestQueue(getContext());

                    Button repairSendMessageButton=(Button)popUpViiew.findViewById(R.id.repairWindowOkButton);
                    repairSendMessageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String CheckRepairButtonUrl="http://www.weblapp.hu/Proba.php?method=insert&column="+elemCime.getText()+"&values="+repairMessage.getText();
                            Log.d("CheckRepairButtonUrl", CheckRepairButtonUrl);
                            StringRequest repairMessageRequest=new StringRequest(Request.Method.GET, CheckRepairButtonUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("repair", response);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("repairErr", error.getMessage());
                                }
                            });
                            queueRepair.add(repairMessageRequest);
                        }
                    });
                }
            });

            Button itemDeleteButton=(Button) convertView.findViewById(R.id.checkitemDelete);
            itemDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deleteMySQLColumnUrl="http://www.weblapp.hu/Proba.php?method=deleteitem&column="+elemCime.getText();
                    Log.d("deleteUrl", deleteMySQLColumnUrl);
                    RequestQueue deleteQueue=Volley.newRequestQueue(getContext());
                    StringRequest deleteRequest=new StringRequest(Request.Method.GET, deleteMySQLColumnUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            adatok.remove(tempCheck);
                            notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    deleteQueue.add(deleteRequest);
                }
            });

        return convertView;
    }

    }
