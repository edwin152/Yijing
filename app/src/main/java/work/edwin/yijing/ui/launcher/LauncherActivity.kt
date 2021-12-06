package work.edwin.yijing.ui.launcher

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.activity.viewModels
import work.edwin.yijing.databinding.ActivityLauncherBinding
import work.edwin.yijing.ui.BaseActivity
import work.edwin.yijing.ui.divination.launchDivination

class LauncherActivity : BaseActivity(), LauncherContract.View {

    private val presenter: LauncherPresenter by viewModels()
    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.view = this

        initView()

        presenter.loadAndDelay {
            launchDivination()
            finish()
        }
    }

    private fun initView() {
        val animation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
        ).apply {
            duration = 5000
            fillAfter = true
            repeatMode = Animation.RESTART
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
        }
        binding.taiji.startAnimation(animation)
    }
}