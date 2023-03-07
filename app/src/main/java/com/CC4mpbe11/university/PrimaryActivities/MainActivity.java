package com.CC4mpbe11.university.PrimaryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.CC4mpbe11.university.Database.SchoolRepository;
import com.CC4mpbe11.university.Entities.UserEntity;
import com.CC4mpbe11.university.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Boolean usernameMatch = false;
    private SchoolRepository schoolRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schoolRepository = new SchoolRepository(getApplication());
        List<UserEntity> allUsers = schoolRepository.getAllUsers();
        boolean adminUser = false;
        for(UserEntity user : allUsers){
            if(user.getUsername().equals("Admin")){
                adminUser = true;
            }
            else{
                adminUser = false;
            }
        }
        if(!adminUser){
            UserEntity user = new UserEntity(1,"Admin", "Admin");
            schoolRepository.insert(user);
        }
        Button enterButtonId = findViewById(R.id.enterButtonId);
        enterButtonId.setOnClickListener(
                v -> openMenuActivity()
        );
    }

    public void openMenuActivity() {
        usernameMatch = false;

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        TextView userPassErrorText = findViewById(R.id.userPassErrorText);

        for(UserEntity user : schoolRepository.getAllUsers()) {
            if (usernameEditText.getText().toString().equals(user.getUsername())) {
                usernameMatch = true;
                if(passwordEditText.getText().toString().equals(user.getPassword())){
                    userPassErrorText.setText("");
                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                }
                else{
                    userPassErrorText.setText(R.string.userPassIncorrect);
                }
            }
        }
        if (!usernameMatch){
            userPassErrorText.setText(R.string.userPassIncorrect);
        }
    }
}