package com.delacruz.ramon.fridgandroid.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.delacruz.ramon.fridgandroid.R;

/**
 * Created by Ramon on 6/14/16.
 */
public class SaveItemDialog extends DialogFragment {

    private EditText mEditText;


    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public SaveItemDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_item, container);
        mEditText = (EditText) view.findViewById(R.id.nameEditText);
        {
            // set this instance as callback for editor action
            mEditText.setOnEditorActionListener((TextView.OnEditorActionListener) this);
            mEditText.requestFocus();
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            getDialog().setTitle("Please enter username");

            return view;
        }
    }
}
