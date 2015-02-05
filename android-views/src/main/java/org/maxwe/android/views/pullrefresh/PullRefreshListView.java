package org.maxwe.android.views.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import org.maxwe.android.views.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Email: danhantao@yeah.net
 * Created by danhantao on 14-12-25.
 */
public class PullRefreshListView extends ListView implements AbsListView.OnScrollListener {
  /* 下划刷新显示格式化日期模板 */
  private final static String DATE_FORMAT = "yyyy年MM月dd日 HH:mm";

  /* 实际的padding的距离与界面上偏移距离的比例 */
  private final static int RATIO = 3;

  private final static int RELEASE_TO_REFRESH = 0;
  private final static int PULL_TO_REFRESH = 1;
  private final static int REFRESHING = 2;
  private final static int DONE = 3;
  private final static int LOADING = 4;

  /* 加载中 */
  private final static int ENDINT_LOADING = 1;

  /* 手动完成刷新 */
  private final static int ENDINT_MANUAL_LOAD_DONE = 2;

  /* 自动完成刷新 */
  private final static int ENDINT_AUTO_LOAD_DONE = 3;

  /*
   * 0:RELEASE_TO_REFRESH; <p> 1:PULL_To_REFRESH; <p> 2:REFRESHING; <p>
   * 3:DONE; <p> 4:LOADING
   */
  private int headState;

  /*
   * 0:完成/等待刷新 ; <p> 1:加载中
   */
  private int endState;

  /* 可以加载更多？ */
  private boolean canLoadMore = false;

  /* 可以下拉刷新？ */
  private boolean canRefresh = false;

  /* 可以自动加载更多吗？（注意，先判断是否有加载更多，如果没有，这个标记也没有意义） */
  private boolean isAutoLoadMore = false;

  /* 当下划，但实际并未刷新，此时禁止自动加载更多 */
  private boolean loadMore = true;

  private LayoutInflater inflater;

  private LinearLayout headView;
  private TextView tipsTextView;
  private TextView lastUpdatedTextView;
  private ImageView arrowImageView;
  private ProgressBar progressBar;

  private View endRootView;
  private ProgressBar endLoadProgressBar;
  private TextView endLoadTipsTextView;

  /* headView动画 */
  private RotateAnimation arrowAnim;

  /* headView反转动画 */
  private RotateAnimation arrowReverseAnim;

  /* 用于保证startY的值在一个完整的touch事件中只被记录一次 */
  private boolean isRecored;

  private int headViewWidth;
  private int headViewHeight;

  private int startY;
  private boolean isBack;

  private int firstItemIndex;
  private int lastItemIndex;
  private int count;

  /* 足够数量充满屏幕？ */
  private boolean enoughCount;

  private OnGListViewListener refreshListener;
  private OnGListViewListener loadMoreListener;

  public PullRefreshListView(Context pContext, AttributeSet pAttrs) {
    super(pContext, pAttrs);
    init(pContext);
  }

  public PullRefreshListView(Context pContext) {
    super(pContext);
    init(pContext);
  }

  public PullRefreshListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
    super(pContext, pAttrs, pDefStyle);
    init(pContext);
  }

  /**
   * 初始化操作
   */
  private void init(Context pContext) {
    setCacheColorHint(pContext.getResources().getColor(R.color.mx_refresh_transparent));
    inflater = LayoutInflater.from(pContext);

    addHeadView();

    setOnScrollListener(this);

    initPullImageAnimation(0);
  }

  /**
   * 当前上划刷新状态
   *
   * @return true可以，false不可以
   */
  public boolean isCanLoadMore() {
    return canLoadMore;
  }

  /**
   * 设置是否可以上划刷新
   *
   * @param canLoadMore true可以，false不可以
   */
  public void setCanLoadMore(boolean canLoadMore) {
    this.canLoadMore = canLoadMore;
    if (this.canLoadMore && getFooterViewsCount() == 0) {
      addFooterView();
    }
    /**
     * 此处可以静止上拉刷新 TODO: 变成点击刷新
     */
//    else if (!this.canLoadMore && endRootView != null) {
//      this.removeFooterView(endRootView);
//    }
  }

  /**
   * 当前下划刷新状态
   *
   * @return true可以，false不可以
   */
  public boolean isCanRefresh() {
    return this.canRefresh;
  }

  /**
   * 设置是否可以下划刷新
   *
   * @param canRefresh true可以，false不可以
   */
  public void setCanRefresh(boolean canRefresh) {
    this.canRefresh = canRefresh;
  }

  /**
   * 当前下划刷新(自动加载更多)状态
   *
   * @return true可以，false不可以
   */
  public boolean isAutoLoadMore() {
    return isAutoLoadMore;
  }

  /**
   * 设置是否可以下划刷新(自动加载更多)
   *
   * @param isAutoLoadMore true可以，false不可以
   */
  public void setAutoLoadMore(boolean isAutoLoadMore) {
    this.isAutoLoadMore = isAutoLoadMore;
  }

  /**
   * 添加下拉刷新的HeadView
   */
  private void addHeadView() {
    headView = (LinearLayout) inflater.inflate(R.layout.mx_listview_head, null);

    arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
    arrowImageView.setMinimumWidth(70);
    arrowImageView.setMinimumHeight(50);
    progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
    tipsTextView = (TextView) headView.findViewById(R.id.head_tipsTextView);
    lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

    measureView(headView);
    headViewHeight = headView.getMeasuredHeight();
    headViewWidth = headView.getMeasuredWidth();

    headView.setPadding(0, -1 * headViewHeight, 0, 0);
    headView.invalidate();

    addHeaderView(headView, null, false);

    headState = DONE;
  }

  /**
   * 添加加载更多FooterView
   */
  private void addFooterView() {
    endRootView = inflater.inflate(R.layout.mx_listview_footer, null);
    endRootView.setVisibility(View.VISIBLE);
    endLoadProgressBar = (ProgressBar) endRootView.findViewById(R.id.pull_to_refresh_progress);
    endLoadTipsTextView = (TextView) endRootView.findViewById(R.id.load_more);
    endRootView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (canLoadMore) {
          if (canRefresh) {
                        /*
                         * 当可以下拉刷新时，如果FooterView没有正在加载，并且HeadView没有正在刷新，才可以点击加载更多
                         * 。
                         */
            if (endState != ENDINT_LOADING && headState != REFRESHING) {
              endState = ENDINT_LOADING;
              onLoadMore();
            }
          } else if (endState != ENDINT_LOADING) {
                        /* 当不能下拉刷新时，FootView不正在加载时，才可以点击加载更多。 */
            endState = ENDINT_LOADING;
            onLoadMore();
          }
        }
      }
    });

    addFooterView(endRootView);

    if (isAutoLoadMore) {
      endState = ENDINT_AUTO_LOAD_DONE;
    } else {
      endState = ENDINT_MANUAL_LOAD_DONE;
    }
  }

  /**
   * 实例化下拉刷新的箭头的动画效果
   */
  private void initPullImageAnimation(final int pAnimDuration) {
    int _Duration;

    if (pAnimDuration > 0) {
      _Duration = pAnimDuration;
    } else {
      _Duration = 250;
    }

    Interpolator _Interpolator = new LinearInterpolator();

    arrowAnim = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    arrowAnim.setInterpolator(_Interpolator);
    arrowAnim.setDuration(_Duration);
    arrowAnim.setFillAfter(true);

    arrowReverseAnim = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    arrowReverseAnim.setInterpolator(_Interpolator);
    arrowReverseAnim.setDuration(_Duration);
    arrowReverseAnim.setFillAfter(true);
  }

  /**
   * 测量HeadView宽高(注意：此方法仅适用于LinearLayout。)
   */
  private void measureView(View pChild) {
    ViewGroup.LayoutParams p = pChild.getLayoutParams();
    if (p == null) {
      p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
    int lpHeight = p.height;

    int childHeightSpec;
    if (lpHeight > 0) {
      childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
    } else {
      childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
    }
    pChild.measure(childWidthSpec, childHeightSpec);
  }

  /**
   * 为了判断滑动到ListView底部没
   */
  @Override
  public void onScroll(AbsListView pView, int pFirstVisibleItem, int pVisibleItecount,
      int pTotalItecount) {
    firstItemIndex = pFirstVisibleItem;
    lastItemIndex = pFirstVisibleItem + pVisibleItecount - 2;
    count = pTotalItecount - 2;
    if (pTotalItecount > pVisibleItecount) {
      enoughCount = true;
    } else {
      enoughCount = false;
    }
  }

  @Override
  public void onScrollStateChanged(AbsListView pView, int pScrollState) {
        /* 存在加载更多功能 */
    if (canLoadMore) {
      if (lastItemIndex == count && pScrollState == SCROLL_STATE_IDLE) {
                /* SCROLL_STATE_IDLE=0，滑动停止 */
        if (endState != ENDINT_LOADING) {
                    /* 自动加载更多，我们让FooterView显示 “更 多” */
          if (isAutoLoadMore) {
                        /* 存在下划刷新并且HeadView没有正在刷新时，FooterView可以自动加载更多。 */
            if (canRefresh) {
              if (headState != REFRESHING && loadMore) {
                                /* FooterView显示 :加载中... */
                endState = ENDINT_LOADING;
                onLoadMore();
                changeEndViewByState();
              }
              loadMore = true;
            } else {
                            /*
                             * 没有下拉刷新，我们直接进行加载更多。 FooterView显示 : 加载中...
                             */
              endState = ENDINT_LOADING;
              onLoadMore();
              changeEndViewByState();
            }
          } else {
                        /*
                         * 不是自动加载更多
                         */
            endState = ENDINT_MANUAL_LOAD_DONE;
            changeEndViewByState();
          }
        }
      }
    } else if (endRootView != null && endRootView.getVisibility() == VISIBLE) {
            /* 突然关闭加载更多功能之后，要移除FooterView。 */
      endRootView.setVisibility(View.GONE);
      this.removeFooterView(endRootView);
    }
  }

  /**
   * 改变加载更多状态
   */
  private void changeEndViewByState() {
        /* 允许加载更多 */
    if (canLoadMore) {
      switch (endState) {
            /* 刷新中 */
        case ENDINT_LOADING:

                    /* 加载中... */
          if (endLoadTipsTextView.getText().equals(R.string.mx_listview_doing_end_refresh)) {
            break;
          }
          endLoadTipsTextView.setText(R.string.mx_listview_doing_end_refresh);
          endLoadTipsTextView.setVisibility(View.VISIBLE);
          endLoadProgressBar.setVisibility(View.VISIBLE);
          break;

                /* 手动刷新完成 */
        case ENDINT_MANUAL_LOAD_DONE:

                    /* 自动刷新完成 */
        case ENDINT_AUTO_LOAD_DONE:

                    /*
                     * 当所有item的高度小于ListView本身的高度时，要隐藏掉FooterView
                     */

          endLoadTipsTextView.setText(R.string.mx_listview_end_click_load_more);
          endLoadTipsTextView.setVisibility(View.VISIBLE);
          endLoadProgressBar.setVisibility(View.GONE);

          endRootView.setVisibility(View.VISIBLE);

                    /*if (enoughCount)
                    {

                    }
                    else
                    {
                        endRootView.setVisibility(View.GONE);
                    }*/

          break;

        default:

          break;
      }
    }
  }

  public boolean onTouchEvent(MotionEvent event) {
    if (canRefresh) {
      if (canLoadMore && endState == ENDINT_LOADING) {
                /* 如果存在加载更多功能，并且当前正在加载更多，默认不允许下划刷新，必须加载完毕后才能使用。 */
        return super.onTouchEvent(event);
      }

      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          if (firstItemIndex == 0 && !isRecored) {
            isRecored = true;
            startY = (int) event.getY();
          }
          break;

        case MotionEvent.ACTION_UP:
          if (headState != REFRESHING && headState != LOADING) {
            if (headState == DONE) {

            }
            if (headState == PULL_TO_REFRESH) {
              headState = DONE;
              changeHeaderViewByState();
            }
            if (headState == RELEASE_TO_REFRESH) {
              headState = REFRESHING;
              changeHeaderViewByState();
              onRefresh();
            }
          }

          isRecored = false;
          isBack = false;
          break;

        case MotionEvent.ACTION_MOVE:
          int tempY = (int) event.getY();

          if (!isRecored && firstItemIndex == 0) {
            isRecored = true;
            startY = tempY;
          }

          if (headState != REFRESHING && isRecored && headState != LOADING) {
                        /*
                         * 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，
                         * 列表会同时进行滚动 ， 可以松手去刷新了
                         */
            if (headState == RELEASE_TO_REFRESH) {
              setSelection(0);
                            /* 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步 */
              if (((tempY - startY) / RATIO < headViewHeight) && (tempY - startY) > 0) {
                headState = PULL_TO_REFRESH;
                changeHeaderViewByState();
              }

                            /* 一下子推到顶了 */
              else if (tempY - startY <= 0) {
                headState = DONE;
                changeHeaderViewByState();
              }

                            /* 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步 */
            }

                        /* 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态 */
            if (headState == PULL_TO_REFRESH) {
              loadMore = false;

              setSelection(0);

                            /* 下拉到可以进入RELEASE_TO_REFRESH的状态 */
              if ((tempY - startY) / RATIO >= headViewHeight) {
                headState = RELEASE_TO_REFRESH;
                isBack = true;
                changeHeaderViewByState();
              } else if (tempY - startY <= 0) {
                headState = DONE;
                changeHeaderViewByState();
              }
            }

            if (headState == DONE) {
              if (tempY - startY > 0) {
                headState = PULL_TO_REFRESH;
                changeHeaderViewByState();
              }
            }

            if (headState == PULL_TO_REFRESH) {
              headView.setPadding(0, -1 * headViewHeight + (tempY - startY) / RATIO, 0, 0);

            }

            if (headState == RELEASE_TO_REFRESH) {

              headView.setPadding(0, (tempY - startY) / RATIO - headViewHeight, 0, 0);
            }
          }
          break;
      }
    }

    return super.onTouchEvent(event);
  }

  /**
   * 当HeadView状态改变时候，调用该方法，以更新界面
   */
  private void changeHeaderViewByState() {
    switch (headState) {
      case RELEASE_TO_REFRESH:
        arrowImageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tipsTextView.setVisibility(View.VISIBLE);
        lastUpdatedTextView.setVisibility(View.VISIBLE);

        arrowImageView.clearAnimation();
        arrowImageView.startAnimation(arrowAnim);
        tipsTextView.setText(R.string.mx_listview_release_refresh);
        break;

      case PULL_TO_REFRESH:
        progressBar.setVisibility(View.GONE);
        tipsTextView.setVisibility(View.VISIBLE);
        lastUpdatedTextView.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.setVisibility(View.VISIBLE);
        if (isBack) {
          isBack = false;
          arrowImageView.clearAnimation();
          arrowImageView.startAnimation(arrowReverseAnim);
          tipsTextView.setText(R.string.mx_listview_pull_to_refresh);
        } else {
          tipsTextView.setText(R.string.mx_listview_pull_to_refresh);
        }
        break;

      case REFRESHING:
        headView.setPadding(0, 0, 0, 0);

        progressBar.setVisibility(View.VISIBLE);
        arrowImageView.clearAnimation();
        arrowImageView.setVisibility(View.GONE);
        tipsTextView.setText(R.string.mx_listview_doing_head_refresh);
        lastUpdatedTextView.setVisibility(View.VISIBLE);
        break;

      case DONE:
        headView.setPadding(0, -1 * headViewHeight, 0, 0);

        progressBar.setVisibility(View.GONE);
        arrowImageView.clearAnimation();
        arrowImageView.setImageResource(R.drawable.mw_pullrefresh_arrow);
        tipsTextView.setText(R.string.mx_listview_pull_to_refresh);
        lastUpdatedTextView.setVisibility(View.VISIBLE);
        break;
    }
  }

  /**
   * OnGListView 的监听事件接口，包含了下划和上划监听方法
   */
  public interface OnGListViewListener {
    public void onRefresh();

    public void onLoadMore();
  }

  /**
   * 设置下划监听事件，反之不会有此功能
   */
  public void setOnRefreshListener(OnGListViewListener pRefreshListener) {
    if (pRefreshListener != null) {
      this.setCanRefresh(true);
      refreshListener = pRefreshListener;
    }
  }

  /**
   * 设置上划监听事件，反之不会有此功能
   */
  public void setOnLoadMoreListener(OnGListViewListener loadMoreListener) {
    if (loadMoreListener != null) {
      this.setCanLoadMore(true);
      this.setAutoLoadMore(true);

      this.loadMoreListener = loadMoreListener;
      if (canLoadMore && getFooterViewsCount() == 0) {
        addFooterView();
      }
    }
  }

  /**
   * 下划刷新
   */
  private void onRefresh() {
    if (refreshListener != null) {
      refreshListener.onRefresh();
    }
  }

  /**
   * 设置要显示LISTVIEW的ITEM
   *
   * @param itemIndex 要显示的ITEM的下标
   */
  public void setDispayItem(int itemIndex) {
    setSelection(itemIndex);
  }

  /**
   * 设置加载更多的View显示还是隐藏
   */
  public void changeEndView(int type) {
    endRootView.setVisibility(type);
  }

  /**
   * 隐藏下划刷新的状态。一般在下划刷新的数据请求完成，adapter刷新之后，调用此方法
   */
  public void onRefreshComplete() {
    headState = DONE;
    lastUpdatedTextView.setText(
        getResources().getString(R.string.mx_listview_refresh_lasttime) + new SimpleDateFormat(
            DATE_FORMAT, Locale.CHINA).format(new Date()));
    changeHeaderViewByState();
  }

  /**
   * 上划刷新
   */
  private void onLoadMore() {
    if (loadMoreListener != null) {
      endLoadTipsTextView.setText(R.string.mx_listview_doing_end_refresh);
      endLoadTipsTextView.setVisibility(View.VISIBLE);
      endLoadProgressBar.setVisibility(View.VISIBLE);

      loadMoreListener.onLoadMore();
    }
  }

  /**
   * 隐藏上划刷新的状态。一般在上划刷新的数据请求完成，adapter刷新之后，调用此方法
   */
  public void onLoadMoreComplete() {
    if (isAutoLoadMore) {
      endState = ENDINT_AUTO_LOAD_DONE;
    } else {
      endState = ENDINT_MANUAL_LOAD_DONE;
    }
    changeEndViewByState();
  }

  /**
   * 主要更新一下，下划刷新时间
   */
  public void setAdapter(BaseAdapter adapter) {
    lastUpdatedTextView.setText(
        getResources().getString(R.string.mx_listview_refresh_lasttime) + new SimpleDateFormat(
            DATE_FORMAT, Locale.CHINA).format(new Date()));
    super.setAdapter(adapter);
  }

}

