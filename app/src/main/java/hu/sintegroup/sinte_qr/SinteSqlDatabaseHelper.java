package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;

public class SinteSqlDatabaseHelper {

    private Context context;
    private String qrData;

    public SinteSqlDatabaseHelper(Context context, String qrData) throws MalformedURLException {
        this.context=context;
        this.qrData=qrData;
    }

}
