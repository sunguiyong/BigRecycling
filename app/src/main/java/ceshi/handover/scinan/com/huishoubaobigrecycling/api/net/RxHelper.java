package ceshi.handover.scinan.com.huishoubaobigrecycling.api.net;


import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import ceshi.handover.scinan.com.huishoubaobigrecycling.base.BaseFragment;
import ceshi.handover.scinan.com.huishoubaobigrecycling.utils.DialogHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


public class RxHelper<T> {
    private String mMessage;

    public RxHelper(String message) {
        mMessage = message;
    }

    //子线程运行，主线程回调
    public Observable.Transformer<T, T> io_main(final RxAppCompatActivity context) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                Observable<T> observable = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                DialogHelper.showProgressDlg(context, mMessage);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), ActivityEvent.STOP));
                return observable;
            }
        };
    }

    public Observable.Transformer<T, T> io_no_main(final RxAppCompatActivity context) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                Observable<T> observable = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {

                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), ActivityEvent.STOP));
                return observable;
            }
        };
    }

    public Observable.Transformer<T, T> io_fragment_main(final BaseFragment context) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                Observable<T> observable = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {

                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.bindUntilEvent(context.lifecycle(), FragmentEvent.DESTROY));
                return observable;
            }
        };
    }

    public Observable.Transformer<T, T> io_toay_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                Observable<T> observable = (Observable<T>) tObservable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {

                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());

                return observable;
            }
        };
    }

}
