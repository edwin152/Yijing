package work.edwinlib.android.cache

import android.os.Handler
import android.os.Looper
import work.edwinlib.android.cache.CacheObserver.Type

open class Cache<T> {

    var isLoading = false
        set(value) {
            field = value
            if (!value) {
                loadFinishByCallback()
            }
        }
    val list: List<T> = ArrayList()

    private val handler = Handler(Looper.getMainLooper())
    private var observers = ArrayList<CacheObserver>()

    val observersCount: Int get() = observers.size
    private val isMainThread: Boolean get() = Looper.getMainLooper() == Looper.myLooper()

    fun notifyByCallback(type: Type, vararg params: Any) {
        if (!isMainThread)  {
            throw InterruptedException("notifyByCallback must be called in UI thread")
        }
        for (callback in observers) {
            callback.update(type, *params)
        }
    }

    fun loadFinishByCallback(payload: Any? = null) {
        if (!isMainThread)  {
            throw InterruptedException("notifyByCallback must be called in UI thread")
        }
        for (callback in observers) {
            callback.loadFinish(payload)
        }
    }

    fun registerCallback(callback: CacheObserver) {
        if (observers.contains(callback)) return
        observers.add(callback)
    }

    fun unregisterCallback(callback: CacheObserver) {
        observers.remove(callback)
    }

    open fun set(dataList: List<T>?) {
        if (isMainThread) {
            synchronized(list) {
                (list as ArrayList<T>).let {
                    it.clear()
                    if (dataList != null) {
                        it.addAll(dataList)
                    }
                }
                notifyByCallback(Type.REFRESH)
            }
        } else {
            handler.post { set(dataList) }
        }
    }

    open fun append(data: T?) {
        if (data == null) return
        if (isMainThread) {
            synchronized(list) {
                val size = list.size
                (list as ArrayList<T>).add(data)
                notifyByCallback(Type.INSERT, size, 1)
            }
        } else {
            handler.post { append(data) }
        }
    }

    open fun append(index: Int, data: T?) {
        if (data == null) return
        if (isMainThread) {
            synchronized(list) {
                (list as ArrayList<T>).add(index, data)
                notifyByCallback(Type.INSERT, index, 1)
            }
        } else {
            handler.post { append(index, data) }
        }
    }

    open fun append(dataList: List<T>?) {
        if (dataList == null) return
        if (isMainThread) {
            synchronized(list) {
                val size = list.size
                (list as ArrayList<T>).addAll(dataList)
                notifyByCallback(Type.INSERT, size, dataList.size)
            }
        } else {
            handler.post { append(dataList) }
        }
    }

    open fun append(index: Int, dataList: List<T>?) {
        if (dataList == null) return
        if (isMainThread) {
            synchronized(list) {
                (list as ArrayList<T>).addAll(index, dataList)
                notifyByCallback(Type.INSERT, index, dataList.size)
            }
        } else {
            handler.post { append(index, dataList) }
        }
    }

    open fun update(data: T) {
        if (isMainThread) {
            synchronized(list) {
                val index = list.indexOf(data)
                notifyByCallback(Type.UPDATE, index, 1)
            }
        } else {
            handler.post { update(data) }
        }
    }

    open fun remove(data: T) {
        if (isMainThread) {
            synchronized(list) {
                val index = list.indexOf(data)
                if (index != -1) {
                    (list as ArrayList<T>).removeAt(index)
                    notifyByCallback(Type.DELETE, index, 1)
                }
            }
        } else {
            handler.post { remove(data) }
        }
    }

    open fun clear() {
        if (isMainThread) {
            synchronized(list) {
                (list as ArrayList<T>).clear()
                notifyByCallback(Type.CLEAR)
            }
        } else {
            handler.post { clear() }
        }
    }
}