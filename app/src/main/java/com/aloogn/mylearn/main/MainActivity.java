package com.aloogn.mylearn.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aloogn.mylearn.R;
import com.aloogn.mylearn.base.BaseActivity;
import com.aloogn.mylearn.base.ViewInject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aloogn.mylearn.main.MainConstantTool.BeiJing;
import static com.aloogn.mylearn.main.MainConstantTool.HangZhou;
import static com.aloogn.mylearn.main.MainConstantTool.ShangHai;
import static com.aloogn.mylearn.main.MainConstantTool.ShenZhen;

@ViewInject(mainlayoutid = R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainActivityContract.Iview {

    @BindView(R.id.fab_main_home)
    FloatingActionButton fabMainHome;
    @BindView(R.id.rb_main_shanghai)
    RadioButton rbMainShanghai;
    @BindView(R.id.rb_main_hangzhou)
    RadioButton rbMainHandzhou;
    @BindView(R.id.rg_main_tab_top)
    RadioGroup rgMainTabTop;
    @BindView(R.id.rb_main_beijing)
    RadioButton rbMainBeijing;
    @BindView(R.id.rb_main_shenzhen)
    RadioButton rbMainShengzhen;
    @BindView(R.id.rg_main_tab_bottom)
    RadioGroup rgMainTabBottom;
    @BindView(R.id.ff_main_tab_top)
    FrameLayout ffMainTabTop;
    @BindView(R.id.fl_main_content)
    FrameLayout flMainContent;
    private boolean isChangeTopOrBottom;

    private IMainActivityContract.IPresenter iPresenter = new MainActivityPresenter(this);

    @Override
    public void afterBindView() {
        //初始化fragment
        initHomeFragment();

        changeAnima(rgMainTabTop, rgMainTabBottom);

        initCheckListener();
    }

    private void initCheckListener() {

        rbMainShanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbMainShanghai.getId() == iPresenter.getmCurrentCheckedId()){
                    return;
                }

                iPresenter.replaceFragment(ShangHai);
            }
        });

        rbMainHandzhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbMainHandzhou.getId() == iPresenter.getmCurrentCheckedId()){
                    return;
                }

                iPresenter.replaceFragment(HangZhou);
            }
        });

        rbMainBeijing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbMainBeijing.getId() == iPresenter.getmCurrentCheckedId()){
                    return;
                }

                iPresenter.replaceFragment(BeiJing);
            }
        });

        rbMainShengzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbMainShengzhen.getId() == iPresenter.getmCurrentCheckedId()){
                    return;
                }

                iPresenter.replaceFragment(ShenZhen);
            }
        });

    }

    //初始化
    private void initHomeFragment() {
        iPresenter.initHomeFragment();
    }

    @OnClick({R.id.fab_main_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_main_home:

                isChangeTopOrBottom = !isChangeTopOrBottom;

                if (isChangeTopOrBottom) {
                    changeAnima(rgMainTabTop, rgMainTabBottom);
                    handleTopPosition();
                } else {
                    changeAnima(rgMainTabBottom, rgMainTabTop);
                    handleBottomPosition();
                }
                break;

        }
    }

    private void handleTopPosition() {
        int topPosition = iPresenter.getTopPosition();
        iPresenter.replaceFragment(topPosition);
    }

    private void handleBottomPosition() {
        int bottomPosition = iPresenter.getBottomPosition();
        iPresenter.replaceFragment(bottomPosition);
    }


    void changeAnima(RadioGroup hide, RadioGroup show) {
        //清除自身动画
        hide.clearAnimation();
        Animation animationHide = AnimationUtils.loadAnimation(this, R.anim.main_tab_translate_hide);
        hide.startAnimation(animationHide);
        hide.setVisibility(View.GONE);

        show.clearAnimation();
        Animation animationShow = AnimationUtils.loadAnimation(this, R.anim.main_tab_translate_hide);
        show.startAnimation(animationHide);
        show.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().hide(mFragment).commit();
    }

    @Override
    public void addFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_content, mFragment).commit();
    }

    @Override
    public void showFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction().show(mFragment).commit();
    }

}
