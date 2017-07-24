package com.harlan.lhc.okgoharlan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.get)
    Button get;
    @Bind(R.id.post)
    Button post;
    @Bind(R.id.text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.get, R.id.post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get:
               OkGo.getInstance().<String>get("").execute(new StringCallback() {
                   @Override
                   public void onSuccess(Response<String> response) {

                   }
               });

                break;
            case R.id.post:
                break;
        }
    }
}
