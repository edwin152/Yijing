package work.edwin.yijing.ui.launcher

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import work.edwin.yijing.ui.BasePresenter
import work.edwinlib.android.utils.Time

class LauncherPresenter : BasePresenter(), LauncherContract.Presenter {

    internal lateinit var view: LauncherContract.View

    override fun loadAndDelay(onFinished: () -> Unit) {
        viewModelScope.launch {
            val curr = Time.curr
            val delayTime = (1000 - Time.curr + curr)
            if (delayTime > 0) {
                delay(delayTime)
            }
            onFinished()
        }
    }
}