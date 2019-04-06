package ite.kmitl.project.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ite.kmitl.project.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class SelectUserFragment extends Fragment {

    Button btnAdmin;
    Button btnStaff;

    public SelectUserFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static SelectUserFragment newInstance() {
        SelectUserFragment fragment = new SelectUserFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_select_user, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnAdmin = (Button) rootView.findViewById(R.id.btnAdmin);
        btnStaff = (Button) rootView.findViewById(R.id.btnStaff);
        btnAdmin.setOnClickListener(buttonClick);
        btnStaff.setOnClickListener(buttonClick);
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

            if(view == btnAdmin){
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.contentContainer,AdminLoginFragment.newInstance(),"AdminLoginFragment")
                        .commit();
            }
            if(view == btnStaff){
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                if(prefs.getBoolean("isStaffLogin",false)) {
                    StaffLoginFragment.FragmentListener fragmentListener = (StaffLoginFragment.FragmentListener) getActivity();
                    fragmentListener.onStaffLogin();
                } else {
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.contentContainer, StaffLoginFragment.newInstance(), "StaffLoginFragment")
                            .commit();
                }
            }
        }
    };

}
