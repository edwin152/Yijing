package work.edwinlib.android.cache

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class LiveDataCache<T> : Cache<T>() {

    private val liveData = MutableLiveData<List<T>>()

    fun notifyByLiveData() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            liveData.value = list
        } else {
            liveData.postValue(list)
        }
    }

    fun getAsync(): LiveData<List<T>> {
        return liveData
    }

    override fun set(dataList: List<T>?) {
        super.set(dataList)
        notifyByLiveData()
    }

    override fun append(data: T?) {
        super.append(data)
        notifyByLiveData()
    }

    override fun append(index: Int, data: T?) {
        super.append(index, data)
        notifyByLiveData()
    }

    override fun append(dataList: List<T>?) {
        super.append(dataList)
        notifyByLiveData()
    }

    override fun append(index: Int, dataList: List<T>?) {
        super.append(index, dataList)
        notifyByLiveData()
    }

    override fun update(data: T) {
        super.update(data)
        notifyByLiveData()
    }

    override fun clear() {
        super.clear()
        notifyByLiveData()
    }
}