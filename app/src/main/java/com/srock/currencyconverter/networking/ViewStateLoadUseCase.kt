package com.srock.currencyconverter.networking

import com.srock.currencyconverter.data.CurrencyExchange
import com.srock.currencyconverter.data.ViewState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ViewStateLoadUseCase(private var repository: CurrencyRepository)  {

    fun loadViewState(inputSubject: BehaviorSubject<Float>, currencyName: String, scrollNeeded: Boolean): Observable<ViewState>{
        return Observable.combineLatest(
                    repository.getCurrenciesUpdates(currencyName)
                        .map { CurrencyExchange.Factory.transformCurrency(it) },
                    inputSubject,
                    BiFunction { exchanges: List<CurrencyExchange>, input: Float ->
                    exchanges.map { exchange -> CurrencyExchange(exchange.key, exchange.rate * input) }
                })
                .zipWith(
                    Observable.range(0, 10000),
                    BiFunction { currencies: List<CurrencyExchange>, index: Int ->
                        ViewState(currencies, index == 0 && scrollNeeded)
                    }
                )
    }
}