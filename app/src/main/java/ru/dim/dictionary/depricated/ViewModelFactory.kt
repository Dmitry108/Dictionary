package ru.dim.dictionary.depricated
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import java.lang.Exception
//import java.lang.IllegalArgumentException
//import javax.inject.Inject
//import javax.inject.Provider
//import javax.inject.Singleton
//
//@Singleton
//class ViewModelFactory @Inject constructor(
//    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
//): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        val viewModelCreator =
//            viewModels[modelClass]
//                ?: viewModels.asIterable().firstOrNull {predicate ->
//                    modelClass.isAssignableFrom(predicate.key)
//                }?.value
//                ?: throw IllegalArgumentException("неизвестный класс $modelClass")
//        return try {
//                viewModelCreator.get() as T
//            } catch (e: Exception) {
//                throw Exception(e)
//            }
//    }
//}