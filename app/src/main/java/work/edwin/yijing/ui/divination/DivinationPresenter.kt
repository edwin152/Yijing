package work.edwin.yijing.ui.divination

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import work.edwin.yijing.model.ComplexGua
import work.edwin.yijing.ui.BasePresenter
import work.edwin.yijing.util.GuaUtils

class DivinationPresenter : BasePresenter(), DivinationContract.Presenter {

    internal lateinit var view: DivinationContract.View

    override fun loadGua(number1: Int, number2: Int, number3: Int, onFinish: (ComplexGua, Int) -> Unit) {
        viewModelScope.launch {
            val btm = number1 % 8
            val top = number2 % 8
            val yao = number3 % 6

            val guaId = GuaUtils.getComplexGuaId(topIndex = top, btmIndex = btm)
            GuaUtils.obtain(guaId)?.let {
                onFinish(it, yao)
            }
        }
    }
}