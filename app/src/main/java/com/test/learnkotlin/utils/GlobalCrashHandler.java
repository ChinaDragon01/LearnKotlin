package com.test.learnkotlin.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GlobalCrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void registerContext(Context context) {
        mContext = context;
    }

    public GlobalCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static GlobalCrashHandler getInstance() {
        return GlobalCrashHandlerHolder.sGlobalCrashHandler;
    }

    static class GlobalCrashHandlerHolder {
        private static GlobalCrashHandler sGlobalCrashHandler = new GlobalCrashHandler();
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        String crashInfo = caughtException(t, e);
//        FailEntry failEntry = new FailEntry();
//        failEntry.setOriginMsg(crashInfo);
//        failEntry.setUrl(t.getName());
//        normalLog(failEntry);
        LogUitls.INSTANCE.i(crashInfo);
    }

    /**
     * @param throwable
     * @return
     */
    private static String caughtException(Thread t, Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();

        return mStringWriter.toString();
    }

//    private void normalLog(FailEntry failEntry) {
//
//        Map<String, Object> request = HttpManager.publicParam();
//        if (CacheData.getInstance().getLoginBean() != null) {
//            request.put("token", CacheData.getInstance().getLoginBean().getToken());
//            request.put("phone", CacheData.getInstance().getLoginBean().getRegisterPhone());
//        }
//        request.put("msg", failEntry.getOriginMsg());
//        request.put("url", failEntry.getUrl());
//        Disposable disposable = wrapObservable(HttpManager.createApi().normalLog(request))
//                .subscribeWith(new BaseObserver<Object>() {
//                    @Override
//                    protected void onSuccess(Object data, String msg) {
//                    }
//
//                    @Override
//                    protected boolean onFail(FailEntry failEntry) {
//                        return true;
//                    }
//                });
//    }
//
//    private <T> Observable<T> wrapObservable(Observable<T> observable) {
//        return observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
//    }
}
