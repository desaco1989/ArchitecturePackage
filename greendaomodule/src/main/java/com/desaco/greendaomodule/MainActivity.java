package com.desaco.greendaomodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * GreenDAO是一个开源的安卓ORM框架，能够使SQLite数据库的开发再次变得有趣。它减轻开发人员处理低级数据库需求，同时节省开发时间。
 * SQLite是一个令人敬畏的内嵌的关系数据库，编写SQL和解析查询结果是相当乏味和耗时的任务。通过将Java对象映射到数据库表（称为ORM，“对象/关系映射”），
 * GreenDAO可以将它们从这些映射中释放出来，这样，您可以使用简单的面向对象的API来存储，更新，删除和查询数据库。
 * 简单的讲，GreenDAO 是一个将对象映射到 SQLite 数据库中的轻量且快速的 ORM 解决方案。
 *
 * https://github.com/greenrobot/greenDAO
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
