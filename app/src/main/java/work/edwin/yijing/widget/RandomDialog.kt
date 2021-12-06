package work.edwin.yijing.widget

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.core.animation.doOnEnd
import work.edwin.yijing.databinding.DialogRandomBinding
import work.edwinlib.android.utils.Time
import work.edwinlib.android.utils.onThrottleClick
import work.edwinlib.android.widget.FullScreenDialog
import kotlin.random.Random

class RandomDialog(context: Context) : FullScreenDialog(context) {

    private val random by lazy {
        Random(Time.curr)
    }

    private lateinit var binding: DialogRandomBinding
    private val nextInt: Int
        get() = random.nextInt(100, 999)

    var callback: Callback? = null
    var number1: Int = 100
    var number2: Int = 100
    var number3: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        gravity = Gravity.CENTER_VERTICAL
    }

    private fun initView() {
        binding.number1.text = number1.toString()
        binding.number2.text = number2.toString()
        binding.number3.text = number3.toString()

        binding.random.onThrottleClick {
            binding.random.isEnabled = false
            setCanceledOnTouchOutside(false)

            val animator3 = ValueAnimator().apply {
                duration = 500
                setIntValues(0)
                addUpdateListener {
                    number3 = nextInt
                    binding.number3.text = number3.toString()
                }
                doOnEnd {
                    callback?.onRandomResult(number1, number2, number3)
                    binding.root.postDelayed({ dismiss() }, 100)
                }
            }
            val animator2 = ValueAnimator().apply {
                duration = 500
                setIntValues(0)
                addUpdateListener {
                    number2 = nextInt
                    binding.number2.text = number2.toString()
                }
                doOnEnd { animator3.start() }
            }
            val animator1 = ValueAnimator().apply {
                duration = 500
                setIntValues(0)
                addUpdateListener {
                    number1 = nextInt
                    binding.number1.text = number1.toString()
                }
                doOnEnd { animator2.start() }
            }

            animator1.start()
        }
    }

    interface Callback {
        fun onRandomResult(number1: Int, number2: Int, number3: Int)
    }
}