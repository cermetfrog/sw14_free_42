package com.mobileapplications.emporium.camera;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.mobileapplications.emporium.R;

public class ImageViewExifInfoDialog extends DialogFragment {
    
    
    public static ImageViewExifInfoDialog newInstance(int title, String msg) {
        ImageViewExifInfoDialog frag = new ImageViewExifInfoDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putString("message", msg);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String msg = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(R.string.close, null)
                .setMessage(msg)
                .create();
    }
}
