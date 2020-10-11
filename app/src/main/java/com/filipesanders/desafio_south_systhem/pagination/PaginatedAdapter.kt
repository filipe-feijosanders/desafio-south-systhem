package com.filipesanders.desafio_south_systhem.pagination

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.filipesanders.desafio_south_systhem.R

abstract class PaginatedAdapter<T, VH : RecyclerView.ViewHolder>(
    private val context: Context,
    diffUtil: DiffUtil.ItemCallback<T>,
    private val paginateOnTop: Boolean = false
) : ListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {

    protected val CELL_TYPE_CONTENT = 0
    protected val CELL_TYPE_LOAD = 1

    var dataSource: PaginatedDataSource<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val shouldLoadMore: Boolean
        get() = dataSource?.value?.isLastPage == false

    private val isRequestingData: Boolean
        get() = dataSource?.isRequestingData?.value == true


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) != CELL_TYPE_LOAD) {
            onBindContentViewHolder(holder as VH, position)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (getItemViewType(position) != CELL_TYPE_LOAD) {
            onBindContentViewHolder(holder as VH, position, payloads)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType != CELL_TYPE_LOAD) {
            onCreateContentViewHolder(parent, viewType)
        } else {
            LoaderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.cell_loader,
                    parent,
                    false
                )
            )
        }
    }

    abstract fun onBindContentViewHolder(holder: VH, position: Int)
    abstract fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): VH

    open fun onBindContentViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        val loadPos = if (paginateOnTop) 0 else itemCount - 1
        return if (shouldLoadMore && position == loadPos) {
            CELL_TYPE_LOAD
        } else {
            CELL_TYPE_CONTENT
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutManager = recyclerView.layoutManager
        if (layoutManager != null) {
            if (paginateOnTop) {
                recyclerView.addOnScrollListener(onScrollListenerTop)
            } else {
                recyclerView.addOnScrollListener(onScrollListenerBottom)
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (paginateOnTop) {
            recyclerView.removeOnScrollListener(onScrollListenerTop)
        } else {
            recyclerView.removeOnScrollListener(onScrollListenerBottom)
        }
        layoutManager = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    private class LoaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val onScrollListenerBottom = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var lastVisibleItem = 0

            if (layoutManager is GridLayoutManager) {
                lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                lastVisibleItem =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }

//            Log.v("Last", lastVisibleItem.toString())

            if (!isRequestingData && lastVisibleItem == itemCount - 1) {

                //Se chegou no fim da lista
                if (shouldLoadMore) {
                    dataSource?.loadMoreItems()
                }
            }
        }
    }

    private val onScrollListenerTop = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var firstVisibleItem = 0

            if (layoutManager is GridLayoutManager) {
                firstVisibleItem =
                    (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                firstVisibleItem =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }

            Log.v("First", firstVisibleItem.toString())

            if (!isRequestingData && firstVisibleItem == 0) {

                //Se chegou no fim da lista
                if (shouldLoadMore) {
                    dataSource?.loadMoreItems()
                }
            }
        }
    }

}