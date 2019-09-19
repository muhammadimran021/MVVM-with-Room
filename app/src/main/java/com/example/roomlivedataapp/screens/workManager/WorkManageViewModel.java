package com.example.roomlivedataapp.screens.workManager;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.roomlivedataapp.worker.BlurWorker;
import com.example.roomlivedataapp.worker.CleanupWorker;
import com.example.roomlivedataapp.worker.SaveImageToFileWorker;

import java.util.List;

import static com.example.roomlivedataapp.Utils.Constants.IMAGE_MANIPULATION_WORK_NAME;
import static com.example.roomlivedataapp.Utils.Constants.KEY_IMAGE_URI;
import static com.example.roomlivedataapp.Utils.Constants.TAG_OUTPUT;

public class WorkManageViewModel extends AndroidViewModel {
    private WorkManager mWorkManager;
    private Uri mImageUri;
    private Uri mOutputUri;
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
    //ProgressBar
    public MutableLiveData<Boolean> isProgressVisible = new MutableLiveData<>();
    public LiveData<Boolean> isVisible = isProgressVisible;
    //GoButton
    public MutableLiveData<Boolean> isGoVisible = new MutableLiveData<>();
    public LiveData<Boolean> isGo = isGoVisible;
    //CancelButton
    public MutableLiveData<Boolean> isCancelVisible = new MutableLiveData<>();
    public LiveData<Boolean> isCancel = isCancelVisible;
    //CancelButton
    public MutableLiveData<Boolean> isSeeFileVisible = new MutableLiveData<>();
    public LiveData<Boolean> isSeeFile = isSeeFileVisible;

    public WorkManageViewModel(@NonNull Application application) {
        super(application);
        mWorkManager = WorkManager.getInstance(application);
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_OUTPUT);
        setVisibilities();
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     *
     * @param blurLevel The amount to blur the image
     */
    void applyBlur(int blurLevel) {

        // Add WorkRequest to Cleanup temporary images
        WorkContinuation continuation = mWorkManager
                .beginUniqueWork(IMAGE_MANIPULATION_WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(CleanupWorker.class));

        // Add WorkRequests to blur the image the number of times requested
        for (int i = 0; i < blurLevel; i++) {
            OneTimeWorkRequest.Builder blurBuilder =
                    new OneTimeWorkRequest.Builder(BlurWorker.class);

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri());
            }

            continuation = continuation.then(blurBuilder.build());
        }

        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .build();

        // Add WorkRequest to save the image to the filesystem
        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)
                .build();
        continuation = continuation.then(save);

        // Actually start the work
        continuation.enqueue();

    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     *
     * @return Data which contains the Image Uri as a String
     */
    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (mImageUri != null) {
            builder.putString(KEY_IMAGE_URI, mImageUri.toString());
        }
        return builder.build();
    }

    private void setVisibilities() {
        isProgressVisible.setValue(false);
        isCancelVisible.setValue(false);
        isSeeFileVisible.setValue(false);
        isGoVisible.setValue(true);
    }

    void setImageUri(String uri) {
        mImageUri = uriOrNull(uri);
    }

    Uri getImageUri() {
        return mImageUri;
    }

    private Uri uriOrNull(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            return Uri.parse(uri);
        }
        return null;
    }

    LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }

    public void setOutputUri(String outputImageUri) {

    }
}
