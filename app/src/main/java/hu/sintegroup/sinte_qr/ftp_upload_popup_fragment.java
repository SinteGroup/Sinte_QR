package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ftp_upload_popup_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ftp_upload_popup_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

   private Ftp_file_list_adapter adapter;
   private ListView FTPLista;
   private ArrayList<String> fileList=new ArrayList<>();
   private FTPClient SinteQrFTPClient;
   private Boolean statusConnectOk=false;

    public ftp_upload_popup_fragment() {
        // Required empty public constructor
    }


    public static ftp_upload_popup_fragment newInstance(String param1, String param2) {
        ftp_upload_popup_fragment fragment = new ftp_upload_popup_fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ftp_upload_popup_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        FTPLista=(ListView) view.findViewById(R.id.FtpFileList);

        SinteFTPFileListAsync as=new SinteFTPFileListAsync();
        Log.d("FTPTaskResult", String.valueOf(as.execute().getStatus()));

        adapter=new Ftp_file_list_adapter(getContext(), R.layout.ftp_upload_popup_window_listview_item, fileList);
        FTPLista.setAdapter(adapter);
        Log.d("FTPAdapter", FTPLista.getAdapter().toString());
        SinteFTPFileUploadAsync upAS=new SinteFTPFileUploadAsync();
        upAS.execute(true);
    }

    private class SinteFTPFileListAsync extends AsyncTask<Void, String, Boolean> {

        protected Boolean doInBackground(Void... voids) {
            Log.d("FTPFut", "Fut");

            Boolean status=false;

            SinteQrFTPClient = new FTPClient();
            try {
                SinteQrFTPClient.connect("ftp.weblapp.hu", 21);
                status=SinteQrFTPClient.login("qr_ftp@weblapp.hu", "Ez66karakter");
                SinteQrFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                SinteQrFTPClient.enterLocalPassiveMode();
                Log.d("FTPOk", String.valueOf(status));
                fileList.addAll(Arrays.asList(SinteQrFTPClient.listNames()));

            } catch (Exception e) {
                Log.e("FTPErrBelso", e.getMessage());
            }
            return status;
        }

        protected void onProgressUpdate(String... values){
            super.onProgressUpdate();
            Log.d("FTPUpdate", values[0]);
        }

        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            super.onPostExecute(result);
            try {
                adapter.notifyDataSetChanged();
            }catch (Exception f){
                Log.e("FTPAdapterNotifyErr", f.getMessage());
            }
            statusConnectOk=result;
            Log.d("FTPMost", "Most"+" "+statusConnectOk);
        }
    }

    private class SinteFTPFileUploadAsync extends AsyncTask<Boolean, String, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... statusParameter) {
            Boolean statusOk=false;
            Log.d("FTPInternal", String.valueOf(getContext().getApplicationInfo().dataDir));

            if(statusParameter[0]){
                try {
                    SinteQrFTPClient.sendCommand("OPTS UTF8 ON");
                    String InputfilenameAndPath="\\Belső tárhely\\Documents\\QRCode_printing.pdf";
                    String OutputFilenameAndPath="\\QRCode_printing.pdf";
                    FileInputStream fis=new FileInputStream(new File(InputfilenameAndPath));
                    SinteQrFTPClient.storeFile(OutputFilenameAndPath, fis);

                } catch (IOException e) {
                    Log.e("FTPFileUploadErr", e.getMessage());
                }
            }
            return statusOk;
        }

        protected void onProgressUpdate(String... values){
            super.onProgressUpdate();
        }

        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            super.onPostExecute(result);
            Log.d("FTPMostUpload", "MostUP"+" "+result);
        }
    }

    }