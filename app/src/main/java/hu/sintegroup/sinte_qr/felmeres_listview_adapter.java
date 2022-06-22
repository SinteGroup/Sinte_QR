package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class felmeres_listview_adapter extends ArrayAdapter<felmeres> {

    private int resourceLayout;
    private Context mContext;
    private List<felmeres> items_list;

    public felmeres_listview_adapter(Context context, int resource) {
        super(context, resource);
        this.resourceLayout=resource;
        this.mContext=context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v==null){
            LayoutInflater vi;
            vi=LayoutInflater.from(mContext);
            v=vi.inflate(resourceLayout, null);
        }

        felmeres f=getItem(position);
        if(f!=null){
            TextView neveText=(TextView) v.findViewById(R.id.felmeresListViewItemTextView);
            if(neveText!=null){
                neveText.setText(f.get_felmeres_neve());
            }
        }

        return v;
    }
}
