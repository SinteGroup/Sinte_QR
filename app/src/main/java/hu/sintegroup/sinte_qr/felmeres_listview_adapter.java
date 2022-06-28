package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class felmeres_listview_adapter extends ArrayAdapter<Gepek> {

    private int resourceLayout;
    private Context mContext;

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
        TextView neveText=(TextView) v.findViewById(R.id.felmeresListViewItemTextView);
        /*Gepek f=getItem(position);
        if(f!=null){
            if(neveText!=null){
                neveText.setText(f.get_felmeres_neve());
            }
        }*/
        return v;
    }
}
