package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.irisoft.pruebatecniva.databinding.ItemMovieFavoriteBinding
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.updateImage
import mx.irisoft.pruebatecniva.utils.tools.CalendarUtils

class MoviesFavoritesAdapter(
    private val onTouchItem: (path: String) -> Unit,
    private val onDeleteClick: (MovieModel) -> Unit,
) : ListAdapter<MovieModel, MoviesFavoritesAdapter.ResultItem>(MovieModelDiffCallback()) {

    inner class ResultItem(private val pictureBinding: ItemMovieFavoriteBinding) :
        RecyclerView.ViewHolder(pictureBinding.root) {
        fun bind(item: MovieModel) {
            with(pictureBinding) {
                itemActvName.text = item.title
                itemActvOriginalTitle.text = item.author
                itemActvDescription.text = item.content
                itemActvReleaseDate.text = CalendarUtils().formatLocal(item.date)
                itemAcivBanner.updateImage(item.imagePath)
                itemAcivBanner.setOnClickListener { onTouchItem(item.imagePath) }
                itemChipSeries.setOnClickListener {
                    onDeleteClick(item)
                    // La lista se actualiza automáticamente con ListAdapter
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItem =
        ResultItem(
            ItemMovieFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(holder: ResultItem, position: Int) {
        holder.bind(getItem(position))
    }

    // Clase utilitaria para comparar elementos de la lista
    private class MovieModelDiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
            oldItem == newItem
    }
}
