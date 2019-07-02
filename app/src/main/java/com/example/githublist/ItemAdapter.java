package com.example.githublist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githublist.controller.DetailActivity;
import com.example.githublist.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private List<Item> items;
    private Context context;

    public ItemAdapter(Context context,List<Item> itemArrayList) {
        this.items = itemArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {

        holder.title.setText(items.get(position).getLogin());
        holder.githublinkk.setText(items.get(position).getHtmlUrl());


        Picasso.with(context)
                .load(items.get(position).getAvatarUrl())
                .placeholder(R.drawable.load)
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, githublinkk;
        private ImageView imageView;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            githublinkk = (TextView) view.findViewById(R.id.githublink);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Item clickedDataItem = items.get(pos);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(pos).getLogin());
                        intent.putExtra("html_url", items.get(pos).getHtmlUrl());
                        intent.putExtra("avatar_url", items.get(pos).getAvatarUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }

}
