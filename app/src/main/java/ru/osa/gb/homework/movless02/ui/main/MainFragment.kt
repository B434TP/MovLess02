import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_main.*
import ru.osa.gb.homework.movless02.AppState
import ru.osa.gb.homework.movless02.MovieDetailFragment
import ru.osa.gb.homework.movless02.R
import ru.osa.gb.homework.movless02.databinding.FragmentMainBinding
import ru.osa.gb.homework.movless02.model.Movie
import ru.osa.gb.homework.movless02.ui.main.MainFragmentAdapter
import ru.osa.gb.homework.movless02.ui.main.MainViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val adapter = MainFragmentAdapter(
        object : OnItemViewClickListener {
            override fun onItemViewClick(movie: Movie) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(MovieDetailFragment.BUNDLE_EXTRA, movie)
                    manager.beginTransaction()
                        .add(R.id.container, MovieDetailFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        val searchText : String = binding.mainFragmentSearchText.text.toString()
        viewModel.getMoviesFromInternetSource(searchText)

        binding.searchButton.setOnClickListener {
            val searchText : String = binding.mainFragmentSearchText.text.toString()
            viewModel.getMoviesFromInternetSource(searchText)
        }

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setMovieList(appState.moviesData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Log.e("ERR", "LIST LOADING ERROR in renderData from AppState.Error")
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    companion object {
        fun newInstance() =
            MainFragment()
    }
}
