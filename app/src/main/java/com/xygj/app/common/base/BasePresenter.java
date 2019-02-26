package com.xygj.app.common.base;

import android.util.Log;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuyougen on 2018/4/11.
 */

public class BasePresenter<V extends BaseView> {
    private WeakReference<V> mWeakReference;
    private CompositeDisposable mCompositeDisposable;
    protected V mView;

    public void attach(V v) {
        if (mWeakReference == null) {
            mWeakReference = new WeakReference<V>(v);
        }
        mView = v;
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    public <T> void addTask(Observable<T> observable, Consumer<T> consumer) {
        mCompositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e("http", "throwable: " + throwable.getMessage());
                                V v = mWeakReference.get();
                                if (v != null) {
                                    v.onNetworkConnectFailed();
                                }
                            }
                        }));
    }

    public void detach() {
        mWeakReference = null;
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
    }

    public V getView() {
        mView = mWeakReference.get();
        return mView;
    }
}
