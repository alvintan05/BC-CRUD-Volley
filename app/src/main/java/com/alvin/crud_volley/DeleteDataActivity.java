package com.alvin.crud_volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alvin.crud_volley.util.AppController;
import com.alvin.crud_volley.util.ServerAPI;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteDataActivity extends AppCompatActivity implements View.OnClickListener {

    // Widget
    @BindView(R.id.edt_delete_nim)
    EditText edtDeleteNim;
    @BindView(R.id.btn_delete_data)
    Button btnDelete;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        btnDelete.setOnClickListener(this);
    }

    private void deleteData() {
        progressDialog.setMessage("Delete Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest delRequest = new StringRequest(Request.Method.POST, ServerAPI.URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(DeleteDataActivity.this, "Pesan: " + res.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Log.d("volley", "error: " + error.getMessage());
                        Toast.makeText(DeleteDataActivity.this, "Pesan: Delete Gagal", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nim", edtDeleteNim.getText().toString().trim());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(delRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_delete_data) deleteData();
    }
}
