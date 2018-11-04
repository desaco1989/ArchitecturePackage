package com.desaco.architecturepackage.jar_utils.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.desaco.architecturepackage.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.squareup.picasso.NetworkPolicy.NO_CACHE;
import static com.squareup.picasso.NetworkPolicy.NO_STORE;

/**
 * Created by desaco on 2018/11/4.
 * 自带内存和硬盘二级缓存功能
 * 出现OOM以及图片缓存
 * <p>
 * Android开发中加载图片时遇到的诸多问题，比如OOM，图片错位等
 * <p>
 * Android-Picasso库使用详解-从入门到源码剖析- https://blog.csdn.net/ko_tin/article/details/53427631
 * Picasso最详细的使用指南- https://www.jianshu.com/p/c68a3b9ca07a
 * android Picasso使用详解- https://blog.csdn.net/a704225995/article/details/64122501
 *
 * <p>
 * Picasso其实是Android系统的图片下载和缓存类库
 */

public class PicassoUtils {
    //加载网络图片,Picasso仅需一行代码就能实现图片的异步加载
    public void load(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    //加载网络图片,没有加载图片时显示的默认图像// 图像加载错误时显示的图像// 被加载的控件
    public void loadPlaceErrorChart(Context context, String url, ImageView imageView) {
        Picasso.get().load(url).placeholder(R.drawable.user).error(R.drawable.error_pic).into(imageView);
    }

    //Picasso还支持加载Resources中的资源文件
    public void loadLocalResources(ImageView imageView) {
        Picasso.get().load(R.drawable.user).into(imageView);
    }

    //Picasso还支持加载  assets中的资源文件
    public void loadLocalAssets(ImageView imageView) {
        Picasso.get().load("file:///android_asset/DvpvklR.png").into(imageView);
    }

    //除了加载网络图片以外,Picasso还支持加载Rfiles中的资源文件。
    public void loadLocalFile(Context context, String url, ImageView imageView) {
        Picasso.get().load(new File("fileUrl")).into(imageView);
    }

    private void LoadNative() {
        //加载资源图片
        // Picasso.with(this).load(R.drawable.alipay).into(iv_picasso);
        //加载资产目录图片
        // Picasso.with(this).load("file:///android_asset/heart.png").into(iv_picasso);
        //加载sd卡图片文件
        //   Picasso.with(this).load(new File("XXX")).into(iv_picasso);
    }

    public void loadNoCacheStore(String url, ImageView imageView) {
//        Picasso.get()
//                .load(url)
//                .memoryPolicy(NO_CACHE, NO_STORE)
//                .into(imageView);
    }

    private void clearMemory() {

    }

    private void clearSDcard() {

    }

    //对这张图片进行剪裁，可以使用resize方法：转换图片以适应布局大小并减少内存占用
    public void loadCropAndResizeImage(String url, ImageView imageView) {
        Picasso.get().load(url).resize(100, 100).centerCrop().into(imageView);
    }

    //这里的200表示200px，如果你想在resize时指定dp
    public void loadResizeDimen() {
//        Picasso.get().load("http://n.sinaimg.cn/translate/20160819/9BpA-fxvcsrn8627957.jpg")
//                .resizeDimen(R.dimen.iv_width,R.dimen.iv_height)
//                .into(iv);
    }

    public void loadResizeCrop(String url, ImageView imageView) {
        Picasso.get().load(url).resize(50, 50).centerCrop().into(imageView);
    }

    public void loadGif(ImageView imageView) {
        //加载本地文件
        Picasso.get().load(new File("/images/oprah_bees.gif")).into(imageView);
    }

    //getResources().getDimensionPixelSize(R.dimen.dp472px);
    public void loadAnimatImage(Context context, String url, ImageView imageView) {
        Picasso
                // 创建Picasso对象
                .get()
                // 载入Url,此方法有很多重载,可自行尝试
                .load(url)
                //读取本地文件不缓存
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                //读取网络文件不缓存
                .networkPolicy(NO_CACHE)
                //图片显示之前的占位图片
                .placeholder(R.drawable.user).
                //重新设置宽高, 此处使用了从Dimen文件获取, 推荐这种方式,也可以直接设置PX值
                        resize(context.getResources().getDimensionPixelSize(R.dimen.space_64dp),//R.dimen.space_64dp
                        context.getResources().getDimensionPixelSize(R.dimen.space_64dp))//按照上一步重置的宽高居中显示,R.dimen.space_64dp
                .centerCrop()
                //使用自定义变换, 此处是圆形头像效果
                .transform(new PicassoCircleTransformation())
                //不淡入淡出, 直接显示
                .noFade()
                //目标ImageView
                .into(imageView);
    }

    //picasso可以对多个加载请求设置相同的tag
    public void loadImage(String url, ImageView imageView) {
        Object tag = new Object();
        Picasso.get()
                .load(url)
//                .resize(dp2px(250),dp2px(250))
                .centerCrop()
                .tag(tag)
                .into(imageView);
    }

    //对于不透明的图片可以使用RGB_565来优化内存
    public void loadConfigRGB(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .into(imageView);
    }

}
