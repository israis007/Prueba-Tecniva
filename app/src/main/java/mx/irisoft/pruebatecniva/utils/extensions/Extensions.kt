package mx.irisoft.pruebatecniva.utils.extensions

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.presentation.base.SimpleDividerItemDecoration

object Extensions {

    private const val TAG = "Extensions"
    private var isScrolling = false

    fun <T> ChipGroup.createChip(layoutInflater: LayoutInflater, textChip: String?, data: T, clicked: (data: T) -> Unit){
        val chip = layoutInflater.inflate(R.layout.chip_item_inversed, null, false) as Chip
        chip.apply {
            id = View.generateViewId()
            text = textChip
            setOnClickListener {
                clicked(data)
            }
        }
        this.addView(chip)
    }

    fun AppCompatImageView.updateImage(path: String) {
        try {
            CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
                Glide.with(this@updateImage)
                    .load(path)
                    .centerCrop()
                    .placeholder(R.drawable.ic_sand_clock)
                    .error(R.drawable.ic_no_photo)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(this@updateImage)
            }
        } catch (e: Exception) {
            Log.d(TAG, e.stackTraceToString())
        }
    }

    fun RecyclerView.setUpScrollingListener(context: Context, adapter: RecyclerView.Adapter<*>, addItemDecoration: Boolean = true, lastElement: (watchLastElement: Boolean, isScrolling: Boolean) -> Unit) {
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL

        this.layoutManager = linearLayoutManager
        this.adapter = adapter
        this.isNestedScrollingEnabled = true

        if (addItemDecoration) {
            this.addItemDecoration(SimpleDividerItemDecoration(context))
        }

        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                    lastElement(false, true)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolling && (linearLayoutManager.itemCount - linearLayoutManager.findFirstVisibleItemPosition() - linearLayoutManager.childCount == 0)) {
                    isScrolling = false
                    lastElement(true, false)
                }
            }
        })
    }

}