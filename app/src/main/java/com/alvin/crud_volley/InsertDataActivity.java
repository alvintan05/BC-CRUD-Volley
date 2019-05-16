package com.alvin.crud_volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class InsertDataActivity extends AppCompatActivity implements View.OnClickListener {

    // widget
    @BindView(R.id.btn_simpan)
    Button btnSave;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.edt_nim)
    EditText edtNim;
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_kelas)
    EditText edtKelas;
    @BindView(R.id.edt_prodi)
    EditText edtProdi;

    ProgressDialog progressDialog;

    // vars
    int update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        // Get data from intent
        Intent data = getIntent();
        update = data.getIntExtra("update", 0);
        String intent_nim = data.getStringExtra("nim");
        String intent_nama = data.getStringExtra("nama");
        String intent_kelas = data.getStringExtra("kelas");
        String intent_prodi = data.getStringExtra("prodi");

        // Kondisi update/insert
        if (update == 1) {
            btnSave.setText("Update Data");
            edtNim.setText(intent_nim);
            edtNama.setText(intent_nama);
            edtKelas.setText(intent_kelas);
            edtProdi.setText(intent_prodi);

            edtNim.setVisibility(View.GONE);
        }

        // On click Listener
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void saveData() {
        String pesan = update == 1 ? "Mengupdate data" : "Menyimpan data";
        String url = update == 1 ? ServerAPI.URL_UPDATE : ServerAPI.URL_INSERT;

        progressDialog.setMessage(pesan);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertDataActivity.this, "Pesan: " + res.getString("message"), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(InsertDataActivity.this, "Pesan: Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nim", edtNim.getText().toString().trim());
                map.put("nama", edtNama.getText().toString().trim());
                map.put("kelas", edtKelas.getText().toString().trim());
                map.put("prodi", edtProdi.getText().toString().trim());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_simpan) saveData();
        if (id == R.id.btn_cancel) finish();
    }
}
