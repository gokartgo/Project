package ite.kmitl.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import ite.kmitl.project.R;

import static android.content.ContentValues.TAG;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ShelvesFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    int[] value = new int[9];
    TextView tvSensor[] = new TextView[9];
    Button btnNotification;

    public ShelvesFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ShelvesFragment newInstance() {
        ShelvesFragment fragment = new ShelvesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_shelve, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        tvSensor[0] = rootView.findViewById(R.id.tvSensor1);
        tvSensor[1] = rootView.findViewById(R.id.tvSensor2);
        tvSensor[2] = rootView.findViewById(R.id.tvSensor3);
        tvSensor[3] = rootView.findViewById(R.id.tvSensor4);
        tvSensor[4] = rootView.findViewById(R.id.tvSensor5);
        tvSensor[5] = rootView.findViewById(R.id.tvSensor6);
        tvSensor[6] = rootView.findViewById(R.id.tvSensor7);
        tvSensor[7] = rootView.findViewById(R.id.tvSensor8);
        tvSensor[8] = rootView.findViewById(R.id.tvSensor9);
        getValueDatabase();

    }

    private void getValueDatabase() {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int index = 0;
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    //Loop 1 to go through all the child nodes of users
                    if(!uniqueKeySnapshot.getKey().equals("product")) {
                        Log.d("firebase-test", uniqueKeySnapshot.getKey());

                        for (DataSnapshot sensorSnapshot : uniqueKeySnapshot.getChildren()) {
                            Log.d("test firebase", sensorSnapshot.getKey() + ":" + sensorSnapshot.getValue() + " " + index);
                            if (Integer.parseInt(sensorSnapshot.getValue() + "") == 0) {
                                tvSensor[index].setBackgroundColor(0xffff0000); // ARGB
                                tvSensor[index].setTextColor(0xffffffff);
                            } else {
                                tvSensor[index].setBackgroundColor(0xffffffff); // ARGB
                                tvSensor[index].setTextColor(0xff000000);
                            }
                            tvSensor[index].setText(sensorSnapshot.getValue() + "");
                            index++;
                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

}
