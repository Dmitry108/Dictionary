package ru.dim.dictionary.di

import dagger.Module
import dagger.Provides
import ru.dim.dictionary.interactor.IInteractor
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.datasource.database.RoomProvider
import ru.dim.dictionary.model.datasource.server.RetrofitProvider
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

const val REMOTE_NAME = "remote"
const val LOCAL_NAME = "local"

@Module
class InteractorModule {
    fun provideMainInteractor(@Named(REMOTE_NAME) remoteRepository: IRepository<List<SearchResult>>,
                              @Named(LOCAL_NAME) localRepository: IRepository<List<SearchResult>>
    ): IInteractor<ViewState> = MainInteractor(remoteRepository, localRepository)
}

@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Named(REMOTE_NAME)
    fun provideRemoteRepository(@Named(REMOTE_NAME) remoteDataSource: IDataSource<List<SearchResult>>
    ): IRepository<List<SearchResult>> = RepositoryImplementation(remoteDataSource)

    @Provides
    @Singleton
    @Named(REMOTE_NAME)
    fun provideDataSourceRemote(): IDataSource<List<SearchResult>> = RetrofitProvider()

    @Provides
    @Singleton
    @Named(LOCAL_NAME)
    fun provideLocalRepository(@Named(LOCAL_NAME) localDataSource: IDataSource<List<SearchResult>>
    ): IRepository<List<SearchResult>> = RepositoryImplementation(localDataSource)

    @Provides
    @Singleton
    @Named(LOCAL_NAME)
    fun provideDataSourceLocal(): IDataSource<List<SearchResult>> = RoomProvider()
}