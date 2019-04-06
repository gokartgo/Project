package ite.kmitl.project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ite.kmitl.project.R;
import ite.kmitl.project.dao.StaffDao;
import ite.kmitl.project.holder.StaffViewHolder;

public class StaffManageAdapter extends RecyclerView.Adapter<StaffViewHolder> {


    private List<StaffDao> staffDataModelList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public StaffManageAdapter(List<StaffDao> staffDataModelList) {
        this.staffDataModelList = staffDataModelList;
    }

    @Override
    public StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StaffViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_staff,parent,false));
    }

    @Override
    public void onBindViewHolder(final StaffViewHolder holder, int position) {
        final StaffDao staffDao = staffDataModelList.get(position);
        holder.tvName.setText(staffDao.getName());
        holder.tvUsername.setText(staffDao.getUsername());
        holder.tvSurname.setText(staffDao.getSurname());
        holder.swStaffKey.setChecked(Integer.parseInt(staffDao.getKey()) > 0 ? true : false);
        holder.swStaffKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference()
                        .child("staff")
                        .child(staffDao.getKeyRef())
                        .child("key");
                if(holder.swStaffKey.isChecked()){
                    databaseReference.setValue("1");
                    Toast.makeText(view.getContext(),staffDao.getKeyRef(),Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.setValue("0");
                    Toast.makeText(view.getContext(),"false",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffDataModelList.size();
    }
}
