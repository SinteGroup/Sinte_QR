package hu.sintegroup.sinte_qr.adapterek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import hu.sintegroup.sinte_qr.R;

public class Ftp_file_list_adapter extends ArrayAdapter<String> {

    private final ArrayList<String> datasource;


    public Ftp_file_list_adapter(@NonNull Context context, int resource, ArrayList<String> dataSource) {
        super(context, resource, dataSource);
        this.datasource=dataSource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.ftp_upload_popup_window_listview_item, parent, false);

        TextView fileListTextTemp= convertView.findViewById(R.id.fileListNameTextView);
        fileListTextTemp.setText(datasource.get(position));

        return convertView;
    }
}
