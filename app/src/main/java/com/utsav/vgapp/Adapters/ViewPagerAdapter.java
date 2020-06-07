package com.utsav.vgapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.utsav.vgapp.Activities.MainActivity;
import com.utsav.vgapp.Models.Article;
import com.utsav.vgapp.R;
import com.utsav.vgapp.Utils;
import com.utsav.vgapp.Activities.WebActivity;

import java.io.File;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<Article> articles;
    private LayoutInflater layoutInflater;
    private Context context;
    private RelativeLayout relativeLayout;

    public ViewPagerAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.fragment_child,container,false);

        TextView title, desc, author, published_at, scource, time,fullstory, sharebtn;
        ImageView imageView;
        CardView cardView;
        ProgressBar progressBar;


        title = itemView.findViewById(R.id.total);
        desc = itemView.findViewById(R.id.desc);
        author = itemView.findViewById(R.id.author);
        published_at = itemView.findViewById(R.id.publishedat);
        scource = itemView.findViewById(R.id.scource);
        progressBar = itemView.findViewById(R.id.progress_load_photo);
        imageView = itemView.findViewById(R.id.img);
        time  = itemView.findViewById(R.id.samay);
        sharebtn = itemView.findViewById(R.id.share);
        fullstory = itemView.findViewById(R.id.fullstory);
        cardView = itemView.findViewById(R.id.cardview);
        relativeLayout = itemView.findViewById(R.id.relative_layout);





        final String urli = articles.get(position).getUrl();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(articles.get(position).getUrlToImage()).apply(requestOptions);

        title.setText(articles.get(position).getTitle());
        author.setText(articles.get(position).getAuthor());
        desc.setText(articles.get(position).getDescription());
        scource.setText(articles.get(position).getSource().getName());
        Picasso.with(context).load(articles.get(position).getUrlToImage()).into(imageView);
        time.setText(" \u2022 " + Utils.DateToTimeFormat(articles.get(position).getPublishedAt()));
        published_at.setText(Utils.DateFormat(articles.get(position).getPublishedAt()));
        progressBar.setVisibility(View.GONE);
        container.addView(itemView,0);
        progressBar.setVisibility(View.GONE);

        fullstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url",urli);

                context.startActivity(intent);
            }
        });

        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    share();
            }
        });


        return itemView;
    }

    private void share() {

    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view){
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgdrawable = view.getBackground();
        if(bgdrawable != null)
        {
            bgdrawable.draw(canvas);
        }else {
            canvas.drawColor(android.R.color.white);

        }
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }
}
