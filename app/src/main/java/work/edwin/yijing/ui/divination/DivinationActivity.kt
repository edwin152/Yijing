package work.edwin.yijing.ui.divination

import android.graphics.Color
import android.os.Bundle
import android.text.TextWatcher
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import work.edwin.yijing.databinding.ActivityDivinationBinding
import work.edwin.yijing.ui.BaseActivity
import work.edwin.yijing.widget.RandomDialog
import work.edwinlib.android.helper.KeyboardHelper
import work.edwinlib.android.utils.*

class DivinationActivity : BaseActivity(), DivinationContract.View {

    private val presenter: DivinationPresenter by viewModels()
    private lateinit var binding: ActivityDivinationBinding
    private val keyboardHelper = KeyboardHelper(this)

    private var textWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.view = this

        initView()
    }

    private fun initView() {
        binding.topMargin.layoutParams.height = barHeight

        keyboardHelper.setKeyboardListener { show, heightDiff ->
            binding.edit.translationY = -heightDiff.toFloat() + barHeight
            if (!show) {
                binding.edit.translationY = 0f
                binding.edit.gone()
            }
            false
        }

        binding.number1.onThrottleClick {
            showEdit(it as TextView)
        }
        binding.number1.onLongClick { _, _, _ ->
            showRandomDialog()
            true
        }
        binding.number2.onThrottleClick {
            showEdit(it as TextView)
        }
        binding.number2.onLongClick { _, _, _ ->
            showRandomDialog()
            true
        }
        binding.number3.onThrottleClick {
            showEdit(it as TextView)
        }
        binding.number3.onLongClick { _, _, _ ->
            showRandomDialog()
            true
        }

        binding.submit.onThrottleClick {
            keyboardHelper.hideKeyboard()
            loadGua()
        }
        binding.root.onThrottleClick {
            keyboardHelper.hideKeyboard()
        }
    }

    private fun showEdit(number: TextView) {
        textWatcher?.let {
            binding.edit.removeTextChangedListener(it)
        }

        binding.edit.visible()
        keyboardHelper.showKeyboard(binding.edit)
        binding.edit.setText(number.text)
        binding.edit.setSelection(number.text.length)
        textWatcher = binding.edit.addTextChangedListener {
            number.text = it
        }
    }

    private fun showRandomDialog() {
        RandomDialog(this).apply {
            number1 = binding.number1.text.toString().toInt()
            number2 = binding.number2.text.toString().toInt()
            number3 = binding.number3.text.toString().toInt()
            callback = object : RandomDialog.Callback {
                override fun onRandomResult(number1: Int, number2: Int, number3: Int) {
                    binding.number1.text = number1.toString()
                    binding.number2.text = number2.toString()
                    binding.number3.text = number3.toString()
                }
            }
        }.show()
    }

    private fun loadGua() {
        val number1 = binding.number1.text.toString().toInt()
        val number2 = binding.number2.text.toString().toInt()
        val number3 = binding.number3.text.toString().toInt()
        presenter.loadGua(number1, number2, number3) { gua, yao ->
            binding.complexGua.visible()
            binding.complexGua.complexGua = gua

            binding.guaName.text = gua.name
            binding.guaWord.text = gua.word
            binding.guaXiangWord.text = gua.xiangWord

            binding.yaoWord1.text = gua.yaoWord1.yaoWord
            binding.yaoXiangWord1.text = gua.yaoWord1.yaoXiangWord
            binding.yaoWord2.text = gua.yaoWord2.yaoWord
            binding.yaoXiangWord2.text = gua.yaoWord2.yaoXiangWord
            binding.yaoWord3.text = gua.yaoWord3.yaoWord
            binding.yaoXiangWord3.text = gua.yaoWord3.yaoXiangWord
            binding.yaoWord4.text = gua.yaoWord4.yaoWord
            binding.yaoXiangWord4.text = gua.yaoWord4.yaoXiangWord
            binding.yaoWord5.text = gua.yaoWord5.yaoWord
            binding.yaoXiangWord5.text = gua.yaoWord5.yaoXiangWord
            binding.yaoWord6.text = gua.yaoWord6.yaoWord
            binding.yaoXiangWord6.text = gua.yaoWord6.yaoXiangWord

            binding.yaoWord1.setTextColor(if (yao == 0) Color.RED else Color.WHITE)
            binding.yaoXiangWord1.setTextColor(if (yao == 0) Color.RED else Color.WHITE)
            binding.yaoWord2.setTextColor(if (yao == 1) Color.RED else Color.WHITE)
            binding.yaoXiangWord2.setTextColor(if (yao == 1) Color.RED else Color.WHITE)
            binding.yaoWord3.setTextColor(if (yao == 2) Color.RED else Color.WHITE)
            binding.yaoXiangWord3.setTextColor(if (yao == 2) Color.RED else Color.WHITE)
            binding.yaoWord4.setTextColor(if (yao == 3) Color.RED else Color.WHITE)
            binding.yaoXiangWord4.setTextColor(if (yao == 3) Color.RED else Color.WHITE)
            binding.yaoWord5.setTextColor(if (yao == 4) Color.RED else Color.WHITE)
            binding.yaoXiangWord5.setTextColor(if (yao == 4) Color.RED else Color.WHITE)
            binding.yaoWord6.setTextColor(if (yao == 5) Color.RED else Color.WHITE)
            binding.yaoXiangWord6.setTextColor(if (yao == 5) Color.RED else Color.WHITE)

            binding.complexGua.yao1.color = if (yao == 0) Color.RED else Color.BLACK
            binding.complexGua.yao2.color = if (yao == 1) Color.RED else Color.BLACK
            binding.complexGua.yao3.color = if (yao == 2) Color.RED else Color.BLACK
            binding.complexGua.yao4.color = if (yao == 3) Color.RED else Color.BLACK
            binding.complexGua.yao5.color = if (yao == 4) Color.RED else Color.BLACK
            binding.complexGua.yao6.color = if (yao == 5) Color.RED else Color.BLACK
        }
    }
}