package hu.sintegroup.sinte_qr;

import static android.app.Activity.RESULT_OK;
import static android.hardware.camera2.CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE;
import static android.hardware.camera2.CameraCharacteristics.STATISTICS_INFO_MAX_FACE_COUNT;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
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

import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashMap;

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
    TextView QRREaderText;

    private FirebaseApp app;

    private TextureView cameraSurfaceView=null;

    public static String firebasePath = "";

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
            this.app = FirebaseApp.initializeApp(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_read, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        cameraSurfaceView=(TextureView) view.findViewById(R.id.QRREadSurface);
        myCameraStart(); //Preview létrehozása QRReadFragmentre
    }

    private void myCameraStart(){

        CameraManager camMan = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);

        try {

            if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //return;
                Log.d("CamM", "Nincs meg a camera és az engedélyezés");
            }
            camMan.openCamera("1", cameraStatecallback, cameraStateBackgroundHandler);
        } catch (CameraAccessException e) {
            Log.d("CamManEx", e.getMessage());
        }catch (Exception Ex){
            Log.d("CamManExp", Ex.getMessage());
        }
    }

    private CameraDevice.StateCallback cameraStatecallback =new CameraDevice.StateCallback(){

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Log.d("CamManOpen", "CamOpened");

        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.d("CamManDisconnect", "CamDisconnect");
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            Log.d("CamError", "CamError");
        }

        public void onConfigured(@NonNull CameraCaptureSession session) throws CameraAccessException {

        }
    };

    private void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = cameraSurfaceView.getSurfaceTexture();
            assert texture != null;
            Surface surface = new Surface(texture);

            mPreviewRequestBuilder = CameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);
            mCameraDevice.createCaptureSession(Arrays.asList(surface),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            if (null == mCameraDevice) {
                                return;
                            }

                            mCaptureSession = cameraCaptureSession;
                            try {
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL\_AF\_MODE,
                                        CaptureRequest.CONTROL\_AF\_MODE\_CONTINUOUS\_PICTURE);

                                mPreviewRequest = mPreviewRequestBuilder.build();
                                mCaptureSession.setRepeatingRequest(mPreviewRequest,
                                        null, null);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(
                                @NonNull CameraCaptureSession cameraCaptureSession) {
                        }
                    }, null
            );
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private HandlerThread backgroundHandlerThread;
    private Handler cameraStateBackgroundHandler;

    private void startBackgroundThread(){
        backgroundHandlerThread=new HandlerThread("CameraVideoThread");
        backgroundHandlerThread.start();
        cameraStateBackgroundHandler =new Handler(backgroundHandlerThread.getLooper());
    }

    private void stopBackgroundThread() throws InterruptedException {
        backgroundHandlerThread.quitSafely();
        backgroundHandlerThread.join();
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        if (requestCode == QRREADOK && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Log.d("Cam_Bundle getExtras", extras.toString());
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            sinteQRFirebaseHelper readerHelper=new sinteQRFirebaseHelper();
            readerHelper.adatbázisReferencia.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        HashMap map = (HashMap) dataSnapshot.child("Felmeresek").getValue();
                        Log.d("readerMap", map.values().toString());
                        String readString = readQRImage(imageBitmap);
                        if (map.containsKey(readString)) {
                            Log.d("readerError", "Van");
                            firebasePath="Felmeresek/"+readString;

                            QRREaderText.setText("fgsdgdfgdfg");

                            NavHostFragment.findNavController(QRReadFragment.this).navigate(R.id.action_QRRead_Fragment_to_AdatfelvetelFragment);
                        } else {
                            Log.d("readerError", "Nincs: "+readString);
                            firebasePath="Felmeresek/"+readString;
                            readerHelper.adatbázisReferencia.getRoot().child(firebasePath).setValue(""); //Átnézni a pth-t
                        }
                    }catch (Exception g){
                        Log.d("ReadderOnDataChange", g.getMessage());
                        //Toast.makeText(getContext(), g.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Log.d("Cam_eredmeny", readQRImage(imageBitmap));
        }
        }catch (Exception f){
            Log.d("Cam_OnAct: ", f.getMessage());
            Toast.makeText(getContext(), "Nem érvényes QR code. Vagy túl kicsi a képen a kód vagy életlen. Érintéssel tudsz fókuszt állítani", Toast.LENGTH_LONG).show();
        }
    }*/


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
        catch (Exception g){
            Log.d("Cam_Error", g.getMessage());
        }
        return contents;
    }
}