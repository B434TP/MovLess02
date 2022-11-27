package ru.osa.gb.homework.movless02

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.osa.gb.homework.movless02.databinding.FragmentMovieDetailBinding
import ru.osa.gb.homework.movless02.model.Movie
import ru.osa.gb.homework.movless02.model.MovieFullData
import ru.osa.gb.homework.movless02.model.RepositoryInternetImpl
import ru.osa.gb.homework.movless02.model.RepositoryLocalImpl

class MovieDetailFragment : Fragment() {


    private var _binding: FragmentMovieDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movie != null) {
            binding.detailViewTitle.text = movie.title
            binding.detailViewId.text = movie.id

            val fullData: MovieFullData = getFullData(movie.id)

            binding.detailViewDescription.text = fullData.plot
            binding.detailViewRating.text = fullData.imDbRating
            binding.detailViewImage
            Glide.with(this)
                .load(movie.image)
                .into(binding.detailViewImage)
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): MovieDetailFragment {
            val fragment = MovieDetailFragment()

            fragment.arguments = bundle
            return fragment
        }
    }

    private fun getFullData(id: String): MovieFullData {
        val rep = RepositoryInternetImpl()
        return rep.getMovieById(id)
    }

}