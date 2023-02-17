package com.ad.whatsappclone.ui.home.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.ad.whatsappclone.databinding.FragmentCameraBinding
import com.ad.whatsappclone.models.Constraints
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private var binding: com.ad.whatsappclone.databinding.FragmentCameraBinding? = null
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private var camera: Camera? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment3
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        if (allPermissionsGranted()) {
            startCamera() //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constraints.REQUIRED_PERMISSION,
                Constraints.PERMISSION_REQUEST_CODE
            )
        }
        return binding!!.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(
            requireContext()
        )
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()
                bindPreview(cameraProvider)
            } catch (e: ExecutionException) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            } catch (_: InterruptedException) {
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
        val imageAnalysis = ImageAnalysis.Builder()
            .build()
        val builder = ImageCapture.Builder()

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
//        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
//        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
//            // Enable the extension if available.
//            hdrImageCaptureExtender.enableExtension(cameraSelector);
//        }
        val imageCapture = builder
            .setTargetRotation(requireActivity().windowManager.defaultDisplay.rotation)
            .build()
        //        preview.setSurfaceProvider(binding.cameraFragmentCameraView.createSurfaceProvider());
        camera = cameraProvider.bindToLifecycle(
            (this as LifecycleOwner),
            cameraSelector,
            preview,
            imageAnalysis,
            imageCapture
        )


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            try {

//                if (data != null) {
//                    Uri imageUri = data.getData();
//                    Intent intent = new Intent(getActivity(), ity.class);
//                    intent.putExtra("imageUri", imageUri.toString());
//                    startActivity(intent);
//                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Snackbar.make(binding!!.root, "You haven't picked Image", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in Constraints.REQUIRED_PERMISSION) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constraints.PERMISSION_REQUEST_CODE) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                displayNeverAskAgainDialog()
                //                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
//                this.finish();
            }
        }
    }

    fun displayNeverAskAgainDialog() {
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setMessage(
            """
    We need to capture & save Image for performing necessary task. Please permit the permission through Settings screen.
    
    Select Permissions -> Enable permission
    """.trimIndent()
        )
        builder.setCancelable(false)
        builder.setPositiveButton("Permit Manually") { dialog, which ->
            dialog.dismiss()
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> requireActivity().finish() }
        builder.show()
    }

    companion object {
        private const val lensFacing = CameraSelector.LENS_FACING_BACK
        private const val IMAGE_REQUEST = 100
    }
}