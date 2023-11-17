package mx.irisoft.pruebatecniva.presentation.main.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.irisoft.pruebatecniva.databinding.ItemImagesBinding
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.updateImage

class UploadImagesAdapter(
    private val listImages: ArrayList<String>,
    private val onTouch: (String) -> Unit,
) : RecyclerView.Adapter<UploadImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(urlImage: String) {
            with(binding) {
                aivImage.updateImage(urlImage)
                aivImage.setOnClickListener {
                    onTouch(urlImage)
                }
                executePendingBindings()
            }
        }
    }

    fun addItemsAfter(list: List<String>) {
        var rest = if (this.listImages.size <= 1) 0 else this.listImages.size
        repeat(list.size) {
            this.listImages.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =
        this.listImages.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.setData(listImages[position])
}