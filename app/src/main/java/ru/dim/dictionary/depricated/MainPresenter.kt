package ru.dim.dictionary.depricated

//class MainPresenter<T: ViewState, V : IView>(
//    private val interactor: MainInteractor = MainInteractor(
//        RepositoryImplementation(DataSourceRemote()),
//        RepositoryImplementation(DataSourceLocal())),
//    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
//    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
//) : IPresenter<T, V> {
//
//    private var view: V? = null
//
//    override fun attachView(view: V) {
//        if (this.view != view) {
//            this.view = view
//        }
//    }
//
//    override fun detachView(view: V) {
//        compositeDisposable.clear()
//        if (this.view == view) {
//            this.view = null
//        }
//    }
//
//    override fun getData(word: String, isOnline: Boolean) {
//        compositeDisposable.add(
//            interactor.getData(word, isOnline)
//                .subscribeOn(schedulerProvider.io())
//                .observeOn(schedulerProvider.ui())
//                .doOnSubscribe { view?.renderData(ViewState.Loading(null)) }
//                .subscribeWith(getObserver())
//        )
//    }
//
//    private fun getObserver(): DisposableObserver<ViewState> =
//        object : DisposableObserver<ViewState>() {
//            override fun onNext(viewState: ViewState) {
//                view?.renderData(viewState)
//            }
//            override fun onError(e: Throwable) {
//                view?.renderData(ViewState.Error(e))
//            }
//            override fun onComplete() { }
//        }
//}