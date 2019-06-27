package com.srock.currencyconverter.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srock.currencyconverter.data.*
import com.srock.currencyconverter.networking.CurrencyRepository
import com.srock.currencyconverter.networking.ViewStateLoadUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CurrencyViewModel : BaseViewModel() {

    private lateinit var subscription: Disposable

    @Inject lateinit var viewStateLoadUseCase: ViewStateLoadUseCase

    private var inputSubject = BehaviorSubject.createDefault(100.0f)

    private val currencyValue: MutableLiveData<ViewState> by lazy {
        MutableLiveData<ViewState>().also {
            loadViewState(Values.STARTING_CURRENCY,false)
        }
    }

    fun getViewState() : LiveData<ViewState>{
        return currencyValue
    }

    fun inputChanged(inputString: String){
        val newInputAmount = if (inputString.isEmpty()) 0.0f else inputString.toFloat()
        inputSubject.onNext(newInputAmount)
    }

    fun changeCurrency(currencyName: String){
        subscription.dispose()
        loadViewState(currencyName,true)
    }


    private fun loadViewState(currencyName: String, scrollNeeded: Boolean) {
        subscription = viewStateLoadUseCase.loadViewState(inputSubject,currencyName,scrollNeeded)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onRetrieveViewStateSuccess(it) },
                { onRetrieveViewStateError() }
            )
    }

    private fun onRetrieveViewStateSuccess(viewState: ViewState){
        currencyValue.value = viewState
    }

    private fun onRetrieveViewStateError(){
        Log.d("CurrencyViewModel","Error on receiving viewState")
    }

    fun updateContinuously(){

    }

    fun stopContinuousUpdates(){

    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}