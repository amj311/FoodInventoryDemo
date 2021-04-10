package com.example.foodinventorydemo.ui.scanner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodinventorydemo.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class ScannerFragment extends Fragment {
    private static final String ARG_CALLER = "param_caller";

    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private TextView barcodeText;
    private CameraSource cameraSource;
    private ImageView scanOverlay;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //This class provides methods to play DTMF tones
    private ToneGenerator toneGen1;
    private ScannerCaller caller;
    private boolean selfAllow = false;
    private boolean extAllow = true;

    public ScannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param caller Parameter 1.
     * @return A new instance of fragment ScannerFragment.
     */
    public static ScannerFragment newInstance(ScannerCaller caller) {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CALLER, caller);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            caller = (ScannerCaller) getArguments().getSerializable(ARG_CALLER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC,100);
        surfaceView = view.findViewById(R.id.surface_view);
        barcodeText = view.findViewById(R.id.barcodeText);
        scanOverlay = view.findViewById(R.id.scanOverlay);

        initializeDetectorsAndSources();

        return view;
    }

    private void initializeDetectorsAndSources() {
        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }

            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeText.post(new Runnable() {
                        @Override
                        public void run() {
                            if (canScan()) {
                                String barcodeData = barcodes.valueAt(0).displayValue;
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                reportData(barcodeData);
                                sleepScanner();
                            }
                        }
                    });
                }
            }

            private void reportData(String data) {
                if (caller != null) caller.onNewScanData(data);
            }
        });

        selfAllow = true;
    }

    private boolean canScan() {
        return extAllow && selfAllow;
    }

    private void updateAllowScan() {
        if (canScan()) scanOverlay.setAlpha(1F);
        else scanOverlay.setAlpha(0.5F);
    }

    public void disallowScan() {
        extAllow = false;
        updateAllowScan();
    }
    public void allowScan() {
        extAllow = true;
        updateAllowScan();
    }

    private void allowSelf(boolean allowance) {
        selfAllow = allowance;
        updateAllowScan();
    }

    public void sleepScanner(){
        new SleepScannerTask().execute();
    };


    private class SleepScannerTask extends AsyncTask {

        /**
         * @deprecated
         */
        @Override
        protected void onPreExecute() {
            allowSelf(false);
        }

        /**
         * @param objects
         * @deprecated
         */
        @Override
        protected Object doInBackground(Object[] objects) {
            SystemClock.sleep(1000);
            allowSelf(true);
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}