package mx.irisoft.pruebatecniva.presentation.main.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.irisoft.pruebatecniva.databinding.ItemMovieBinding
import mx.irisoft.pruebatecniva.domain.models.PopularMovieItemModel
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.updateImage

class MoviesAdapter (
    private val list: ArrayList<PopularMovieItemModel>,
    private val onTouchItem: (path: String) -> Unit,
    private val onFavoriteClick: (PopularMovieItemModel) -> Unit
): RecyclerView.Adapter<MoviesAdapter.ResultItem>() {

    inner class ResultItem(private val pictureBinding: ItemMovieBinding) : RecyclerView.ViewHolder(pictureBinding.root) {
        fun setData(item: PopularMovieItemModel){
            with(pictureBinding){
                itemActvName.text = item.title
                itemActvOriginalTitle.text = item.originalTitle
                itemActvDescription.text = item.description
                itemActvReleaseDate.text = item.releaseDate
                itemAcivBanner.updateImage(item.imagePath)
                itemAcivBanner.setOnClickListener { onTouchItem(item.imagePath) }
                itemChipSeries.setOnClickListener { onFavoriteClick(item) }

                executePendingBindings()
            }
        }
    }

    fun addItemsAfter(list: List<PopularMovieItemModel>) {
        var rest = if (this.list.size <= 1) 0 else this.list.size
        repeat(list.size) {
            this.list.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItem =
        ResultItem(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: ResultItem, position: Int) =
        holder.setData(list[position])
}