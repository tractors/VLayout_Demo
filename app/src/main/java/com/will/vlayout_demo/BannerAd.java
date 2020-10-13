package com.will.vlayout_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class BannerAd extends DelegateAdapter.Adapter<BaseViewHolder> {

    private Context mContext;

    public BannerAd(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.vlayout_banner,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/1.jpg");
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/2.jpg");
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/3.jpg");
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/4.jpg");
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/5.jpg");
        arrayList.add("http://dn.dengpaoedu.com/examples/glide/6.jpg");
        // 绑定数据
        Banner mBanner = holder.getView(R.id.banner);
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片加载器
        mBanner.setImages(arrayList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(mContext.getApplicationContext(), "banner点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}