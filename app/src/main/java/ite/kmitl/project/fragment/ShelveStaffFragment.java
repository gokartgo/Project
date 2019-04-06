package ite.kmitl.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ite.kmitl.project.R;
import ite.kmitl.project.adapter.ProductAdapter;
import ite.kmitl.project.dao.ProductItemDao;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ShelveStaffFragment extends Fragment {

    private List<ProductItemDao> response_data;
    private ProductAdapter dataAdapter;
    private RecyclerView mRecyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public ShelveStaffFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ShelveStaffFragment newInstance() {
        ShelveStaffFragment fragment = new ShelveStaffFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_shelve_staff, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("product");
        response_data = new ArrayList<>();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        dataAdapter = new ProductAdapter(response_data);
        mRecyclerView.setAdapter(dataAdapter);
        bindingData();
    }

    private void bindingData() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                response_data.add(dataSnapshot.getValue(ProductItemDao.class));
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                response_data.set(Integer.parseInt(dataSnapshot.child("index").getValue()+""),dataSnapshot.getValue(ProductItemDao.class));
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("firebase","remove");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("firebase","move");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("firebase","cancel");
            }
        });
    }

    private int getIemIndex(ProductItemDao dataModel){
        int index = -1;
        for(int i =0; i < response_data.size(); i++){
            if(response_data.get(i).getName().equals(dataModel.getName())){
                index = i;
                break;
            }
        }
        return  index;
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
