package hu.sintegroup.sinte_qr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TooManyListenersException;
import java.util.concurrent.Executor;

import hu.sintegroup.sinte_qr.databinding.FragmentFirstBinding;
import hu.sintegroup.sinte_qr.databinding.FragmentQRReadBinding;

public class QRReadFragment extends Fragment {

    private FragmentQRReadBinding bindingNavigation;

    private ImageReader QR_image_read;

    private Bundle qrAdatok;
    private String meresSzama;
    private String gyartmany_azonosito;

    public static String firebasePath = "";

    CameraCaptureSession.StateCallback sessionCallBack=null;

    public QRReadFragment() {
        // Required empty public constructor
    }

    public static QRReadFragment newInstance(String param1, String param2) {
        QRReadFragment fragment = new QRReadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindingNavigation = DataBindingUtil.inflate(inflater, R.layout.fragment_q_r_read, container, false);
        return bindingNavigation.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
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
        };
        bindingNavigation.QRREadSurface.getHolder().addCallback(surfaceCallback);
    }

    public void onStart() {
        super.onStart();
    }

    private void startCameraPreview(int width, int height) {

        try {
            Handler cameraBackgroundHandler = new Handler();
            CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);

            CameraDevice.StateCallback cameraCallback = new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    //A barcodeScenner dob hibát fordításkor
                    BarcodeDetector barcodeDetect=new BarcodeDetector.Builder(getContext()).setBarcodeFormats(Barcode.QR_CODE).build();

                    if(barcodeDetect.isOperational()){
                        Log.d("Barcode", "Byrcode mukodik");
                    }

                    QR_image_read=ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);

                    QR_image_read.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        @SuppressLint("ResourceType")
                        @Override
                        public void onImageAvailable(ImageReader imageReader) {
                            Image qrImage=imageReader.acquireNextImage();

                            Log.d("BarcodeQrimage", qrImage.toString());

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                ByteBuffer buffer= Arrays.stream(qrImage.getPlanes()).findFirst().get().getBuffer();
                                byte[] bytes=new byte[buffer.remaining()];
                                buffer.get(bytes);

                                Bitmap qrBitmap=BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                                Frame builder=new Frame.Builder().setBitmap(qrBitmap).build();
                                SparseArray<Barcode> barcodeResult=barcodeDetect.detect(builder);

                                Log.d("BarcodeMegy", String.valueOf(barcodeResult.size()));

                                if(barcodeResult.size() > 0){
                                    try {
                                        for (int i = 0; i < barcodeResult.size(); i++) {
                                            Barcode barcodeTemp = barcodeResult.valueAt(i);
                                            String Value = barcodeTemp.displayValue;
                                            Log.d("BarcodeGet", Value);
                                            if(!Value.contains("n=")){
                                                meresSzama=Value;
                                            }else {

                                                String[] tempValues = Value.split("n=");

                                                if (tempValues[1].contains("&t=")) {
                                                    String[] tempValues_t = tempValues[1].split("&t="); //Ha van gyártmányazonosító, akkor azt is leválaszja, egyébként csak felmérés számot ad át.
                                                    meresSzama = tempValues_t[0];
                                                    gyartmany_azonosito = tempValues_t[1];
                                                } else {
                                                    meresSzama = tempValues[1];
                                                }
                                            }
                                            qrAdatok = new Bundle();
                                            qrAdatok.putString("QrMeresszama", meresSzama);
                                            NavHostFragment.findNavController(QRReadFragment.this).navigate(R.id.action_QRReadFragment_to_DocMakeFragment2, qrAdatok);

                                            String vaneIlyenQrUrl="https://www.weblapp.hu/Proba.php?method=checkValidQR&QrErteke="+meresSzama;
                                            RequestQueue vaneIlyenQrQueue= Volley.newRequestQueue(getContext());
                                            StringRequest vaneIlyenQrRequest=new StringRequest(Request.Method.GET, vaneIlyenQrUrl, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("vaneIlyenQrQueue", String.valueOf(response.contains(meresSzama)));
                                                    Log.d("vaneIlyenQrResponse", response);

                                                    if(response.contains("VanilyenVazze")) {

                                                    }else{
                                                        NavHostFragment.findNavController(QRReadFragment.this).navigate(R.id.action_QRReadFragment_to_AdatfelvetelFragment, qrAdatok);
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Log.d("vaneIlyenQrQueue", error.getMessage());
                                                }
                                            });
                                            vaneIlyenQrQueue.add(vaneIlyenQrRequest);
                                        }

                                    }catch (Exception f){
                                        Log.d("Barcode_Err", f.getMessage());
                                    }

                                }
                            }
                            qrImage.close();
                        }

                    }, cameraBackgroundHandler);


                    sessionCallBack = new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            try {
                                CaptureRequest.Builder Builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                                Builder.addTarget(bindingNavigation.QRREadSurface.getHolder().getSurface());
                                Builder.addTarget(QR_image_read.getSurface());
                                CaptureRequest request=Builder.build();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                    cameraCaptureSession.setSingleRepeatingRequest(request, new Executor() {
                                        @Override
                                        public void execute(Runnable runnable) {
                                            Log.d("Exekjútor", "Az Exekjútor exekjútol");


                                        }
                                    }, new CameraCaptureSession.CaptureCallback() {
                                        @Override
                                        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                                            super.onCaptureStarted(session, request, timestamp, frameNumber);


                                        }
                                    });
                                }
                            } catch (CameraAccessException e) {
                                Log.d("CaptureSession", e.getMessage());
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    };
                    try {
                        cameraDevice.createCaptureSession(Arrays.asList(bindingNavigation.QRREadSurface.getHolder().getSurface(), QR_image_read.getSurface()), sessionCallBack, cameraBackgroundHandler);
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