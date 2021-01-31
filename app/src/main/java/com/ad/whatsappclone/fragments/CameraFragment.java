package com.ad.whatsappclone.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.ad.whatsappclone.databinding.FragmentCameraBinding;
import com.ad.whatsappclone.models.Constraints;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CameraFragment extends Fragment {

    FragmentCameraBinding binding;
    private static int lensFacing = CameraSelector.LENS_FACING_BACK;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Camera camera;

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment3
        binding = FragmentCameraBinding.inflate(inflater, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        TabLayout tabLayout =((AppCompatActivity) getActivity()).findViewById(R.id.mainTabLayout);
//        tabLayout.setVisibility(View.GONE);

        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(getActivity(), Constraints.REQUIRED_PERMISSION, Constraints.PERMISSION_REQUEST_CODE);
        }

        return binding.getRoot();
    }

    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    cameraProvider.unbindAll();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }

        final ImageCapture imageCapture = builder
                .setTargetRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation())
                .build();
        preview.setSurfaceProvider(binding.cameraFragmentCameraView.createSurfaceProvider());
        camera = cameraProvider.bindToLifecycle((LifecycleOwner) getActivity(), cameraSelector, preview, imageAnalysis, imageCapture);


//        captureBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SaveImageFile imageFile = new SaveImageFile(MainActivity.this);
//
//                SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
//                File file = new File(imageFile.getBatchDirectoryName(), imageFile.getAppName() + mDateFormat.format(new Date()) + ".jpg");
//
//                ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
//                imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback() {
//                    @Override
//                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(MainActivity.this, ImagePreviewActivity.class);
//                                intent.putExtra("imageUrl", file.getPath());
//                                startActivity(intent);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(@NonNull ImageCaptureException error) {
//                        error.printStackTrace();
//                    }
//                });
//            }
//        });
    }

    private boolean allPermissionsGranted() {
        for (String permission : Constraints.REQUIRED_PERMISSION) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}