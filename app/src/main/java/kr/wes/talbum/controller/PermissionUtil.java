/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package kr.wes.talbum.controller;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


public class PermissionUtil {
    private String[] targetPermissions = {};
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String TAG = "PermissionUtil_CUSTOM_TAG";
    public Activity activity;

    public PermissionUtil(Activity activity, String[] targetPermissions) {
        this.activity = activity;
        this.targetPermissions = targetPermissions;
    }

    public boolean isGrantedExternalStoragePermissions() {
        if (ActivityCompat.checkSelfPermission(activity, targetPermissions[0])
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, targetPermissions[1])
                != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG, "External storage permissions has NOT been granted. Requesting permissions.");
            return false;
        } else {
            Log.i(TAG,
                    "External storage permissions have already been granted. Get all images and setup GridView");
            return true;
        }
    }

    public void requestExternalStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                targetPermissions[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(activity, targetPermissions[1])) {

            Log.i(TAG,
                    "Displaying external storage permission rationale to provide additional context.");
        }
        ActivityCompat.requestPermissions(activity, targetPermissions, REQUEST_EXTERNAL_STORAGE);
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}
