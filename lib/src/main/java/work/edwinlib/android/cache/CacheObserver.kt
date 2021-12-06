package work.edwinlib.android.cache

interface CacheObserver {

    val headerSize: Int

    /**
     * INSERT index(Int) count(Int)
     * DELETE index(Int) count(Int)
     * UPDATE index(Int) count(Int)
     * MOVE from(Int) to(Int)
     * CLEAR
     * REFRESH
     */
    fun update(type: Type, vararg params: Any)

    fun loadFinish(payload: Any? = null)

    enum class Type {
        INSERT,
        DELETE,
        MOVE,
        UPDATE,
        CLEAR,
        REFRESH,
    }
}