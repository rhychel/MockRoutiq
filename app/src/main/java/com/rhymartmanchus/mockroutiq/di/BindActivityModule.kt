package com.rhymartmanchus.mockroutiq.di

import com.rhymartmanchus.mockroutiq.MainContract
import com.rhymartmanchus.mockroutiq.MainPresenter
import com.rhymartmanchus.mockroutiq.data.RoutesRepository
import com.rhymartmanchus.mockroutiq.domain.AppDispatcher
import com.rhymartmanchus.mockroutiq.domain.IAppDispatchers
import com.rhymartmanchus.mockroutiq.domain.RoutesGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BindActivityModule {

    @Binds
    abstract fun bindAppDispatcher(appDispatcher: AppDispatcher): IAppDispatchers

    @Binds
    abstract fun bindRoutesGateway(routesRepository: RoutesRepository): RoutesGateway

    @Binds
    abstract fun bindMainPresenter(mainPresenter: MainPresenter): MainContract.Presenter

}