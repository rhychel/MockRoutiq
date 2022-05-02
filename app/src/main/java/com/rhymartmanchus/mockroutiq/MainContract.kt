package com.rhymartmanchus.mockroutiq

import com.rhymartmanchus.mockroutiq.domain.RouteDetail

interface MainContract {

    interface View {
        fun renderDetails(routeDetail: RouteDetail)
    }

    interface Presenter {
        fun takeView(view: View?)
        fun onViewCreated()
        fun detachView()
    }

}