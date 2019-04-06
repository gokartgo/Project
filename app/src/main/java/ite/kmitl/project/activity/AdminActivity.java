package ite.kmitl.project.activity;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ite.kmitl.project.R;
import ite.kmitl.project.fragment.AdminLoginFragment;
import ite.kmitl.project.fragment.ShelveAdminFragment;
import ite.kmitl.project.fragment.StaffManageFragment;

public class AdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button btnManageStaff;
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initInstance();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ShelveAdminFragment.newInstance(),"ShelveAdminFragment")
                    .commit();
        }
    }

    private void initInstance() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(AdminActivity.this
                ,drawerLayout
                ,R.string.open_drawer
                ,R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnManageStaff = (Button) findViewById(R.id.btnManageStaff);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnManageStaff.setOnClickListener(ButtonClick);
        btnLogout.setOnClickListener(ButtonClick);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    final View.OnClickListener ButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            drawerLayout.closeDrawer(Gravity.START);
            if(view == btnManageStaff){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, StaffManageFragment.newInstance(),"StaffManageFragment")
                        .addToBackStack(null)
                        .commit();
            }
            if(view == btnLogout) {
                finish();
            }
        }
    };
}
