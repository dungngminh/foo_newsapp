package studio.komkat.android_newsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import studio.komkat.android_newsapp.Locator
import studio.komkat.android_newsapp.R
import studio.komkat.android_newsapp.Result

class HeadlinesFragment : Fragment() {
    private val onStopCompositeDisposable = CompositeDisposable()

    private val vm: HeadlinesVM by viewModels<HeadlinesVM>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return if (modelClass == HeadlinesVM::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    HeadlinesVM(articleRepository = Locator.provideArticleRepository())  as T
                } else {
                    throw IllegalArgumentException("Unknown class $modelClass")
                }
            }
        }
    })

    // Access on Main thread
    private val homeAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeAdapter(
            onPressed = {
                findNavController().navigate(
                    HeadlinesFragmentDirections.actionHeadlinesToArticle(
                        title = it.title ?: ""
                    )
                )
                // navigate to ...
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headlines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<RecyclerView>(R.id.recyclerView).run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        vm.getTopHeadlines()
    }

    override fun onStart() {
        super.onStart()

        vm.articlesResultObservable
            .subscribeBy(
                onNext =  { result ->
                    when(result){
                        is Result.Error -> {
                            // show error
                            // hide loading
                        }
                        Result.Loading -> {
                            // show loading
                            // hide error
                        }
                        is Result.Success -> {
                            // hide loading, hide error
                            // show data
                            homeAdapter.submitList(result.data)
                        }
                    }
                }
            )
            .addTo(onStopCompositeDisposable)
    }

    override fun onStop() {
        super.onStop()
        onStopCompositeDisposable.dispose()
    }
}