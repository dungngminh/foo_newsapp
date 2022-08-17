package studio.komkat.android_newsapp

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface RxSchedulerProvider {
  val main: Scheduler
  val io: Scheduler
}

class DefaultRxSchedulerProvider : RxSchedulerProvider {
  override val main: Scheduler
    get() = AndroidSchedulers.mainThread()

  override val io: Scheduler
    get() = Schedulers.io()
}

class TestRxSchedulerProvider : RxSchedulerProvider {
  override val main: Scheduler
    get() = Schedulers.trampoline()

  override val io: Scheduler
    get() = Schedulers.trampoline()
}