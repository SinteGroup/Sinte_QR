package hu.sintegroup.sinte_qr;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.TELECOM_SERVICE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRReadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int QRREADOK = 1;

    public QRReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRReadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRReadFragment newInstance(String param1, String param2) {
        QRReadFragment fragment = new QRReadFragment();
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
        return inflater.inflate(R.layout.fragment_q_r_read, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Intent cmaIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cmaIntent, QRREADOK);
        Log.d("Cam_intent: ", "intent elment");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        if (requestCode == QRREADOK && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.d("Cam_Bundle getExtras", extras.toString());
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            TextView tempData=(TextView)getView().findViewById(R.id.QRDataSnapshotView);
            /*sinteQRFirebaseHelper readDatabase=new sinteQRFirebaseHelper();
            tempData.setText(readDatabase.getData(readQRImage(imageBitmap)).getValue().toString());*/
            Log.d("Cam_eredmeny", readQRImage(imageBitmap));
        }
        }catch (Exception f){
            Log.d("Cam_OnAct: ", f.getMessage());
            Toast.makeText(getContext(), "Nem érvényes QR code. A kódot merőlegesen kell leolvasni és más nem lehet rajta.", Toast.LENGTH_SHORT).show();
        }
    }

    public static String readQRImage(Bitmap bMap) {
        String contents = "Üres";
        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();// use this otherwise ChecksumExceptionú
        Log.d("Cam_bitmap", bitmap.toString());
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        } catch (NotFoundException e) { Log.d("Cam_NotFound", e.getMessage()); }
        catch (ChecksumException e) { Log.d("Cam_Checksum: ", e.getMessage()); }
        catch (FormatException e) { Log.d("Cam_format: ", e.getMessage()); }
        return contents;
    }
}