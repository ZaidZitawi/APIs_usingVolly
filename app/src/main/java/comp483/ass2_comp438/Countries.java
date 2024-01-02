package comp483.ass2_comp438;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Countries extends AppCompatActivity {
    private String url = "https://testapi.devtoolsdaily.com/countries";
    Button btnCountries;
    EditText edtCountry;
    TextView showCountry;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        btnCountries = findViewById(R.id.btnCountries);
        edtCountry = findViewById(R.id.edtCountry);
        showCountry = findViewById(R.id.showcountry);
        queue = Volley.newRequestQueue(this);

        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //entered country
                String enteredCountry = edtCountry.getText().toString().trim();

                //if  country is empty
                if (enteredCountry.isEmpty()) {
                    Toast.makeText(Countries.this, "Please enter a country", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make a request to the API
                makeRequest(enteredCountry);
            }
        });
    }

    private void makeRequest(String enteredCountry) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // convert the response to a json array
                            JSONArray jsonArray = new JSONArray(response);

                            //find the country in the editttext
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject countryObject = jsonArray.getJSONObject(i);

                                // Check if the country name matches the entered name
                                if (countryObject.has("name")) {
                                    String countryName = countryObject.getString("name");

                                    if (countryName.equalsIgnoreCase(enteredCountry)) {
                                        String iso2 = countryObject.getString("iso2");
                                        String iso3 = countryObject.getString("iso3");
                                        showCountry.setText("Country: " + countryName + "\nISO2 Code: " + iso2 + "\nISO3 Code: " + iso3);
                                        return;
                                    }
                                }
                            }

                            // Country not found in the response
                            showCountry.setText("Country not found");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showCountry.setText("Error parsing JSON");
                        }
                    }
                }, new Response.ErrorListener() {//erorr
            @Override
            public void onErrorResponse(VolleyError error) {
                showCountry.setText("Error: " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

}
