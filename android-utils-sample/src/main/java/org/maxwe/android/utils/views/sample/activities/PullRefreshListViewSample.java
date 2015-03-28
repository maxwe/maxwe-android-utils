package org.maxwe.android.utils.views.sample.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.maxwe.android.utils.views.pullrefresh.PullRefreshListView;
import org.maxwe.android.utils.views.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: danhantao@yeah.net
 * Created by danhantao on 14-12-25.
 */
public class PullRefreshListViewSample extends Activity {
  private PullRefreshListView pullRefreshListView;
  private ListViewAdapter listViewAdapter;
  private List<String[]> listData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mx_listview_pullrefresh);
    pullRefreshListView = (PullRefreshListView) findViewById(R.id.mx_pullrefresh_listview);
    listData = new ArrayList<>();
    initData();
    listViewAdapter = new ListViewAdapter(this, listData);
    pullRefreshListView.setAdapter(listViewAdapter);
    // 下拉刷新
    pullRefreshListView.setOnRefreshListener(new PullRefreshListView.OnGListViewListener() {
      @Override
      public void onRefresh() {
        new RefreshAsync().execute();

      }

      @Override
      public void onLoadMore() {

      }
    });

    //加载更多
    pullRefreshListView.setOnLoadMoreListener(new PullRefreshListView.OnGListViewListener() {
      @Override
      public void onRefresh() {

      }

      @Override
      public void onLoadMore() {

        new onLoadMore().execute();
      }
    });
    pullRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] str = listData.get(i);
        Toast.makeText(PullRefreshListViewSample.this, str[0] + ":" + str[1], Toast.LENGTH_LONG)
            .show();
      }
    });

  }

  class RefreshAsync extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      List<String[]> list = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        String str[] = {("刷新" + i + " 一"), ("刷新" + i + " 二")};
        list.add(str);
      }
      listData.addAll(0, list);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      pullRefreshListView.onRefreshComplete();
      listViewAdapter.onChangeData(listData);
    }
  }

  class onLoadMore extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      List<String[]> list = new ArrayList<>();
      for (int i = 0; i < 20; i++) {
        String str[] = {("加载更多" + i + " 一"), ("加载更多" + i + " 二")};
        list.add(str);
      }
      listData.addAll(listData.size(), list);
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      listViewAdapter.onChangeData(listData);
      pullRefreshListView.onLoadMoreComplete();
      if (listData.size()>200){
        pullRefreshListView.setCanRefresh(false);
        pullRefreshListView.setAutoLoadMore(false);
        pullRefreshListView.changeEndView(View.GONE);//隐藏加载加载更多
      }
    }
  }

  /**
   * 初始化数据
   */
  private void initData() {
    for (int i = 0; i < 20; i++) {
      String str[] = {("标题" + i + " 一"), ("标题" + i + " 二")};
      listData.add(str);
    }
  }

  class ListViewAdapter extends BaseAdapter {
    private List<String[]> list;
    private Context ctx;

    public void onChangeData(List<String[]> setData) {
      this.list = setData;
      this.notifyDataSetChanged();
    }

    public ListViewAdapter(Context ctx, List<String[]> list) {
      this.ctx = ctx;
      this.list = list;
    }

    @Override
    public int getCount() {
      return list.size();
    }

    @Override
    public Object getItem(int i) {
      return list.get(i);
    }

    @Override
    public long getItemId(int i) {
      return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
      ViewHolder viewHolder;
      if (view == null) {
        view = View.inflate(ctx, R.layout.mx_listview_pullrefresh_item, null);
        viewHolder = new ViewHolder();
        viewHolder.textView1 = (TextView) view.findViewById(R.id.mv_pullrefresh_title1);
        viewHolder.textView2 = (TextView) view.findViewById(R.id.mv_pullrefresh_title2);
        view.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) view.getTag();
      }
      String[] str = list.get(i);
      viewHolder.textView1.setText(str[0]);
      viewHolder.textView2.setText(str[1]);
      if (i % 3 == 0) {
        viewHolder.textView1.setTextColor(Color.parseColor("#00ff00"));
        viewHolder.textView2.setTextColor(getResources().getColor(R.color.color1));
      } else if (i % 3 == 1) {
        viewHolder.textView1.setTextColor(getResources().getColor(R.color.color2));
        viewHolder.textView2.setTextColor(getResources().getColor(R.color.color3));
      } else {
        viewHolder.textView1.setTextColor(getResources().getColor(R.color.color4));
        viewHolder.textView2.setTextColor(getResources().getColor(R.color.color5));
      }
      return view;
    }
  }

  static class ViewHolder {
    TextView textView1;
    TextView textView2;
  }
}
