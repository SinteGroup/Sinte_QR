package hu.sintegroup.sinte_qr;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.Arrays;

public class myCameraHelper {

    public myCameraHelper(){

    }

    protected void myCameraStart(Context context, View view){

        CameraManager camMan = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            Log.d("CamMan", camMan.getCameraIdList()[1]);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            camMan.openCamera(String.valueOf(1), cameraStatecallback, cameraStateBackgroundHandler);
        } catch (CameraAccessException e) {
            Log.d("CamManEx", e.getMessage());
        }catch (Exception Ex){
            Log.d("CamManExp", Ex.getMessage());
        }

        SurfaceView cameraTexture= (SurfaceView) view.findViewById(R.id.QRREadSurface);
        try {
            CameraCharacteristics myCameraCharater=camMan.getCameraCharacteristics(String.valueOf(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP));
            //Figyelni, mert itt kellene egy getOutSizes(), de Javaban egyenlőre nem találom.

        } catch (CameraAccessException e) {
            Log.d("CamMapChar", e.getMessage());
        }
    }

    private CameraDevice.StateCallback cameraStatecallback =new CameraDevice.StateCallback(){

        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Log.d("CamManOpen", "CamOpened");
        }

        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Log.d("CamManDisconnect", "CamDisconnct");
        }

        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            Log.d("CamError", "CamError");
        }
    };

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

}
