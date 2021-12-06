package work.edwinlib.android.widget

import work.edwinlib.android.cache.CacheObserver
import work.edwinlib.android.cache.CacheObserver.Type

class SimpleListObserver<T>(
    private val adapter: ListAdapter<T, *>,
    private val onLoadFinish: (Any?) -> Unit = {},
) : CacheObserver {
    override val headerSize: Int get() = adapter.headerSize

    var callback: Callback? = null

    override fun update(type: Type, vararg params: Any) {
        callback?.beforeNotify()
        when (type) {
            Type.INSERT -> {
                if (params[0] == -1) return
                adapter.notifyItemRangeInserted(
                    headerSize + params[0] as Int, params[1] as Int
                )
            }
            Type.DELETE -> {
                if (params[0] == -1) return
                adapter.notifyItemRangeRemoved(
                    headerSize + params[0] as Int, params[1] as Int
                )
            }
            Type.UPDATE -> {
                if (params[0] == -1) return
                adapter.notifyItemRangeChanged(
                    headerSize + params[0] as Int, params[1] as Int
                )
            }
            Type.MOVE -> {
                if (params[0] == -1) return
                adapter.notifyItemMoved(
                    headerSize + params[0] as Int, headerSize + params[1] as Int
                )
                adapter.notifyItemChanged(headerSize + params[1] as Int)
            }
            Type.CLEAR, Type.REFRESH -> {
                if (params.isEmpty()) {
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.notifyItemRangeChanged(0, adapter.itemCount, params[0])
                }
            }
        }
        callback?.afterNotify()
    }

    override fun loadFinish(payload: Any?) {
        onLoadFinish(payload)
    }

    interface Callback {
        fun beforeNotify()
        fun afterNotify()
    }
}