package work.edwin.yijing.util

import work.edwin.yijing.App
import work.edwin.yijing.model.ComplexGua
import work.edwin.yijing.model.SimpleGua
import java.io.BufferedReader
import java.io.InputStreamReader

object GuaUtils {
    private val GUA_ID_MAP = arrayOf(
        intArrayOf(1, 43, 14, 34, 9, 5, 26, 11),
        intArrayOf(10, 58, 38, 54, 61, 60, 41, 19),
        intArrayOf(13, 49, 30, 55, 37, 63, 22, 36),
        intArrayOf(25, 17, 21, 51, 42, 3, 27, 24),
        intArrayOf(44, 28, 50, 32, 57, 48, 18, 46),
        intArrayOf(6, 47, 64, 40, 59, 29, 4, 7),
        intArrayOf(33, 31, 56, 62, 53, 39, 52, 15),
        intArrayOf(12, 45, 35, 16, 20, 8, 23, 2),
    )

    fun getComplexGuaId(topIndex: Int, btmIndex: Int): Int {
        return GUA_ID_MAP[btmIndex][topIndex]
    }

    fun obtain(id: Int): ComplexGua? {
        val stream = App.instance.assets.open("_64.txt")
        val streamReader = InputStreamReader(stream)
        val reader = BufferedReader(streamReader)

        do {
            val line = reader.readLine() ?: return null

            if (id.toString() != line) continue

            val name = reader.readLine()
            val (topGua, btmGua) = reader.readLine().let {
                SimpleGua.parse(it.substring(2, 3)) to SimpleGua.parse(it.substring(0, 1))
            }
            val word = reader.readLine()
            val xiangWord = reader.readLine()

            val yaoWord1 = reader.readLine()
            val yaoXiangWord1 = reader.readLine()
            val yaoWord2 = reader.readLine()
            val yaoXiangWord2 = reader.readLine()
            val yaoWord3 = reader.readLine()
            val yaoXiangWord3 = reader.readLine()

            val yaoWord4 = reader.readLine()
            val yaoXiangWord4 = reader.readLine()
            val yaoWord5 = reader.readLine()
            val yaoXiangWord5 = reader.readLine()
            val yaoWord6 = reader.readLine()
            val yaoXiangWord6 = reader.readLine()

            return ComplexGua(
                id = id, name = name,
                topGua = topGua, btmGua = btmGua,
                word = word, xiangWord = xiangWord,
                ComplexGua.YaoWord(
                    yaoWord = yaoWord1,
                    yaoXiangWord = yaoXiangWord1,
                ),
                ComplexGua.YaoWord(
                    yaoWord = yaoWord2,
                    yaoXiangWord = yaoXiangWord2,
                ),
                ComplexGua.YaoWord(
                    yaoWord = yaoWord3,
                    yaoXiangWord = yaoXiangWord3,
                ),
                ComplexGua.YaoWord(
                    yaoWord = yaoWord4,
                    yaoXiangWord = yaoXiangWord4,
                ),
                ComplexGua.YaoWord(
                    yaoWord = yaoWord5,
                    yaoXiangWord = yaoXiangWord5,
                ),
                ComplexGua.YaoWord(
                    yaoWord = yaoWord6,
                    yaoXiangWord = yaoXiangWord6,
                ),
            )
        } while (true)
    }
}