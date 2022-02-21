package com.leo.regexpatternvalidator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.leo.regexpatternvalidator.databinding.ActivityMainBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();


        binding.edtString.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                // yourEditText...
                if((!TextUtils.isEmpty(binding.edtRegex.getText().toString())) && regexValidator()) {
                    binding.tvError.setText(matchString());
                }else{
                    binding.edtRegex.setError("Please enter regex");
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    private void init(){
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidate()){

                    binding.tvError.setText(matchString());

                }

            }
        });

    }

    private boolean isValidate(){

        if (TextUtils.isEmpty(binding.edtRegex.getText().toString())) {
            binding.edtRegex.setError("Please enter regex");
            return false;
        }
        if((binding.edtRegex.length()>0) && (!regexValidator())){
            binding.edtRegex.setError("Please enter vaild regex");
            return false;
        }

        if (TextUtils.isEmpty(binding.edtString.getText().toString())) {
            binding.edtString.setError("Please enter something");
            return false;
        }



        return true;
    }

    // Method for checking  entered regex is vaild or not

    private boolean regexValidator(){
        try {
            Pattern.compile(binding.edtRegex.getText().toString());
            return true;
        } catch (PatternSyntaxException exception) {
            System.out.println(exception.getDescription());

            return false;

        }

    }


    // Method for matching regex with entered string

    private String matchString(){

     /*   final Pattern pattern = Pattern.compile(binding.edtRegex.getText().toString());
        final Matcher matcher = pattern.matcher(binding.edtString.getText().toString());

        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }*/
        if(binding.edtString.getText().toString().matches(binding.edtRegex.getText().toString())){
            binding.tvError.setTextColor(Color.GREEN);
            return "Entered String is valid";
        }else{
            String nonMatching = binding.edtString.getText().toString().replaceAll(binding.edtRegex.getText().toString(), "");
            binding.tvError.setTextColor(Color.RED);
          return  "Non matching characters : "+nonMatching;
        }




    }
}