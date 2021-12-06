package work.edwinlib.android.widget

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import work.edwinlib.android.utils.onThrottleClick

abstract class ListAdapter<T, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER_OFFSET = 100
        private const val VIEW_TYPE_FOOTER_OFFSET = 200
    }

    private val headerList = ArrayList<View>()
    private val footerList = ArrayList<View>()
    var dataList: List<T> = ArrayList()

    protected var onItemClick: (data: T) -> Unit = {}
    protected var onLoadMore: () -> Unit = {}

    // true 调用加载回调 false 不调用加载回调
    var loadMore = true
    val dataSize: Int get() = dataList.size
    val headerSize: Int get() = headerList.size
    val footerSize: Int get() = footerList.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position < headerSize -> {
                position + VIEW_TYPE_HEADER_OFFSET
            }
            position >= headerSize + dataSize -> {
                position - headerSize - dataSize + VIEW_TYPE_FOOTER_OFFSET
            }
            else -> getItemVT(position)
        }
    }

    open fun getItemVT(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when {
            viewType / VIEW_TYPE_HEADER_OFFSET == 1 -> {
                HeaderViewHolder(headerList[viewType - VIEW_TYPE_HEADER_OFFSET])
            }
            viewType / VIEW_TYPE_FOOTER_OFFSET == 1 -> {
                FooterViewHolder(footerList[viewType - VIEW_TYPE_FOOTER_OFFSET])
            }
            else -> onCreateVH(parent, viewType)
        }
    }

    abstract fun onCreateVH(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> Unit
            is FooterViewHolder -> Unit
            else -> {
                val data = getItem(position)
                holder.itemView.onThrottleClick {
                    onItemClick(data)
                }
                @Suppress("UNCHECKED_CAST")
                onBindVH(holder as VH, position)

                if (loadMore && position == dataSize + headerSize - 1) {
                    onLoadMore()
                }
            }
        }
    }

    abstract fun onBindVH(holder: VH, position: Int)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        when (holder) {
            is HeaderViewHolder -> Unit
            is FooterViewHolder -> Unit
            else -> {
                @Suppress("UNCHECKED_CAST")
                onBindVH(holder as VH, position, payloads)
            }
        }
    }

    open fun onBindVH(holder: VH, position: Int, payloads: MutableList<Any>) {}

    override fun getItemCount() = headerSize + dataSize + footerSize

    fun onItemClick(onItemClick: (data: T) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun onLoadMore(onLoadMore: () -> Unit) {
        this.onLoadMore = onLoadMore
    }

    fun performLoadMore() {
        if (loadMore) {
            onLoadMore()
        }
    }

    fun addHeader(header: View) {
        if (headerList.contains(header)) return

        headerList.add(header)
        notifyItemInserted(headerList.size - 1)
    }

    fun addHeader(index: Int, header: View) {
        if (headerList.contains(header)) return

        headerList.add(index, header)
        notifyItemInserted(index)
    }

    fun removeHeader(header: View) {
        val index = headerList.indexOf(header)
        if (index == -1) return

        headerList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addFooter(footer: View) {
        if (footerList.contains(footer)) return

        footerList.add(footer)
        notifyItemInserted(headerSize + dataSize + footerSize - 1)
    }

    fun addFooter(index: Int, footer: View) {
        if (footerList.contains(footer)) return

        footerList.add(index, footer)
        notifyItemInserted(headerSize + dataSize + index)
    }

    fun removeFooter(footer: View) {
        val index = footerList.indexOf(footer)
        if (index == -1) return

        footerList.removeAt(index)
        notifyItemRemoved(headerSize + dataSize + index)
    }

    fun getItem(position: Int): T {
        return dataList[position - headerList.size]
    }

    fun getItemOrNull(position: Int): T? {
        return if (position in headerSize until headerSize + dataSize) {
            getItem(position)
        } else null
    }

    fun getPosition(data: T?): Int {
        if (data == null) return -1
        return dataList.indexOf(data) + headerList.size
    }

    fun getFirstItem(): T? {
        return dataList.firstOrNull()
    }

    fun getLastItem(): T? {
        return dataList.lastOrNull()
    }

    open fun setData(list: List<T>?) {
        val dataList = this.dataList as ArrayList<T>
        if (list.isNullOrEmpty()) {
            dataList.clear()
            notifyDataSetChanged()
        } else if (dataList.isEmpty()) {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        } else if (list.containsAll(dataList) && list[0] == dataList[0]) {
            val size = dataSize
            dataList.clear()
            dataList.addAll(list)
            notifyItemRangeInserted(size + headerSize, dataSize - size)
        } else if (dataList.containsAll(list) && list[0] == dataList[0]) {
            val size = list.size
            dataList.clear()
            dataList.addAll(list)
            notifyItemRangeRemoved(size + headerSize, size - dataSize)
        } else {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    open fun appendData(list: List<T>?) {
        if (list == null) return

        val dataList = this.dataList as ArrayList<T>
        val startIndex = list.size + headerList.size
        dataList.addAll(list)
        notifyItemRangeChanged(startIndex, list.size)
    }

    fun notifyDataSetChanged(payload: Any?) {
        notifyItemRangeChanged(headerSize, dataSize, payload)
    }

    fun clear() {
        val dataList = this.dataList as ArrayList<T>
        val size = dataSize
        dataList.clear()
        notifyItemRangeRemoved(headerSize, size)
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            setIsRecyclable(false)
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            setIsRecyclable(false)
        }
    }
}