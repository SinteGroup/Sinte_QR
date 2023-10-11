package hu.sintegroup.sinte_qr;

import android.content.Context;

import java.net.MalformedURLException;

public class SinteSqlDatabaseHelper {

    private Context context;
    private String qrData;

    public SinteSqlDatabaseHelper(Context context, String qrData) throws MalformedURLException {
        this.context=context;
        this.qrData=qrData;
    }

}
