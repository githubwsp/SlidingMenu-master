package com.wsp.thinkpad.slidingmenu;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * author  ${吴心良}
 * data: 2017/3/29.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingMenu slidingMenu = new SlidingMenu(this);
        //设置左右两个滑动菜单滑动
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //全屏都能滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //弄个阴影
        slidingMenu.setShadowDrawable(R.drawable.demo);
        //阴影宽度
//        slidingMenu.setShadowWidthRes(20);
        // 菜单的宽度
        slidingMenu.setBehindWidth(555);
        //渐变效果
        slidingMenu.setFadeDegree(0.55f);
        //添加
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //这个坑，布局里面设置数据要这样设置，马丹，刚刚开始直接在上面设置没数据，郁闷了半天
        //这个坑，布局里面设置数据要这样设置，马丹，刚刚开始直接在上面设置没数据，郁闷了半天
        //这个坑，布局里面设置数据要这样设置，马丹，刚刚开始直接在上面设置没数据，郁闷了半天
        //重要的事情说三遍
        slidingMenu.setMenu(setLeftMenu());
        slidingMenu.setSecondaryMenu(R.layout.sliddingmenu_right);//设置右侧滑的布局文件
        slidingMenu.setSecondaryShadowDrawable(R.drawable.demo);//设置右侧滑的布局效果

    }

    public View setLeftMenu() {
        View view = getLayoutInflater().inflate(R.layout.slidingmenu_left, null);
        final PullToRefreshListView listview = (PullToRefreshListView) view.findViewById(R.id.dongtai_listview);
        //设置上拉和下拉
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        //这里设置一些常规的东西
        listview.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listview.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listview.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listview.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        listview.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        listview.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, getData()));
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void reslst) {
                        //结束刷新
                        listview.onRefreshComplete();
                    }
                }.execute();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                //监听listview item 点击
            }
        });
        return view;
    }

    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            data.add("我是左边第" + i + "个");
        }
        return data;
    }

}
