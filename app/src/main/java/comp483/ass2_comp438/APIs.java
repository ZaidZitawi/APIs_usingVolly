package comp483.ass2_comp438;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class APIs extends AppCompatActivity {

    Button btnCountries;
    Button btnCompanies;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apis);
        btnCountries=findViewById(R.id.btnCountries);
        btnCompanies=findViewById(R.id.btnCompanies);
        btnLogout=findViewById(R.id.btnLogout);

        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APIs.this, Countries.class);
                startActivity(intent);
            }
        });
        btnCompanies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APIs.this, Companies.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(APIs.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}