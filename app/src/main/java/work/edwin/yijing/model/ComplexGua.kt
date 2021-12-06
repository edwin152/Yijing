package work.edwin.yijing.model

data class ComplexGua(
    val id: Int,
    val name: String,
    val topGua: SimpleGua,
    val btmGua: SimpleGua,
    val word: String,
    val xiangWord: String,
    val yaoWord1: YaoWord,
    val yaoWord2: YaoWord,
    val yaoWord3: YaoWord,
    val yaoWord4: YaoWord,
    val yaoWord5: YaoWord,
    val yaoWord6: YaoWord,
) {
    data class YaoWord(
        val yaoWord: String = "",
        val yaoWordTrans: String = "",
        val yaoXiangWord: String = "",
        val yaoXiangWordTrans: String = "",
    )
}
