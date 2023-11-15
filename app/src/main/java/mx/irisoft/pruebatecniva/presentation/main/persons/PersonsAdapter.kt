package mx.irisoft.pruebatecniva.presentation.main.persons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.irisoft.pruebatecniva.BuildConfig
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.databinding.ItemPeopleBinding
import mx.irisoft.pruebatecniva.domain.models.KnowForModel
import mx.irisoft.pruebatecniva.domain.models.PopularPersonModel
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.createChip
import mx.irisoft.pruebatecniva.utils.extensions.Extensions.updateImage

class PersonsAdapter (
    private val context: Context,
    private val arrayList: ArrayList<PopularPersonModel>,
    private val onTouchElement: (String) -> Unit,
    private val onChipClicked: (KnowForModel) -> Unit
): RecyclerView.Adapter<PersonsAdapter.PersonItem>() {


    inner class PersonItem( private val binding: ItemPeopleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: PopularPersonModel) {
            with(binding) {
                itemActvName.text = item.name
                itemActvOriginalTitle.text = item.knownForDepartment
                itemActvDescription.text = context.getString(if (item.gender != 1) R.string.male else R.string.female)
                itemActvReleaseDate.text = item.popularity.toString()
                val path = "${BuildConfig.URL_IMGS}${item.profilePath}"
                itemAcivBanner.updateImage(path)
                itemAcivBanner.setOnClickListener { onTouchElement(path) }
                item.knownFor.forEach { item ->
                    itemCgGroup.createChip(LayoutInflater.from(context), item.title ?: context.getString(R.string.no_data), item){
                        onChipClicked(it)
                    }
                }

                executePendingBindings()
            }
        }
    }

    fun addItemsAfter(list: List<PopularPersonModel>) {
        var rest = if (this.arrayList.size <= 1) 0 else this.arrayList.size
        repeat(list.size) {
            this.arrayList.add(rest, list[it])
            notifyItemInserted(rest)
            rest++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonItem =
        PersonItem(ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int =
        this.arrayList.size

    override fun onBindViewHolder(holder: PersonItem, position: Int) =
        holder.bindData(arrayList[position])
}