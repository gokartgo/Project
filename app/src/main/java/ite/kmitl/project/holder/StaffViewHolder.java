package ite.kmitl.project.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import ite.kmitl.project.R;

public class StaffViewHolder extends RecyclerView.ViewHolder{

    public TextView tvUsername;
    public TextView tvName;
    public TextView tvSurname;
    public Switch swStaffKey;

    public StaffViewHolder(View itemView) {
        super(itemView);
        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvSurname = (TextView) itemView.findViewById(R.id.tvSurname);
        swStaffKey = (Switch) itemView.findViewById(R.id.swStaffKey);
    }
}
