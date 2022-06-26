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
import com.sevima.mobile.adapter.CourseAdapter;
import com.sevima.mobile.model.Course;
import com.sevima.mobile.server.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseFragment extends Fragment {

    private List<Course> courseList;
    private CourseAdapter courseAdapter;
    private final ServerAPI serverAPI;

    public CourseFragment(ServerAPI serverAPI) {
        this.serverAPI = serverAPI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_course, container, false);

        courseList = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        courseAdapter = new CourseAdapter(getContext(),courseList);
        recyclerView.setAdapter(courseAdapter);

        loadData();

        return view;
    }

    private void loadData() {
        Call<String> call = serverAPI.getCourse();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if(response.body() != null){
                        JSONObject object = new JSONObject(response.body());
                        for (int i=0;i<object.getJSONArray("data").length();i++){
                            JSONObject item = object.getJSONArray("data").getJSONObject(i);
                            Course course = new Course(item.getInt("id"),
                                    item.getString("title"),
                                    item.getString("price"),
                                    item.getString("image"),
                                    item.getString("content"));
                            courseList.add(course);
                        }
                        courseAdapter.notifyDataSetChanged();
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