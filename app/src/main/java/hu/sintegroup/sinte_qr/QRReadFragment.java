package hu.sintegroup.sinte_qr;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.common.primitives.Bytes;
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

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;

import hu.sintegroup.sinte_qr.databinding.FragmentQRReadBinding;

public class QRReadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentQRReadBinding binding;
    ImageReader QR_image_read;

    public static String firebasePath = "";

    private CameraDevice camera=null;
    CameraCaptureSession.StateCallback sessionCallBack=null;

    public QRReadFragment() {
        // Required empty public constructor
    }

    public static QRReadFragment newInstance(String param1, String param2) {
        QRReadFragment fragment = new QRReadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q_r_read, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_q_r_read);

        binding.QRREadSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
                startCameraPreview(width, height);
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        /*if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("CamMPermissionExp", "Nincs camera2 engedély");
            return;
        } else {
            //startCameraPreview();
        }*/
    }

    private void startCameraPreview(int width, int height) {
        try {
            Handler cameraBackgroundHandler = new Handler();
            CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);

            CameraDevice.StateCallback cameraCallback = new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    //A barcodeScenner dob hibát fordításkor
                    BarcodeDetector barcodeDetect=new BarcodeDetector.Builder().setBarcodeFormats(Barcode.QR_CODE).build();

                    if(!barcodeDetect.isOperational()){
                        Log.d("Barcode", "Byrcode mukodik");
                    }

                    ImageReader imageReader=ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);


                    sessionCallBack = new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            try {
                                CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                builder.addTarget(binding.QRREadSurface.getHolder().getSurface());
                                cameraCaptureSession.setRepeatingRequest(builder.build(), null, null);
                            } catch (CameraAccessException e) {
                                Log.d("CaptureSession", e.getMessage());
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    };
                    try {
                        cameraDevice.createCaptureSession(Arrays.asList(binding.QRREadSurface.getHolder().getSurface()), sessionCallBack, cameraBackgroundHandler);
                    } catch (CameraAccessException e) {
                        Log.d("CapTureSessionEx", e.getMessage());
                    }
                }

                @Override
                public void onClosed(@NonNull CameraDevice cameraDevice) {
                    Log.d("CamMonClosed", "Camre closed");
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                    Log.d("CamMonDiconnected", "Camera is disconnected");
                }

                @Override
                public void onError(@NonNull CameraDevice cameraDevice, int i) {
                    Log.d("CamMonError", "Send camera eror message");
                }
            };

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            cameraManager.openCamera("0", cameraCallback, cameraBackgroundHandler);

        }catch (CameraAccessException camEx){
            Log.d("CamMex", camEx.getMessage());
        }
    }
}