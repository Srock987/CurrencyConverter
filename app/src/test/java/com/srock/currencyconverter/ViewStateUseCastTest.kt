package com.srock.currencyconverter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.srock.currencyconverter.data.Currency
import com.srock.currencyconverter.networking.CurrencyRepository
import com.srock.currencyconverter.networking.ViewStateLoadUseCase
import com.srock.currencyconverter.viewmodels.CurrencyViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.*

class ViewStateUseCastTest {

    private val multiplier = 10.0f
    private val inputSubject = BehaviorSubject.createDefault(multiplier)

    inline fun <reified T> lambdaMock(): T = mock(T::class.java)

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Test
    fun testObtainingViewState(){

        val name = "EUR"
        val pair = ("GBP" to 2.0f)
        val repository = object : CurrencyRepository {
            override fun getCurrenciesUpdates(currencyName: String): Observable<Currency> {
                return Observable.just(Currency(name, Date(), linkedMapOf(pair)))
            }
        }

        val useCase = ViewStateLoadUseCase(repository)
        useCase.loadViewState(inputSubject,name,true)
            .subscribe({
                    assert(it.shouldScroll)
                    assert(it.currencyExchanges.size == 2)
                    assert(it.currencyExchanges.first().key==name)
                    assert(it.currencyExchanges.first().rate.equals(multiplier))
                    assert(it.currencyExchanges.last().key==pair.first)
                    assert(it.currencyExchanges.last().rate.equals(multiplier*pair.second))
                },
                {
                    it.printStackTrace()
                    assert(false)
                }
            )
    }

}
