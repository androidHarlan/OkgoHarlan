package com.harlan.lhc.okgoharlan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.harlan.lhc.okgoharlan.mvp.presenter.MvpPresenter;
import com.harlan.lhc.okgoharlan.mvp.view.MVPview;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MVP extends BaseActivity implements MVPview {
    MvpPresenter mvpPresenter;
    @Bind(R.id.textview)
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
        mvpPresenter = new MvpPresenter(this);
        mvpPresenter.init();
    }

    @Override
    public void onSuccess(String str) {
        textview.setText(str);

    }

    @Override
    public void onError(Throwable response) {

    }

    @Override
    public void onComplete() {
        dismissLoading();

    }

    @Override
    public void onRefresh(String str) {

    }

    @Override
    public void onStar() {
        showLoading();
    }

    @Override
    public void onRefreshLoadMore(String str) {

    }
}
