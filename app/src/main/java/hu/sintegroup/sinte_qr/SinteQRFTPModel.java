package hu.sintegroup.sinte_qr;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;

public class SinteQRFTPModel {
    public FTPClient SinteQrFTPClient = null;

    public boolean connect(String host, String username, String password, int port) {
        try {
            return new SinteQR_FTP_Helper(host, username, password, port).execute().get();
        } catch (Exception e) {
            return false;
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
            boolean retBool = false;
            try {

                SinteQrFTPClient = new FTPClient();
                SinteQrFTPClient.connect(host, port);
                boolean status = SinteQrFTPClient.login(username, password);
                SinteQrFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                SinteQrFTPClient.enterLocalPassiveMode();
                Log.d("FTPOk", String.valueOf(status));

                String[] fileList= SinteQrFTPClient.listNames();
                for (String temp:fileList) {
                    Log.d("FTPfileList", temp);
                }

                File fff=new File("sajat.txt");
                fff.createNewFile();
                /*FileInputStream inp=new FileInputStream(fff);
                SinteQrFTPClient.storeFile("ottani.txt", inp);*/

                SinteQrFTPClient.disconnect();
            } catch (Exception e) {
                Log.d("FTPErr", e.getMessage());
            }
            return retBool;
        }
    }
}

