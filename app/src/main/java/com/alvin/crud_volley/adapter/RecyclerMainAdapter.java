package com.alvin.crud_volley.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvin.crud_volley.InsertDataActivity;
import com.alvin.crud_volley.R;
import com.alvin.crud_volley.model.ModelData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerMainAdapter.ViewHolder> {

    private Context mContext;
    private List<ModelData> mItems;

    public RecyclerMainAdapter(Context mContext, List<ModelData> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ModelData md = mItems.get(i);
        viewHolder.tvNim.setText(md.getNim());
        viewHolder.tvNama.setText(md.getNama());
        viewHolder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nim)
        TextView tvNim;
        @BindView(R.id.tv_nama)
        TextView tvNama;

        ModelData md;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(mContext, InsertDataActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("nim", md.getNim());
                    update.putExtra("nama", md.getNama());
                    update.putExtra("kelas", md.getKelas());
                    update.putExtra("prodi", md.getProdi());

                    mContext.startActivity(update);
                }
            });
        }
    }
}
