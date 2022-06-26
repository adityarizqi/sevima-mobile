package com.sevima.mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevima.mobile.R;
import com.sevima.mobile.adapter.NewsAdapter;
import com.sevima.mobile.model.News;
import com.sevima.mobile.server.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private final ServerAPI serverAPI;

    public NewsFragment(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_news, container, false);

        newsList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(getContext(),newsList);
        recyclerView.setAdapter(newsAdapter);

        loadData();

        return view;
    }

    private void loadData() {
        Call<String> call = serverAPI.getNews();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body() != null){
                        JSONObject object = new JSONObject(response.body());
                        for (int i=0;i<object.getJSONArray("data").length();i++){
                            JSONObject item = object.getJSONArray("data").getJSONObject(i);
                            News news = new News(item.getInt("id"),
                                    item.getString("title"),
                                    item.getString("image"),
                                    item.getString("content"));
                            newsList.add(news);
                        }
                        newsAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}