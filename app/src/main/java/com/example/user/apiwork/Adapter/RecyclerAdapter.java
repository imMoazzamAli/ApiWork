package com.example.user.apiwork.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.apiwork.Model.ModelSignIn;
import com.example.user.apiwork.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    private List<ModelSignIn> modelSignInsList;

    public RecyclerAdapter(List<ModelSignIn> modelSignInsList) {
        this.modelSignInsList = modelSignInsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtName.setText(modelSignInsList.get(position).getFirstName());
        holder.txtEmail.setText(modelSignInsList.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return modelSignInsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);



            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }
    }

}
