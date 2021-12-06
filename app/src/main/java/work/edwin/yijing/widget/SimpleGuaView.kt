package work.edwin.yijing.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import work.edwin.yijing.R
import work.edwin.yijing.databinding.ViewGuaSimpleBinding
import work.edwin.yijing.model.SimpleGua

@SuppressLint("CustomViewStyleable")
class SimpleGuaView : LinearLayoutCompat {

    private val binding: ViewGuaSimpleBinding

    var simpleGua: SimpleGua
        set(value) {
            field = value
            binding.topYao.yao = simpleGua.topYao
            binding.midYao.yao = simpleGua.midYao
            binding.btmYao.yao = simpleGua.btmYao
        }

    val btmYao: YaoView get() = binding.btmYao
    val midYao: YaoView get() = binding.midYao
    val topYao: YaoView get() = binding.topYao

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val layoutInflater = LayoutInflater.from(context)
        binding = ViewGuaSimpleBinding.inflate(layoutInflater, this)
        orientation = VERTICAL

        val a = context.obtainStyledAttributes(attrs, R.styleable.Gua)
        simpleGua = SimpleGua.values()[a.getInt(R.styleable.Gua_gua_name, 0)]
        a.recycle()
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(VERTICAL)
    }
}