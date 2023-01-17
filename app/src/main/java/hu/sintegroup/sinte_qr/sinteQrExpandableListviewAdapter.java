package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sinteQrExpandableListviewAdapter extends BaseExpandableListAdapter {

    Context context=null;
    HashMap<String, List<String>> items;

    sinteQrExpandableListviewAdapter(Context context, HashMap<String, List<String>> items){
        this.context=context;
        this.items=items;
    }
    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.items.size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View convertview, ViewGroup viewGroup) {

        if(convertview==null){
            LayoutInflater inflate=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview=inflate.inflate(R.layout.javitasoklistviewitem, null);
        }
        TextView itemTextView=(TextView) convertview.findViewById(R.id.itemTitleTextview);
        itemTextView.setText("Item: "+position+" "+items);
        return convertview;
    }

    @Override
    public View getChildView(int position, int i1, boolean b, View convertview, ViewGroup viewGroup) {
        if(convertview==null){
            LayoutInflater inflate=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview=inflate.inflate(R.layout.javitasoklistviewchilditem, null);
        }
        TextView itemTextView=(TextView) convertview.findViewById(R.id.listviewItemChildTextView);
        itemTextView.setText("Item: "+position+" "+items);
        return convertview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
