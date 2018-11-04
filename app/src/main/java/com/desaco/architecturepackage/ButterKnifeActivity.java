package com.desaco.architecturepackage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by desaco on 2018/11/3.
 */

public class ButterKnifeActivity extends Activity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv3)
    TextView mTv3;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_butterknife
        setContentView(R.layout.activity_butterknife);
    }
}
