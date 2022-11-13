package ru.osa.gb.homework.movless02.ui.main

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.osa.gb.homework.movless02.R
import ru.osa.gb.homework.movless02.model.Movie


class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var movieData: List<Movie> = listOf()
    fun setMovieList(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_movies_list_item, parent, false) as
                    View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.moviesListItemTitle).text = movie.title
            itemView.findViewById<TextView>(R.id.moviesListItemDescription).text = movie.description
            var img : ImageView = itemView.findViewById(R.id.moviesListItemImage)

            // На будущее разобраться как правильно загружать картинки по url
//            Glide.with(itemView)
//                .load(movie.image)
//                .into(img)
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    movie.title,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
