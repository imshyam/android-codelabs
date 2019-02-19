package com.shyam.a0219alarmmanager;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.shyam.a0219alarmmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

    }

    public void onCheckChange(CompoundButton button, Boolean isChecked) {
        if(isChecked) {
            Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "UnChecked", Toast.LENGTH_SHORT).show();
        }
    }
}
