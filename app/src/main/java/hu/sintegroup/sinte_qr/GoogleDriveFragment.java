package hu.sintegroup.sinte_qr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoogleDriveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleDriveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String kozvetlenMappaLink="https://drive.google.com/drive/u/1/folders/1Oi00rA4fTwfaA-_ASqpEf8XNwHNZCrne";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoogleDriveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleDriveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleDriveFragment newInstance(String param1, String param2) {
        GoogleDriveFragment fragment = new GoogleDriveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_google_drive, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        WebView driveWeb=(WebView) view.findViewById(R.id.googleDriveWebview); //Megírni, majd ha minden működik, hogy a google driveval szinkronizáljon.
        driveWeb.setWebViewClient(new WebViewClient());                         //Első verziónak ennyi is jó.
        driveWeb.getSettings().setLoadsImagesAutomatically(true);
        driveWeb.getSettings().setJavaScriptEnabled(true);
        driveWeb.getSettings().setBuiltInZoomControls(true);
        driveWeb.getSettings().setSupportZoom(true);
        driveWeb.getSettings().setLoadWithOverviewMode(true);
        driveWeb.getSettings().setUseWideViewPort(true);
        driveWeb.getSettings().setAllowContentAccess(true);
        driveWeb.loadUrl(kozvetlenMappaLink);
    }

}