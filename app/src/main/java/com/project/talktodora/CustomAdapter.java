package com.project.talktodora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class CustomAdapter  extends PagerAdapter {
    private int[] image_resources={R.drawable.nomessage,R.drawable.nomessage,R.drawable.nomessage,R.drawable.nomessage};
    private int[] string_resources={R.string.talktodora,R.string.talktodora,R.string.talktodora,R.string.talktodora};
    private Context context;
    private LayoutInflater layoutInflater;
    public CustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemview=layoutInflater.inflate(R.layout.lsititem,container,false);
        ImageView iv=itemview.findViewById(R.id.liimage);
        TextView tv=itemview.findViewById(R.id.listtext);
        iv.setImageResource(image_resources[position]);
        tv.setText(string_resources[position]);
        container.addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
