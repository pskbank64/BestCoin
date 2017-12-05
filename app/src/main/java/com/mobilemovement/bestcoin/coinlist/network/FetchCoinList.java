package com.mobilemovement.bestcoin.coinlist.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobilemovement.bestcoin.BuildConfig;
import com.mobilemovement.bestcoin.coinlist.adapter.CoinListAdapter;
import com.mobilemovement.bestcoin.coinlist.model.CoinListDataModel;
import com.mobilemovement.bestcoin.coinlist.model.CoinListUpperModel;
import com.mobilemovement.bestcoin.network.BestCoinAPI;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nuhkoca on 4.12.2017.
 */

public class FetchCoinList {

    public static void fetchCoins(final CoinListAdapter coinListAdapter) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BestCoinAPI bestCoinAPI = retrofit.create(BestCoinAPI.class);

        Observable<CoinListUpperModel> getCoinList = bestCoinAPI.loadCoins();

        getCoinList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CoinListUpperModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CoinListUpperModel coinListUpperModel) {
                        coinListAdapter.updateRecyclerview(coinListUpperModel.getCoinListDataModels());
                    }
                });
    }
}