package mx.irisoft.pruebatecniva.presentation.main.moviesfavorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.irisoft.pruebatecniva.databinding.ItemMovieFavoriteBinding
import mx.irisoft.pruebatecniva.domain.models.MovieModel
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.updateImage
import mx.irisoft.pruebatecniva.utils.tools.CalendarUtils

class MoviesFavoritesAdapter(
    private val list: ArrayList<MovieModel>,
    private val onTouchItem: (path: String) -> Unit,
    private val onDeleteClick: (MovieModel) -> Unit,
) : RecyclerView.Adapter<MoviesFavoritesAdapter.ResultItem>() {

    inner class ResultItem(private val pictureBinding: ItemMovieFavoriteBinding) : RecyclerView.ViewHolder(pictureBinding.root) {
        fun setData(item: MovieModel, position: Int) {
            with(pictureBinding) {
                itemActvName.text = item.title
                itemActvOriginalTitle.text = item.author
                itemActvDescription.text = item.content
                itemActvReleaseDate.text = CalendarUtils().formatLocal(item.date)
                itemAcivBanner.updateImage(item.imagePath)
                itemAcivBanner.setOnClickListener { onTouchItem(item.imagePath) }
                itemChipSeries.setOnClickListener {
                    onDeleteClick(item)
                    list.remove(item)
                    notifyItemRemoved(position)
                }

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItem =
        ResultItem(ItemMovieFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: ResultItem, position: Int) =
        holder.setData(list[position], position)
}