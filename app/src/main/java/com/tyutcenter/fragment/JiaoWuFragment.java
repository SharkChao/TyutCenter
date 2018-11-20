package com.tyutcenter.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tyutcenter.R;
import com.tyutcenter.annotation.ContentView;
import com.tyutcenter.base.BaseFragment;
import com.tyutcenter.databinding.FragmentJiaowuBinding;
import com.tyutcenter.presenter.MainPresenter;
import com.tyutcenter.views.MyGridView;


/**
 * Created by SharkChao on 2017/1/13.
 */
@ContentView(R.layout.fragment_jiaowu)
public class JiaoWuFragment extends BaseFragment<MainPresenter.MainUiCallback> implements MainPresenter.MainUi{
    ImageView ivPhone;
    MyGridView gridView;
    RelativeLayout relativeLayout;
    private String[]titles={"成绩","课表","学分绩点","选课管理","一键评教","四六级查询","我要蹭课"};
    private String[] contents={"随时了解最新成绩","一键查询课表","学分绩点排名一键查询","选课管理","这个是一键评教","这个是四六级查询","要学就学喜欢的课程"};
    private int []picId={R.mipmap.p6, R.mipmap.p2, R.mipmap.p3, R.mipmap.p6, R.mipmap.p6, R.mipmap.p2,R.mipmap.p2,R.mipmap.p2,R.mipmap.p3};


    @Override
    protected void handleArguments(Bundle arguments) {

    }

    @Override
    protected void initTitle() {
        isShowToolBar(false);
    }

    @Override
    protected void initViews(ViewDataBinding viewDataBinding, Bundle savedInstanceState) {
        FragmentJiaowuBinding binding = (FragmentJiaowuBinding) viewDataBinding;
        ivPhone = binding.ivPhone;
        gridView = binding.gvGuide;
        relativeLayout = binding.rlPic;

    }

    @Override
    public void initData() {
        gridView.setAdapter(new MyAdapter());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       //成绩模块
                       break;
                   case 1:
                       //课表模块
                       break;
                   case 2:
                       //学分绩点模块
                       break;
                   case 3:
                       //选课管理模块
                       //选课管理
                       break;
                   case 4:
                       //一键评教
                       break;
                   case 5:
                       //四六级查询
                       break;
                   case 6:
                       //蹭一节课
                       break;
                   case 7:
                       //空闲教室查询
                       break;
                   default:
                       break;
               }
           }
       });
    }

    @Override
    public void initEvent() {
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0351-6010300"));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(getContext(),R.layout.activity_jiaowu_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide);
            TextView title= (TextView) view.findViewById(R.id.tv_guide);
            TextView content= (TextView) view.findViewById(R.id.tv_content);
            imageView.setImageResource(picId[position]);
            title.setText(titles[position]);
            content.setText(contents[position]);
            return view;
        }
    }
}
