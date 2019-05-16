package com.alvin.crud_volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alvin.crud_volley.adapter.RecyclerMainAdapter;
import com.alvin.crud_volley.model.ModelData;
import com.alvin.crud_volley.util.AppController;
import com.alvin.crud_volley.util.ServerAPI;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Widget
    @BindView(R.id.btn_insert)
    Button btnInsert;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    // Vars
    private static final String TAG = "MainActivity";
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        mItems = new ArrayList<>();

        loadJson();

        // Initialize recyclerview
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        mAdapter = new RecyclerMainAdapter(this, mItems);
        recyclerView.setAdapter(mAdapter);

        // Click
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


    }

    private void loadJson() {
        progressDialog.setMessage("Mengambil Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onResponse: " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setNim(data.getString("nim"));
                                md.setNama(data.getString("nama"));
                                md.setKelas(data.getString("kelas"));
                                md.setProdi(data.getString("prodi"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_insert)
            startActivity(new Intent(MainActivity.this, InsertDataActivity.class));
        if (id == R.id.btn_delete)
            startActivity(new Intent(MainActivity.this, DeleteDataActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mItems.clear();
        loadJson();
    }
}
