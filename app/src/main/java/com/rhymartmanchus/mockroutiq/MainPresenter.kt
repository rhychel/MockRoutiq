package com.rhymartmanchus.mockroutiq

import com.rhymartmanchus.mockroutiq.domain.FetchRouteDetailsUseCase
import com.rhymartmanchus.mockroutiq.domain.IAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(
    private val appDispatcher: IAppDispatchers,
    private val fetchRouteDetailsUseCase: FetchRouteDetailsUseCase
) : MainContract.Presenter, CoroutineScope {

    private var view: MainContract.View? = null
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = appDispatcher.io() + job

    override fun takeView(view: MainContract.View?) {
        this.view = view
    }

    override fun onViewCreated() {
        launch {
            try {
                val result = fetchRouteDetailsUseCase.execute(Unit)
                withContext(appDispatcher.ui()) {
                    view?.renderDetails(result.routeDetail)
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override fun detachView() {
        view = null
    }


}