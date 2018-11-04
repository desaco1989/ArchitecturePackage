package com.desaco.architecturepackage.jar_utils.glide;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.desaco.architecturepackage.R;

import java.io.File;

/**
 * Created by desaco on 2018/11/4.
 * <p>
 * Glide、Picasso和Fresco三个图片加载库
 * <p>
 * 图片框架的三级缓存策略，或只缓存在内存中 只缓存在SD卡中 或两者都缓存
 * <p>
 * DiskCacheStrategy.NONE 不做磁盘缓存
 * DiskCacheStrategy.SOURCE 只缓存图像原图
 * DiskCacheStrategy.RESULT 只缓存加载后的图像，即处理后最终显示时的图像
 * DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
 * <p>
 * 圆角图片
 * <p>
 * Glide.with(context).resumeRequests()和 Glide.with(context).pauseRequests()
 * 当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求。
 * <p>
 * Glide拦截器
 * <p>
 * Glide只可播放本地视频文件，不可播放网络文件。
 * Glide加载assets文件
 * <p>
 * 可以看到Glide的with方法接受的参数有：Context、Activity、Fragment、FragmentActivity；Glide会自动从Activity、Fragment、FragmentActivity中获取它们的Context‘；
 * 我们还可以用getApplicationContext()方法获取全局Context，当你在Activity生命周期之外获取图片时就可以用这个方法。
 * 接受Activity、Fragment、FragmentActivity作为这个方法的参数有一个好处就是会使图片加载会和Activity、Fragment、FragmentActivity的生命周期绑定在一起。
 * <p>
 * Glide 的基础用法- https://www.jianshu.com/p/e46b42bfeef5
 * Android图片加载框架Glide用法- https://www.cnblogs.com/guilin-hu/p/5706916.html
 * 如何快速将Glide3替换成Glide4- https://www.imooc.com/article/22332?block_id=tuijian_wz
 * Glide使用详解- https://www.jianshu.com/p/7ce7b02988a4
 */

public class GlideUtils {

    /**
     * 基础用法.
     */
    private void baseUsed(Context context, String url, ImageView iv) {//链式调用
        Glide.with(context)
                .load(url)
                .into(iv);
    }

    private void gildeOptions(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.drawable.user)//default_avatar 默认头像
                .error(R.drawable.error_pic)//image_error 错误头像
                .fallback(R.drawable.error_pic);//fallback_nodata 无数据头像

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    private void loadGif(Context context, String gifUrl, ImageView imageView) {
        Glide.with(context).load(gifUrl).into(imageView);
    }

    /**
     * 图片不缓存，实时加载
     * skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
     */
    private void loadImageNoCache(Context context, String url, ImageView iv) {
//        Glide.with(context).load("http://avatar.csdn.net/9/7/A/1_zhangphil.jpg")
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(iv);

//        Glide.get(context).setMemoryCategory(MemoryCategory.LOW);

        // DiskCacheStrategy.NONE： 表示不缓存任何内容。
        // DiskCacheStrategy.DATA： 表示只缓存原始图片。
        // DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
        // DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
        // DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
        RequestOptions options2 = new RequestOptions()
                //禁用内存缓存
                .skipMemoryCache(true)
                //硬盘缓存功能
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context)
                .load(url)
                .apply(options2)
                .into(iv);
    }

    // * 当列表在滑动的时候，调用pauseRequests()取消请求
    private void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    // 当列表在滑动的时候，滑动停止时，调用resumeRequests()恢复请求。
    private void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    //清理内存中的缓存。
    private void clearMemory(Context context) {
        Glide.get(context).clearMemory();


    }

    //  清理硬盘中的缓存。
    private void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();

    }

    /**
     * 自定义圆形裁剪.
     */
    private void customerOptions(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.error_pic)
                .fallback(R.drawable.error_pic)
                .transform(new CircleTransform());//CircleTransform圆角图片

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    //Glide只可播放本地视频文件，不可播放网络文件
    private void loadLocalVideo(Context context, String filePath, ImageView iv) {
        Glide.with(context).load(Uri.fromFile(new File(filePath))).into(iv);
    }

    /**
     * 图片加载优先级
     * <p>
     * Priority.LOW
     * Priority.NORMAL
     * Priority.HIGH
     * Priority.IMMEDIATE
     */
    private void loadImageWithPriority(Context context, String url, ImageView iv) {
        Glide.with(context)
                .load(url)
//                .priority(Priority.HIGH)//优先级
                .into(iv);
    }

    /**
     * 获取gif第一帧
     * <p>
     * 注意：在Glide 3中的语法是先load()再asBitmap()的，
     * <p>
     * 而在Glide 4中是先asBitmap()再load()的
     * <p>
     * 如果写错了顺序就肯定会报错了
     */
    private void loadGifFirstFrame(Context context, String url, ImageView iv) {
        Glide.with(context)
                //.asBitmap()//强制指定加载静态图片
                //.asGif()//强制指定加载动态图片
                //.asFile()//强制指定文件格式的加载
                .asDrawable()//强制指定Drawable格式的加载
                .load("file:///android_asset/jdfw.gif")
                .into(iv);
    }

    //重用开始多个加载
    private void multipleLoad(Context context) {
//        RequestOptions options = new RequestOptions();
//        options.centerCrop()
//                .placeholder(R.drawable.user)
//                .error(R.drawable.error_pic)
//                .fallback(R.drawable.error_pic);
//
//        RequestBuilder<Drawable> requestBuilder =
//                Glide.with(context)
//                        .asDrawable().apply(options);
//        for (int i = 0; i < mImageGroup.getChildCount(); i++) {
//            ImageView view = (ImageView) mImageGroup.getChildAt(i);
//            requestBuilder.clone()
//                    .load(urls[i])
//                    .into(view);
//        }
    }

    private void gildeAppUsed(Context context, String url, ImageView iv) {
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.user)//default_avatar 默认头像
//                .error(R.drawable.error_pic)//image_error 错误头像
//                .fallback(R.drawable.error_pic);//fallback_nodata 无数据头像
//                .into(iv);
    }
    /**
     * DiskCacheStrategy.NONE 不做磁盘缓存
     DiskCacheStrategy.SOURCE 只缓存图像原图
     DiskCacheStrategy.RESULT 只缓存加载后的图像，即处理后最终显示时的图像
     DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
     */
    /**
     * 缓存.
     */
    private void diskCacheStrategyAll(Context context, String url) {
//        Glide.with(context)
//                .asBitmap()
//                .load(url)
//                .placeholder(R.drawable.user)
//                .error(R.drawable.image_error)
//                .fallback(R.drawable.fallback_nodata)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mImageView);
    }


}
