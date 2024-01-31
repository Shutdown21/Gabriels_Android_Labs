package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.hube0078.R;
import algonquin.cst2335.hube0078.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.button.setOnClickListener(click ->{
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });
        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has: " + model.editString);
        });
        model.isSelected.observe(this, seleceted ->{
            variableBinding.checkbox.setChecked(seleceted);
            variableBinding.radio.setChecked(seleceted);
            variableBinding.swicth.setChecked(seleceted);
        });

        variableBinding.checkbox.setOnCheckedChangeListener((btn, isChecked) ->{
            model.isSelected.postValue(isChecked);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "The value is now: " +isChecked, duration);
            toast.show();
        });

        variableBinding.swicth.setOnCheckedChangeListener((btn, isChecked) ->{
            model.isSelected.postValue(isChecked);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "The value is now: " +isChecked, duration);
            toast.show();
        });

        variableBinding.radio.setOnCheckedChangeListener((btn, isChecked) ->{
            model.isSelected.postValue(isChecked);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, "The value is now: " +isChecked, duration);
            toast.show();
        });

        variableBinding.logo.setOnClickListener((btn) ->{});

        variableBinding.myimagebutton.setOnClickListener(click ->{
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this ,"The height is: " + click.getHeight() + " The width is : " + click.getWidth(), duration);
            toast.show();
        });
    }
}