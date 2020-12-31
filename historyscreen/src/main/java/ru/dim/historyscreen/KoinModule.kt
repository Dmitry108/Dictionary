package ru.dim.historyscreen

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadHistoryModules

val loadHistoryModules by lazy {
    loadKoinModules(listOf(historyModule))
}

val historyModule = module {
    viewModel { HistoryViewModel(get()) }
}