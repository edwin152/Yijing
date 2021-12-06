package work.edwin.yijing.ui.launcher

import work.edwin.yijing.ui.BaseContract

interface LauncherContract {
    interface View : BaseContract.View
    interface Presenter : BaseContract.Presenter {
        fun loadAndDelay(onFinished: () -> Unit)
    }
}