package com.harlan.lhc.okgoharlan.mvp.view;

import com.lzy.okgo.model.Response;

/**
 * Created by a1 on 2017/7/26.
 */
public interface MVPview {
    void onSuccess(String str);

    void onError(Throwable response);

    void onComplete();

    void onRefresh(String str);
    void onStar();

    void onRefreshLoadMore(String str);
}
