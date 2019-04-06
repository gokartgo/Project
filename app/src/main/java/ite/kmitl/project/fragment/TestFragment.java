package ite.kmitl.project.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.Map;

import ite.kmitl.project.R;
import ite.kmitl.project.activity.MainActivity;

import static android.content.ContentValues.TAG;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class TestFragment extends Fragment {

    Button btnNotification;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public TestFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //btnNotification = rootView.findViewById(R.id.btnNotification);
        //btnNotification.setOnClickListener(buttonClick);
        getValueDatabase();
    }

    private void getValueDatabase() {
        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        // Read from the database
        myRef.child("ultrasonic").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                int[] value = new int[9];
                for(int i = 1 ; i<9 ; i++ ){
                    value[i] = Integer.parseInt(String.valueOf(map.get("sensor" + (i) ) ) ); // sensor + i is name key in firebase
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Read from the database
        myRef.child("loadCell").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                int value = Integer.parseInt(String.valueOf(map.get("sensor1") ) ); // sensor1 is name key in firebase loadCell
                Log.d("value: 0 ",value+"");
                if(value == 0){
                    notification();
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

    final View.OnClickListener buttonClick = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            if(view == btnNotification){
                Toast.makeText(getContext(),"button shelve click",Toast.LENGTH_SHORT).show();
                notification();
            }
        }
    };

    NotificationManager mManager;
    NotificationCompat.Builder nb;

    /**
     * Create and push the notification
     */
    public void notification()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
        nb = getChannelNotification("test notification title","test notification message");
        getNotificationManager().notify(1,nb.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("test","channel name",NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(channel);
    }

    public NotificationManager getNotificationManager() {
        if(mManager == null){
            mManager = (NotificationManager) Contextor.getInstance().getContext()
                    .getSystemService(Contextor.getInstance().getContext().NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title,String message) {
        Intent activityIntent = new Intent(getContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getContext(),
                0,activityIntent,0);
        return new NotificationCompat.Builder(getContext(), "test")
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_3d);
    }
}
