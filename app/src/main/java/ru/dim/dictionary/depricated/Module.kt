package ru.dim.dictionary.depricated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.dim.dictionary.di.LOCAL_NAME
import ru.dim.dictionary.di.REMOTE_NAME
import ru.dim.dictionary.interactor.MainInteractor
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.datasource.database.RoomProvider
import ru.dim.dictionary.model.datasource.server.RetrofitProvider
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.repository.RepositoryImplementation
import ru.dim.dictionary.view.MainActivity
import ru.dim.dictionary.viewmodel.MainViewModel
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}

@Module(includes = [InteractorModule::class])
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}

@Module
class InteractorModule {
    @Provides
    fun provideMainInteractor(@Named(REMOTE_NAME) remoteRepository: IRepository<List<SearchResult>>,
                              @Named(LOCAL_NAME) localRepository: IRepository<List<SearchResult>>) =
        MainInteractor(remoteRepository, localRepository)
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