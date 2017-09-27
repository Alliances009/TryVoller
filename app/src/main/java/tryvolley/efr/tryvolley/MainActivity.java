package tryvolley.efr.tryvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView lvPegawai;
    private ArrayAdapter adapter = null;

    private ArrayList<String> listData = new ArrayList<String>();
    private RequestQueue queue = null;
    private final String url = "http://192.168.1.96/android/API/data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(getApplicationContext());
        getData(url);

        lvPegawai = (ListView) findViewById(R.id.lv_pegawai);
        adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,listData);
        lvPegawai.setAdapter(adapter);

        lvPegawai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) lvPegawai.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, ""+item, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getData(String url) {
        StringRequest strRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Log Data", "onResponse: "+response);
                try {
                    JSONObject res = new JSONObject(response); // parse string jado json object
                    JSONArray data = res.getJSONArray("data"); // get index data (berbentuk array)

                    for (int i = 0;i < data.length();i++){ // proses looping json array
                        listData.add(""+data.get(i));  // add json array ke array
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(strRequest);
    }


}
