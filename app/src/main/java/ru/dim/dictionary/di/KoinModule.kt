package ru.dim.dictionary.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.datasource.database.RoomProvider
import ru.dim.dictionary.model.datasource.server.RetrofitProvider
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.repository.RepositoryImplementation
import ru.dim.dictionary.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val interactorModule = module {
    factory { MainInteractor(get(named(REMOTE_NAME)), get(named(LOCAL_NAME))) }
}
val repositoryModule = module {
    single <IRepository<List<SearchResult>>> (named(REMOTE_NAME))
        { RepositoryImplementation(RetrofitProvider()) }
    single <IRepository<List<SearchResult>>> (named(LOCAL_NAME))
        { RepositoryImplementation(RoomProvider()) }
}