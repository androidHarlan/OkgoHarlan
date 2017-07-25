package com.harlan.lhc.okgoharlan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.harlan.lhc.okgoharlan.Api.Urls;
import com.harlan.lhc.okgoharlan.Imagerloader.GlideImageLoader;
import com.harlan.lhc.okgoharlan.callback.DialogCallback;
import com.harlan.lhc.okgoharlan.moder.LhcResponse;
import com.harlan.lhc.okgoharlan.moder.ServerModel;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx.adapter.ObservableResponse;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.Observer;

import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Bind(R.id.get)
    Button get;
    @Bind(R.id.postJson)
    Button postJson;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.clear)
    Button clear;
    @Bind(R.id.Upload)
    Button Upload;
    @Bind(R.id.upBytes)
    Button upBytes;
    @Bind(R.id.upString)
    Button upString;
    @Bind(R.id.fileDownload)
    Button fileDownload;
    @Bind(R.id.post)
    Button post;
    @Bind(R.id.cacheokrx2)
    Button cacheokrx2;
    private ArrayList<ImageItem> imageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

    @OnClick({R.id.cacheokrx2,R.id.post, R.id.fileDownload, R.id.upString, R.id.upBytes, R.id.Upload, R.id.clear, R.id.get, R.id.postJson})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cacheokrx2:

                break;
            case R.id.post:
                OkGo.<LhcResponse<ServerModel>>post(Urls.URL_METHOD)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
                        .params("param1", "paramValue1")//
                        .params("param2", "paramValue2")//
                        .params("param3", "paramValue3")//

                        .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                        .execute(new DialogCallback<LhcResponse<ServerModel>>(this) {
                            @Override
                            public void onSuccess(Response<LhcResponse<ServerModel>> response) {
                                text.setText("post成功");
                            }


                            @Override
                            public void onError(Response<LhcResponse<ServerModel>> response) {
                                text.setText("post失败");
                            }
                        });
                break;
            case R.id.get:
                OkGo.getInstance().setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE).setRetryCount(3).setCacheTime(100000).<String>get(Urls.URL_JSONOBJECT)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                text.setText("网络数据：" + response.body());
                            }

                            @Override
                            public void onError(Response<String> response) {
                                response.getException().printStackTrace();
                            }

                            @Override
                            public void onCacheSuccess(Response<String> response) {
                                text.setText("缓存数据：" + response.body());

                                super.onCacheSuccess(response);
                            }

                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                showLoading();
                                super.onStart(request);
                            }

                            @Override
                            public void onFinish() {
                                dismissLoading();
                                super.onFinish();
                            }
                        });
                break;
            case R.id.postJson:
                HashMap<String, String> params = new HashMap<>();
                params.put("key1", "value1");
                params.put("key2", "这里是需要提交的json格式数据");
                params.put("key3", "也可以使用三方工具将对象转成json字符串");
                params.put("key4", "其实你怎么高兴怎么写都行");
                JSONObject jsonObject = new JSONObject(params);
                OkGo.<LhcResponse<ServerModel>>post(Urls.URL_TEXT_UPLOAD)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//  这里不要使用params，upJson 与 params 是互斥的，只有 upJson 的数据会被上传
                        .upJson(jsonObject)//
                        .execute(new DialogCallback<LhcResponse<ServerModel>>(this) {
                            @Override
                            public void onSuccess(Response<LhcResponse<ServerModel>> response) {
                                text.setText("提交json成功");

                            }

                            @Override
                            public void onError(Response<LhcResponse<ServerModel>> response) {
                                text.setText("提交json失败");
                            }
                        });
                break;
            case R.id.Upload:
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());
                imagePicker.setMultiMode(true);   //多选
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setSelectLimit(9);    //最多选择9张
                imagePicker.setCrop(false);       //不进行裁剪
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.upBytes:
                OkGo.<LhcResponse<ServerModel>>post(Urls.URL_TEXT_UPLOAD)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")// 这里不要使用params，upBytes 与 params 是互斥的，只有 upBytes 的数据会被上传
                        .upBytes("这是字节数据".getBytes())//
                        .execute(new DialogCallback<LhcResponse<ServerModel>>(this) {
                            @Override
                            public void onSuccess(Response<LhcResponse<ServerModel>> response) {

                            }

                            @Override
                            public void onError(Response<LhcResponse<ServerModel>> response) {

                            }
                        });
                break;
            case R.id.upString:
                OkGo.<LhcResponse<ServerModel>>post(Urls.URL_TEXT_UPLOAD)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")// 这里不要使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
                        .upString("这是要上传的长文本数据！")//
//                .upString("这是要上传的长文本数据！", MediaType.parse("application/xml"))// 比如上传xml数据，这里就可以自己指定请求头
                        .execute(new DialogCallback<LhcResponse<ServerModel>>(this) {
                            @Override
                            public void onSuccess(Response<LhcResponse<ServerModel>> response) {

                            }

                            @Override
                            public void onError(Response<LhcResponse<ServerModel>> response) {

                            }
                        });
                break;
            case R.id.clear:
                text.setText("");
                break;
            case R.id.fileDownload:
                OkGo.<File>get(Urls.URL_DOWNLOAD)//
                        .tag(this)//

                        .execute(new FileCallback("OkGo.apk") {

                            @Override
                            public void onStart(Request<File, ? extends Request> request) {
                                text.setText("正在下载中");
                            }

                            @Override
                            public void onSuccess(Response<File> response) {
                                text.setText("下载完成");
                            }

                            @Override
                            public void onError(Response<File> response) {
                                text.setText("下载出错");
                            }

                            @Override
                            public void downloadProgress(Progress progress) {
                                text.setText("下载进度：" + progress);
                              /*  String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                                String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                                tvDownloadSize.setText(downloadLength + "/" + totalLength);
                                String speed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                                tvNetSpeed.setText(String.format("%s/s", speed));
                                tvProgress.setText(numberFormat.format(progress.fraction));
                                pbProgress.setMax(10000);
                                pbProgress.setProgress((int) (progress.fraction * 10000));*/
                            }
                        });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.e("backinfo", "imageItems:" + imageItems.size());
                if (imageItems != null && imageItems.size() > 0) {
                    upload();

                }
            }
        }
    }

    private void upload() {
        ArrayList<File> files = new ArrayList<>();
        if (imageItems != null && imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                files.add(new File(imageItems.get(i).path));
            }
        }
        //拼接参数
        OkGo.<LhcResponse<ServerModel>>post(Urls.URL_FORM_UPLOAD)//
                .tag(this)//
                .headers("header1", "headerValue1")//
                .headers("header2", "headerValue2")//
                .params("param1", "paramValue1")//
                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
                        //  .upFile(new File(imageItem.path))
                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                .execute(new DialogCallback<LhcResponse<ServerModel>>(this) {


                    @Override
                    public void onSuccess(Response<LhcResponse<ServerModel>> response) {
                        text.setText("上传成功");
                    }

                    @Override
                    public void onError(Response<LhcResponse<ServerModel>> response) {
                        text.setText("上传失败");
                        super.onError(response);
                    }
                });

    }
}
