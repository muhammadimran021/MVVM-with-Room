package com.example.roomlivedataapp.screens.workManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.bumptech.glide.Glide;
import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.Utils.Constants;
import com.example.roomlivedataapp.databinding.ActivityWorkManagerBinding;

import java.util.Arrays;
import java.util.List;

public class WorkManager extends AppCompatActivity implements WorkManagerListeners {
    private ActivityWorkManagerBinding binding;
    private WorkManageViewModel viewModel;

    //Select Image
    private static final String TAG = "SelectImageActivity";
    private static final int REQUEST_CODE_IMAGE = 100;
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;

    // Permission Array
    private static final List<String> sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );

    private int mPermissionRequestCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager);
        viewModel = ViewModelProviders.of(this).get(WorkManageViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setModel(viewModel);
        binding.setClick(this);


        blurWorkObserver();
    }

    private void blurWorkObserver() {
        viewModel.getOutputWorkInfo().observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfo) {
                // Note that these next few lines grab a single WorkInfo if it exists
                // This code could be in a Transformation in the ViewModel; they are included here
                // so that the entire process of displaying a WorkInfo is in one location.

                // If there are no matching work info, do nothing
                if (workInfo == null || workInfo.isEmpty()) {
                    return;
                }
                // We only care about the one output status.
                // Every continuation has only one worker tagged TAG_OUTPUT
                WorkInfo workInfos = workInfo.get(0);

                boolean finished = workInfos.getState().isFinished();
                if (!finished) {
                    showWorkInProgress();
                } else {
                    showWorkFinished();
                    // Normally this processing, which is not directly related to drawing views on
                    // screen would be in the ViewModel. For simplicity we are keeping it here.
                    Data outputData = workInfos.getOutputData();

                    String outputImageUri =
                            outputData.getString(Constants.KEY_IMAGE_URI);

                    // If there is an output file show "See File" button
                    if (!TextUtils.isEmpty(outputImageUri)) {
                        viewModel.setOutputUri(outputImageUri);
                        viewModel.isSeeFileVisible.setValue(true);
                    }
                }
            }
        });
    }

    private void showWorkFinished() {
        viewModel.isProgressVisible.setValue(false);
        viewModel.isCancelVisible.setValue(false);
        viewModel.isGoVisible.setValue(true);
    }

    private void showWorkInProgress() {
        viewModel.isProgressVisible.setValue(true);
        viewModel.isCancelVisible.setValue(true);
        viewModel.isGoVisible.setValue(false);
        viewModel.isSeeFileVisible.setValue(false);
    }

    private void setUriInViewModel(String imageUri) {
        viewModel.setImageUri(imageUri);
        if (viewModel.getImageUri() != null) {
            Glide.with(this).load(viewModel.getImageUri()).into(binding.ImageView);
        }
    }


    /**
     * Request permissions twice - if the user denies twice then show a toast about how to update
     * the permission for storage. Also disable the button if we don't have access to pictures on
     * the device.
     */
    private void requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (mPermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                mPermissionRequestCount += 1;
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toArray(new String[0]),
                        REQUEST_CODE_PERMISSIONS);
            } else {
                Toast.makeText(this, R.string.set_permissions_in_settings,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkAllPermissions() {
        boolean hasPermissions = true;
        for (String permission : sPermissions) {
            hasPermissions &=
                    ContextCompat.checkSelfPermission(
                            this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    /**
     * Permission Checking
     **/

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary(); // no-op if permissions are granted already.
        }
    }

    /**
     * Image Selection
     **/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMAGE) {
                handleImageRequestResult(data);
            } else {
                Log.d(TAG, "Unknown request code.");
            }
        } else {
            Log.e(TAG, String.format("Unexpected Result code %s", resultCode));
        }
    }

    private void handleImageRequestResult(Intent data) {
        Uri imageUri = null;
        if (data.getClipData() != null) {
            imageUri = data.getClipData().getItemAt(0).getUri();
        } else if (data.getData() != null) {
            imageUri = data.getData();
        }

        if (imageUri == null) {
            Log.e(TAG, "Invalid input image Uri.");
            return;
        }
        setUriInViewModel(imageUri.toString());
        Glide.with(this).load(imageUri).into(binding.ImageView);
    }

    @Override
    public void cancelWorkClick() {

    }

    @Override
    public void goClick() {
        viewModel.applyBlur(getBlurLevel());
    }

    @Override
    public void seeFilesClick() {
        Uri currentUri = viewModel.getOutputUri();
        if (currentUri != null) {
            Intent actionView = new Intent(Intent.ACTION_VIEW, currentUri);
            if (actionView.resolveActivity(getPackageManager()) != null) {
                startActivity(actionView);
            }
        }
    }

    @Override
    public void blurAmountRadioCick() {

    }

    @Override
    public void imageClick() {
        Intent chooseIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE);
    }

    /**
     * Get the blur level from the radio button as an integer
     *
     * @return Integer representing the amount of times to blur the image
     */
    private int getBlurLevel() {
        switch (binding.radioBlurGroup.getCheckedRadioButtonId()) {
            case R.id.radio_blur_lv_1:
                return 1;
            case R.id.radio_blur_lv_2:
                return 2;
            case R.id.radio_blur_lv_3:
                return 3;
        }
        return 1;
    }
}
