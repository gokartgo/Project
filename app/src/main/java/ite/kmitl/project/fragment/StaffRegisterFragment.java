package ite.kmitl.project.fragment;

import android.os.Bundle;
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
public class StaffRegisterFragment extends Fragment {

    EditText edName, edSurname, edAge, edUsername, edPassword, edRePassword;
    Button btnRegisterOk;
    FirebaseDatabase database;
    DatabaseReference myRef,myRefPush;

    public StaffRegisterFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static StaffRegisterFragment newInstance() {
        StaffRegisterFragment fragment = new StaffRegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_staff_register, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRefPush = database.getReference().child("staff").push();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        edName = (EditText) rootView.findViewById(R.id.edName);
        edSurname = (EditText) rootView.findViewById(R.id.edSurname);
        edAge = (EditText) rootView.findViewById(R.id.edAge);
        edUsername = (EditText) rootView.findViewById(R.id.edUsername);
        edPassword = (EditText) rootView.findViewById(R.id.edPassword);
        edRePassword = (EditText) rootView.findViewById(R.id.edRePassword);
        btnRegisterOk = (Button) rootView.findViewById(R.id.btnRegisterOk);
        btnRegisterOk.setOnClickListener(buttonClickRegister);
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

    /***************
     * Listener Zone
     ***************/

    final View.OnClickListener buttonClickRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == btnRegisterOk) {
                // button to register
                checkRegister();
            }
        }
    };

    private void checkRegister() {
        if(edName.getText().toString().length() <= 0){
            Toast.makeText(getContext(), "please input name", Toast.LENGTH_SHORT).show();
            return;
        } else if(edSurname.getText().toString().length() <= 0){
            Toast.makeText(getContext(), "please input surname", Toast.LENGTH_SHORT).show();
            return;
        } else if(edAge.getText().toString().length() <= 0){
            Toast.makeText(getContext(), "please input age", Toast.LENGTH_SHORT).show();
            return;
        } else if(edUsername.getText().toString().length() <= 0) {
            Toast.makeText(getContext(), "please input username", Toast.LENGTH_SHORT).show();
            return;
        } else if(edPassword.getText().toString().length() <= 0) {
            Toast.makeText(getContext(), "please input password", Toast.LENGTH_SHORT).show();
            return;
        } else if(!edPassword.getText().toString().equals(edRePassword.getText().toString())) {
            Toast.makeText(getContext(), "password and repassword not same", Toast.LENGTH_SHORT).show();
            return;
        }

        myRef.child("staff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    String dataCheck = data.child("username").getValue()+"";
                    if( dataCheck.equals(edUsername.getText().toString()) ){
                        Toast.makeText(getContext(), "username already exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                myRefPush.child("name").setValue(edName.getText().toString());
                myRefPush.child("surname").setValue(edSurname.getText().toString());
                myRefPush.child("age").setValue(edAge.getText().toString());
                myRefPush.child("username").setValue(edUsername.getText().toString());
                myRefPush.child("password").setValue(edPassword.getText().toString());
                myRefPush.child("key").setValue("0");
                Toast.makeText(getContext(), "register finish", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
