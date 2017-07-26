package com.harlan.lhc.okgoharlan.mvp.model.api;

import com.harlan.lhc.okgoharlan.Api.Urls;
import com.harlan.lhc.okgoharlan.okrx2.RxUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;

import io.reactivex.Observable;

/**
 * Created by a1 on 2017/7/26.
 */
public class modelApi {
    public  Observable<String> getString() {
        HttpHeaders headers = new HttpHeaders();

        HttpParams params = new HttpParams();


        return RxUtils.request(HttpMethod.GET, Urls.test, String.class, params, headers);
    }
}
