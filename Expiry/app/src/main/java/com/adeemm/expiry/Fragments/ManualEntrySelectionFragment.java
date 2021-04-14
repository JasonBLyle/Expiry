package com.adeemm.expiry.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.adeemm.expiry.R;


public class ManualEntrySelectionFragment extends DialogFragment {

    public CallbackResult callbackResult;

    public void setCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manual_entry_dialog, container, false);

        ((ImageButton) rootView.findViewById(R.id.close_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        rootView.findViewById(R.id.text_search_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClickHandler(0);
            }
        });

        rootView.findViewById(R.id.category_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClickHandler(1);
            }
        });

        rootView.findViewById(R.id.scratch_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClickHandler(2);
            }
        });

        return rootView;
    }

    private void optionClickHandler(int option) {
        if (callbackResult != null) {
            callbackResult.sendResult(option);
        }

        getActivity().finish();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public interface CallbackResult {
        void sendResult(int requestCode);
    }
}