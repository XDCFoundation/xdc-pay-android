package com.app.xdcpay.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatImageView;

import com.app.xdcpay.R;

public class AppUtility {
    private static Dialog progress;

    public static void hideDialog() {
        try {
            if (progress != null && progress.isShowing())
                progress.dismiss();
        } catch (Exception e) {
//            Log.d(AppConstants.TAG, "hideDialog " + e.getLocalizedMessage());
        }
    }

    public static void showAlert(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void showDialog(Context context) {
        if (context == null)
            return;
        if (progress != null && progress.isShowing())
            return;
        progress = new Dialog(context);
        progress.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progress.setContentView(R.layout.dialog_progress);
        AppCompatImageView ivLoader = progress.findViewById(R.id.ivLoader);
//        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(ivLoader);
//        Glide.with(context).load(R.raw.loader).into(imageViewTarget);
        progress.setCancelable(false);
        progress.show();
    }
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}