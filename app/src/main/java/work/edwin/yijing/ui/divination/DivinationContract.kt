package work.edwin.yijing.ui.divination

import work.edwin.yijing.model.ComplexGua
import work.edwin.yijing.ui.BaseContract

interface DivinationContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter {
        fun loadGua(number1: Int, number2: Int, number3: Int, onFinish: (ComplexGua, Int) -> Unit)
    }
}