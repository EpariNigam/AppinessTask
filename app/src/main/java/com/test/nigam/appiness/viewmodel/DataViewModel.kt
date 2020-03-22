package com.test.nigam.appiness.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.nigam.appiness.model.Item
import com.test.nigam.appiness.service.ApiService
import com.test.nigam.appiness.service.RetrofitUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class DataViewModel : ViewModel() {
    val liveData = MutableLiveData<List<Item>>()
    var dataList = listOf<Item>()

    @SuppressLint("CheckResult")
    fun getData() {
        val observable = RetrofitUtil.retrofit
            .create(ApiService::class.java).getResponse()
            .flatMapIterable { iterable -> iterable }
            .toSortedList { o1, o2 -> o1.title.compareTo(o2.title) }
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResults, this::handleError)
    }

    @SuppressLint("CheckResult")
    fun search(key: String) {
        Observable.fromArray(dataList).flatMapIterable { iterable -> iterable }
            .filter { t ->
                t.title.toLowerCase(Locale.getDefault())
                    .contains(key.toLowerCase(Locale.getDefault()))
            }
            .toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                liveData.value = list
            }
    }

    private fun handleResults(dataList: List<Item>?) {
        this.dataList = dataList!!
        liveData.value = dataList
    }

    private fun handleError(t: Throwable) {

    }
}