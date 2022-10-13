package hu.sintegroup.sinte_qr;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FragmentQRReadBinding binding;
    ImageReader QR_image_read;

    public static String firebasePath = "";

    public QRReadFragment() {
        // Required empty public constructor
    }

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
        return inflater.inflate(R.layout.fragment_q_r_read, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_q_r_read);

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
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("CamMPermissionExp", "Nincs camera2 engedély");
            return;
        } else {
            try {
                startCameraPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startCameraPreview() throws CameraAccessException {
        Handler cameraBackgroundHandler = new Handler();

        ArrayList<Integer> elerhetoCamerak = new ArrayList<>();

        CameraManager camManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        String[] camIdList = camManager.getCameraIdList();
        for (String temp : camIdList) {
            CameraCharacteristics tempChar = camManager.getCameraCharacteristics(temp);
            Integer tempCharDirection = tempChar.get(CameraCharacteristics.LENS_FACING);
            if (tempCharDirection != null && tempCharDirection == CameraCharacteristics.LENS_FACING_BACK) {
                elerhetoCamerak.add(tempCharDirection);
            }
        }



        CameraDevice.StateCallback camStateCallback = new CameraDevice.StateCallback() {

                public void onOpened (@NonNull CameraDevice cameraDevice){

                BarcodeDetector barcodeDetektor = new BarcodeDetector.Builder(getContext()).setBarcodeFormats(Barcode.QR_CODE).build();

                if (!barcodeDetektor.isOperational()) {
                    Log.d("CamMBarcode", "Barcode mukodik");
                }

                    QR_image_read = ImageReader.newInstance(binding.QRREadSurface.getWidth(), binding.QRREadSurface.getHeight(), ImageFormat.JPEG, 1);

                    QR_image_read.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        @Override
                        public void onImageAvailable(ImageReader imageReader) {
                            Image qrImage = imageReader.acquireNextImage();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                ByteBuffer buffer = Arrays.stream(qrImage.getPlanes()).findFirst().get().getBuffer();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);

                                Log.d("CamMBuffer", String.valueOf(buffer.capacity()));
                                Log.d("CamMBytes", String.valueOf(bytes.length));

                                Bitmap qrBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
                                Frame qrBuilder = new Frame.Builder().setBitmap(qrBitmap).build();
                                SparseArray<Barcode> barcodeEResults = barcodeDetektor.detect(qrBuilder);
                                Log.d("CamMBarcodeban", "Barcodeban");

                                if (barcodeEResults.size() > 0) {
                                    Log.d("CamMBarcodeTMeret", String.valueOf(barcodeEResults.size()));
                                    Log.d("CamMBarcodeTartalom", String.valueOf(barcodeEResults.get(1)));
                                }

                                qrImage.close();

                            }
                        }
                    }, cameraBackgroundHandler);

                CameraCaptureSession.StateCallback captureSession = new CameraCaptureSession.StateCallback() {
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {

                        CaptureRequest.Builder builder = null;
                        try {
                            builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        builder.addTarget(binding.QRREadSurface.getHolder().getSurface());
                        builder.addTarget(QR_image_read.getSurface());

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                try {
                                    cameraCaptureSession.setRepeatingRequest(builder.build(), null, null);
                                } catch (CameraAccessException e) {
                                    Log.d("CamM", e.getMessage());
                                }
                            }

                    }

                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        Log.d("CamMConfFail", "onConfigureFailed");

                    }
                };

                try {
                    cameraDevice.createCaptureSession(Arrays.asList(binding.QRREadSurface.getHolder().getSurface(), QR_image_read.getSurface()), captureSession, cameraBackgroundHandler);
                } catch (CameraAccessException e) {
                    Log.d("CamAcEx", e.getMessage());
                }

            }

                @Override
                public void onDisconnected (@NonNull CameraDevice cameraDevice){
                Log.d("CamMDisConEx", "Camera discinnected");
            }

                @Override
                public void onError (@NonNull CameraDevice cameraDevice,int i){
                Log.d("CamMErConEx", "Camera Error");
            }
        };

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("CamManExp", "Nincs kamera engedély");
            return;
        }
        camManager.openCamera("0", camStateCallback, cameraBackgroundHandler);

    }
}