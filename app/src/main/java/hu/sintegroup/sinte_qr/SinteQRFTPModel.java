package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PrimitiveIterator;

public class SinteQRFTPModel {
    public FTPClient SinteQrFTPClient = null;
    private Context context;
    private ListView ListV;

    public Boolean connect(String host, String username, String password, int port) {
        try {
            return new SinteQR_FTP_Helper(host, username, password, port).execute().get();
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> createSinteFTPFileList(Context context){

        this.context=context;
        try {
            return new SinteQR_FTP_FileList().execute().get();
        } catch (Exception e) {
            return null;
        }
    }

    public class SinteQR_FTP_Helper extends AsyncTask<Void, Void, Boolean> {

        private String host;
        private String username;
        private String password;
        private int port;

        public SinteQR_FTP_Helper(String host, String username, String password, int port) {
            this.port = port;
            this.username = username;
            this.password = password;
            this.host = host;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean status=false;
            try {

                SinteQrFTPClient = new FTPClient();
                SinteQrFTPClient.connect(host, port);
                status=SinteQrFTPClient.login(username, password);
                SinteQrFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                SinteQrFTPClient.enterLocalPassiveMode();
                Log.d("FTPOk", String.valueOf(status));

            } catch (Exception e) {
                Log.d("FTPErr", e.getMessage());
            }
            return status;
        }
    }

    public class SinteQR_FTP_FileList extends AsyncTask<Void, Void, ArrayList<String>> {

        public SinteQR_FTP_FileList() {

        }

        @Override
        protected  ArrayList<String> doInBackground(Void... voids) {

            ArrayList<String> fileList=new ArrayList<>();
            try {
                connect("ftp.weblapp.hu", "qr_ftp@weblapp.hu", "Ez66karakter", 21);

                fileList.addAll(Arrays.asList(SinteQrFTPClient.listNames()));
                for (String temp:fileList) {
                    Log.d("FTPfileList", temp);
                }

            } catch (Exception e) {
                Log.d("FTPErrFileList", e.getMessage());
            }
            return fileList;
        }
    }
}

