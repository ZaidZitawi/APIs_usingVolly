package comp483.ass2_comp438;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Companies extends AppCompatActivity {
//this API shows the big 40 Companies in the Weerld_british accent. and reveal them in an arraylist
    private ListView listView;
    private Button btnLoad;
    private List<String> companyList;

    private static final String API_URL = "https://testapi.devtoolsdaily.com/companies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        listView = findViewById(R.id.listv);
        btnLoad = findViewById(R.id.btnload);
        companyList = new ArrayList<>();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }

    private void request() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a json array from the provided URL using get method request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJsonArray(response);
                        updateListView();
                    }
                },//throw an error as toast massege
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(Companies.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void parseJsonArray(JSONArray jsonArray) {
        companyList.clear(); // Clear the list before adding new data

        try {
            // Loop through the JSON array and extract needed info
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject companyObject = jsonArray.getJSONObject(i);
                String companyName = companyObject.getString("name");

                // Add the company name
                companyList.add(companyName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListView() {
        // Create an ArrayAdapter to display the company names in the listview
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                companyList);

        // Set the ArrayAdapter to the ListView
        listView.setAdapter(arrayAdapter);
    }
}
