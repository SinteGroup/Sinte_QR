package hu.sintegroup.sinte_qr.helpers;

import android.content.Context;

import java.net.MalformedURLException;

public class SinteSqlDatabaseHelper {

    private final Context context;
    private final String qrData;

    public SinteSqlDatabaseHelper(Context context, String qrData) throws MalformedURLException {
        this.context=context;
        this.qrData=qrData;
    }

}
