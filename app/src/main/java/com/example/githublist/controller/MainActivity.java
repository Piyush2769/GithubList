package com.example.githublist.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githublist.ItemAdapter;
import com.example.githublist.R;
import com.example.githublist.api.Client;
import com.example.githublist.api.Service;
import com.example.githublist.model.Item;
import com.example.githublist.model.ItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView disconnected;
    private Item item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        swipeContainer=findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(MainActivity.this, "Github users refreshed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadJSON()
    {
        disconnected=findViewById(R.id.disconnected);
        try
        {
            Client client=new Client();
            Service apiService=
                    Client.getClient().create(Service.class);

            Call<ItemResponse> call=apiService.getItems();
            call.enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response)
                {
                    List<Item> items=response.body().getItems();
                    recyclerView.setAdapter(new ItemAdapter(getApplicationContext(),items));
                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {

                    Toast.makeText(MainActivity.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            });
        }

        catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    private void initViews()
    {
        pd=new ProgressDialog(this);
        pd.setMessage("Fetching Github Users....");
        pd.setCancelable(false);
        pd.show();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }
}
