package com.utsav.vgapp.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utsav.vgapp.Activities.MainActivity;
import com.utsav.vgapp.Adapters.ViewPagerAdapter;
import com.utsav.vgapp.ApiClient;
import com.utsav.vgapp.Models.Article;
import com.utsav.vgapp.Models.News;
import com.utsav.vgapp.R;
import com.utsav.vgapp.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {



    private VerticalViewPager verticalViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Article> articles = new ArrayList<>();
    public static final String API_KEY = "bcc657afdd334ff4af1b914be4ae3549";
    private RelativeLayout relativeLayout;
    private View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.main_fragment, container, false);

        verticalViewPager = view.findViewById(R.id.viewpager);

        LoadJSON("","in",API_KEY);

        return view;


    }

    public void LoadJSON(String query,String country, String key){
        Call<News> call;

        call = ApiClient.getInstance().getapi().getNews(country,key);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null)
                {
                    articles.clear();
                    articles = response.body().getArticles();
                    viewPagerAdapter = new ViewPagerAdapter(articles,getContext());
                    verticalViewPager.setAdapter(viewPagerAdapter);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showToast(String message){
        View toastview = getLayoutInflater().inflate(R.layout.custom_toast,(ViewGroup)view.findViewById(R.id.lin_ley));

        TextView textView = toastview.findViewById(R.id.error_message_toast);
        TextView retry_btn = toastview.findViewById(R.id.retry_btn);

        textView.setText(message);
        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadJSON("","in",API_KEY);
            }
        });

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastview);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }

}
