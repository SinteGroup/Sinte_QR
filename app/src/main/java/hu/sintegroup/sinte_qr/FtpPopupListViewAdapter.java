package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class FtpPopupListViewAdapter extends ArrayAdapter<String> {

    public ArrayList<String> fileList;

    public FtpPopupListViewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> fileList) {
        super(context, resource, fileList);
        this.fileList=fileList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.ftp_upload_popup_window, parent, false);

        TextView fileItemText=(TextView) convertView.findViewById(R.id.fileListNameTextView);
        fileItemText.setText(fileList.get(position));

        return convertView;
    }
}
