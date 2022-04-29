package com.app.xdcpay.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.app.xdcpay.R;
import com.app.xdcpay.Utils.BaseActivity;
import com.app.xdcpay.Views.TextViewMedium;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScannerActivity extends BaseActivity {
    public static String ACTIVITY_NAME = "ACTIVITY_NAME";
    public static String ADDRESS = "WALLET_ADDRESS";
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private TextViewMedium barcodeText;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private String barcodeData, str_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }

    @Override
    public void getId() {
        surfaceView = findViewById(R.id.surfaceView);
        barcodeText = findViewById(R.id.barcodeText);
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        setData();
    }

    @Override
    public void setListener() {
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null)
            str_Activity = intent.getStringExtra(ACTIVITY_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    private void initialiseDetectorsAndSources() {
//        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannerActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
//                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                Log.d("barcode:", "1" + barcodeData);
                                saveAddress(barcodeData);
                            } else {
                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                Log.d("barcode:", "2" + barcodeData);
                                saveAddress(barcodeData);
                            }
                        }
                    });
                }
            }
        });
    }

    private void saveAddress(String address) {
        cameraSource.release();
        if (str_Activity.equals("HomeActivity")) {
            Intent intent = new Intent(ScannerActivity.this, HomeActivity.class);
            intent.putExtra(ADDRESS, address);
            startActivity(intent);
            finish();
        } else if (str_Activity.equals("SendActivity")) {
            Intent intent = new Intent(ScannerActivity.this, SendActivity.class);
            intent.putExtra(ADDRESS, address);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (str_Activity.equals("HomeActivity")) {
            Intent intent = new Intent(ScannerActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else if (str_Activity.equals("SendActivity")) {
            Intent intent = new Intent(ScannerActivity.this, SendActivity.class);
            startActivity(intent);
            finish();
        }
    }
}