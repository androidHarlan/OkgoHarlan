package com.harlan.lhc.okgoharlan.mvp.presenter;

import com.harlan.lhc.okgoharlan.mvp.model.api.modelApi;
import com.harlan.lhc.okgoharlan.mvp.view.MVPview;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by a1 on 2017/7/26.
 */
public class MvpPresenter {
    MVPview mvPview;
    modelApi modelApi;

    public MvpPresenter(MVPview mvPview) {
        this.mvPview = mvPview;
        modelApi = new modelApi();
    }

    public void init() {
        modelApi.getString().subscribeOn(Schedulers.io())//

                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mvPview.onStar();
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())  //
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }


                    @Override
                    public void onNext(@NonNull String s) {
                        mvPview.onSuccess(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mvPview.onError(e);
                        e.printStackTrace();            //请求失败


                    }

                    @Override
                    public void onComplete() {
                        mvPview.onComplete();
                    }


                });
    }
}
