package studio.komkat.android_newsapp.ui.main

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import studio.komkat.android_newsapp.Result
import studio.komkat.android_newsapp.asResult
import studio.komkat.android_newsapp.domain.model.Article
import studio.komkat.android_newsapp.domain.repository.ArticleRepository

class HeadlinesVM(
  private val articleRepository: ArticleRepository,
) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  /**
   * input
   */
  private val getTopHeadlinesActionRelay: PublishRelay<Unit> = PublishRelay.create()

  /**
   * output
   */
  private val articlesResultRelay: BehaviorRelay<Result<List<Article>>> =
    BehaviorRelay.createDefault(Result.Loading)

  /**
   * replay 1
   */
  val articlesResultObservable: Observable<Result<List<Article>>> = articlesResultRelay.hide()

  init {
    getTopHeadlinesActionRelay
      .filter {
        val result = articlesResultRelay.value!!
        result is Result.Error || result == Result.Loading
      }
      .flatMap {
        articleRepository
          .getTopHeadlines()
          .asResult()
      }
      .subscribeBy(onNext = articlesResultRelay::accept)
      .addTo(compositeDisposable)

    Observable.fromCallable { Math.random() }
      .subscribeBy {

      }
      .addTo(compositeDisposable)
  }

  fun getTopHeadlines() {
    getTopHeadlinesActionRelay.accept(Unit)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
  }
}