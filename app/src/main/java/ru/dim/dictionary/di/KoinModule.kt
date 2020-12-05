package ru.dim.dictionary.di

import androidx.room.Room
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dim.dictionary.interactor.DataInteractor
import ru.dim.dictionary.interactor.IDataInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.datasource.database.*
import ru.dim.dictionary.model.datasource.server.RetrofitProvider
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.repository.RepositoryImplementation
import ru.dim.dictionary.viewmodel.DescriptionViewModel
import ru.dim.dictionary.viewmodel.HistoryViewModel
import ru.dim.dictionary.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DescriptionViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
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