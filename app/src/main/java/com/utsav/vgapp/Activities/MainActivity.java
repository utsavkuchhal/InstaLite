package com.utsav.vgapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.utsav.vgapp.ApiClient;
import com.utsav.vgapp.Models.Article;
import com.utsav.vgapp.Models.News;
import com.utsav.vgapp.Adapters.PagerAdapter;
import com.utsav.vgapp.R;
import com.utsav.vgapp.Adapters.ViewPagerAdapter;
import com.utsav.vgapp.Settings;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    private VerticalViewPager viewPager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String API_KEY = "bcc657afdd334ff4af1b914be4ae3549";
    private ViewPagerAdapter viewAdapter;
    private PagerAdapter pagerAdapter;
    private List<Article> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("My Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));
        tabLayout.addTab(tabLayout.newTab().setText("Trending"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        LoadJSON("","in",API_KEY);

    }

    public void LoadJSON(String query,String country, String key){


        Call<News> call;
        if (query.length() > 0)
        {
            call = ApiClient.getInstance().getapi().getspecificdata(query,API_KEY);
        }else
        {
            Intent intent = getIntent();
            String category = intent.getStringExtra("category");
            if (category == null)
                call = ApiClient.getInstance().getapi().getNews(country,key);
            else
                call = ApiClient.getInstance().getapi().getNewsbyCategories(country,category,key);
        }



        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null)
                {
                    articles.clear();
                    articles = response.body().getArticles();
                    viewAdapter = new ViewPagerAdapter(articles,MainActivity.this);
                    viewPager.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
//                showToast("Network Error");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem serachmenuitem = menu.findItem(R.id.action_search);


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2)
                {
                    LoadJSON(query,"in",API_KEY);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadJSON(newText,"in",API_KEY);
                return false;
            }
        });

        serachmenuitem.getIcon().setVisible(false,false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.android_settings){
           startActivity(new Intent(MainActivity.this,Settings.class));
        }
        return true;
    }

//    public void showToast(String message){
//        View toastview = getLayoutInflater().inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.lin_ley));
//
//        TextView textView = toastview.findViewById(R.id.error_message_toast);
//        TextView retry_btn = toastview.findViewById(R.id.retry_btn);
//
//        textView.setText(message);
//        retry_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        Toast toast = new Toast(MainActivity.this);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(toastview);
//        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
//        toast.show();
//    }


}
