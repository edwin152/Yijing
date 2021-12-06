package work.edwin.yijing.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import work.edwin.yijing.databinding.ViewGuaComplexBinding
import work.edwin.yijing.model.ComplexGua
import work.edwin.yijing.model.SimpleGua

class ComplexGuaView : LinearLayoutCompat {

    private val binding: ViewGuaComplexBinding

    var complexGua: ComplexGua
        set(value) {
            field = value
            binding.topSimple.simpleGua = value.topGua
            binding.btmSimple.simpleGua = value.btmGua
        }

    val yao1: YaoView get() = binding.btmSimple.btmYao
    val yao2: YaoView get() = binding.btmSimple.midYao
    val yao3: YaoView get() = binding.btmSimple.topYao
    val yao4: YaoView get() = binding.topSimple.btmYao
    val yao5: YaoView get() = binding.topSimple.midYao
    val yao6: YaoView get() = binding.topSimple.topYao

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ViewGuaComplexBinding.inflate(layoutInflater, this)
        orientation = VERTICAL

        complexGua = ComplexGua(
            1, "",
            SimpleGua.Kun, SimpleGua.Zhen,
            "", "",
            ComplexGua.YaoWord(), ComplexGua.YaoWord(), ComplexGua.YaoWord(),
            ComplexGua.YaoWord(), ComplexGua.YaoWord(), ComplexGua.YaoWord(),
        )
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(VERTICAL)
    }
}