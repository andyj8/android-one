package com.andy.auth;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private OnLoginListener mOnLoginListener;

    private EditText mUsernameEdit;
    private EditText mPasswordEdit;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameEdit = (EditText) view.findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) view.findViewById(R.id.password_edit);

        Button submitButton = (Button) view.findViewById(R.id.submit_login_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mOnLoginListener.handleLogin(
                    mUsernameEdit.getText().toString(),
                    mPasswordEdit.getText().toString()
                );
            }
        });

        return view;
    }

    public interface OnLoginListener {
        public void handleLogin(String username, String password);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnLoginListener = (OnLoginListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
