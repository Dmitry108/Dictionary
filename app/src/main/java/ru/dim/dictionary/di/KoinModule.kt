package ru.dim.dictionary.di

import android.util.Log
import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dim.core.interactor.DataInteractor
import ru.dim.core.interactor.IDataInteractor
import ru.dim.dictionary.app.DictionaryApp
import ru.dim.dictionary.view.description.DescriptionActivity
import ru.dim.dictionary.view.main.MainActivity
import ru.dim.dictionary.viewmodel.DescriptionViewModel
import ru.dim.dictionary.viewmodel.MainViewModel
//import ru.dim.historyscreen.HistoryViewModel
import ru.dim.model.ViewState
import ru.dim.model.entity.SearchResult
import ru.dim.repository.IRepository
import ru.dim.repository.RepositoryImplementation
import ru.dim.repository.datasource.database.IRepositoryLocal
import ru.dim.repository.datasource.database.LocalDatabase
import ru.dim.repository.datasource.database.RepositoryLocal
import ru.dim.repository.datasource.database.RoomProvider
import ru.dim.repository.datasource.server.RetrofitProvider

fun injectDependencies() = loadModules

val loadModules by lazy {
    loadKoinModules(listOf(viewModelModule, repositoryModule, interactorModule))
}

val viewModelModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
    }
    scope(named<DescriptionActivity>()) {
        viewModel { DescriptionViewModel(get()) }
    }
}

val interactorModule = module {
//    single { DataInteractor(get(named(REMOTE_NAME)), get(named(LOCAL_NAME))) }
    single<IDataInteractor<ViewState>> { DataInteractor(get(), get()) }
}

val repositoryModule = module {
    single <IRepository<List<SearchResult>>> //(named(REMOTE_NAME))
        { RepositoryImplementation(RetrofitProvider()) }
    single <IRepositoryLocal<List<SearchResult>>> //(named(LOCAL_NAME))
        { RepositoryLocal(RoomProvider(get())) }
    single { Room.databaseBuilder(get(), LocalDatabase::class.java, "LocalDatabase").build() }
    single { get<LocalDatabase>().getLocalDAO() }
}