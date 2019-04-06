package ite.kmitl.project.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ite.kmitl.project.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class StaffLoginFragment extends Fragment {

    EditText edUsername;
    EditText edPassword;
    Button btnLogin;
    boolean haveUser = false;
    Button btnRegister;
    FirebaseDatabase database;
    DatabaseReference myRef;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    public interface FragmentListener {
        void onStaffLogin();
    }

    public StaffLoginFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffLoginFragment newInstance() {
        StaffLoginFragment fragment = new StaffLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_staff_login, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        editor.putBoolean("isStaffLogin",false);
        editor.commit();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        edUsername = (EditText) rootView.findViewById(R.id.edUsername);
        edPassword = (EditText) rootView.findViewById(R.id.edPassword);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(buttonClick);
        btnRegister.setOnClickListener(buttonClick);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    final View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(view == btnLogin) {
                if(edUsername.getText().toString().length() <= 0){
                    Toast.makeText(getContext(),"please input username",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edPassword.getText().toString().length() <= 0){
                    Toast.makeText(getContext(),"please input password",Toast.LENGTH_SHORT).show();
                    return;
                }
                checkLogin();
            }

            if(view == btnRegister) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer,StaffRegisterFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }

        }
    };

    private void checkLogin() {
        myRef.child("staff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (edUsername.getText().toString().equals(data.child("username").getValue() + "")
                            && edPassword.getText().toString().equals(data.child("password").getValue() + "")) {
                        haveUser = true;
                        if(data.child("key").getValue().toString().equals("1")){
                            editor.putBoolean("isStaffLogin",true);
                            editor.commit();
                            FragmentListener fragmentListener = (FragmentListener) getActivity();
                            fragmentListener.onStaffLogin();
                            getFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getContext(), "access is denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (!haveUser) {
                    Toast.makeText(getContext(), "username or password not true", Toast.LENGTH_SHORT).show();
                }
                haveUser=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
