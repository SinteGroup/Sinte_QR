package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
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

import java.util.ArrayList;

import hu.sintegroup.sinte_qr.databinding.FragmentQRReadBinding;

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

    private FragmentQRReadBinding binding;


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
        binding.QRREadSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            Log.d("CamMPermissionExp", "Nincs camera2 engedély");
            return;
        }else {

        }
    }

    private void startCameraPreview() throws CameraAccessException {
        Handler cameraBackgroundHandler=new Handler();

        ArrayList<Integer> elerhetoCamerak=new ArrayList<>();

        CameraManager camManager= (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        String[] camIdList=camManager.getCameraIdList();
        for (String temp : camIdList){
            CameraCharacteristics tempChar=camManager.getCameraCharacteristics(temp);
            Integer tempCharDirection=tempChar.get(CameraCharacteristics.LENS_FACING);
            if(tempCharDirection != null && tempCharDirection == CameraCharacteristics.LENS_FACING_BACK){
                elerhetoCamerak.add(tempCharDirection);
            }
        }

        CameraDevice.StateCallback camStateCallback=new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                CameraCaptureSession.StateCallback captureSession= new CameraCaptureSession.StateCallback() {

                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        try {
                            CaptureRequest.Builder builder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                            builder.addTarget(binding.QRREadSurface.getHolder().getSurface());
                            
                        } catch (CameraAccessException e) {
                            Log.d("CamMExp", e.getMessage());
                        }
                    }


                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                    }
                };
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {

            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {

            }
        };

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
        catch (Exception g){
            Log.d("Cam_Error", g.getMessage());
        }
        return contents;
    }
}