package work.edwin.yijing.model

enum class SimpleGua(val guaName: String, val topYao: Yao, val midYao: Yao, val btmYao: Yao) {
    Qian("乾", Yao.Yang, Yao.Yang, Yao.Yang),
    Kun("坤", Yao.Yin, Yao.Yin, Yao.Yin),
    Zhen("震", Yao.Yin, Yao.Yin, Yao.Yang),
    Gen("艮", Yao.Yang, Yao.Yin, Yao.Yin),
    Li("离", Yao.Yang, Yao.Yin, Yao.Yang),
    Kan("坎", Yao.Yin, Yao.Yang, Yao.Yin),
    Dui("兑", Yao.Yin, Yao.Yang, Yao.Yang),
    Xun("巽", Yao.Yang, Yao.Yang, Yao.Yin);

    companion object {
        fun parse(guaName: String): SimpleGua {
            values().forEach {
                if (guaName == it.guaName) return it
            }
            return Qian
        }
    }
}