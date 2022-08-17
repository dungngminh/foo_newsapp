package studio.komkat.android_newsapp

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    object Loading : Result<Nothing>
}

fun <T: Any> Observable<T>.asResult(): Observable<Result<T>> = this
    .map<Result<T>> {
        Result.Success(it)
    }
    .startWithItem(Result.Loading)
    .onErrorReturn { Result.Error(it) }

fun <T: Any> Single<T>.asResult(): Observable<Result<T>> = toObservable().asResult()
fun <T: Any> Maybe<T>.asResult(): Observable<Result<T>> = toObservable().asResult()