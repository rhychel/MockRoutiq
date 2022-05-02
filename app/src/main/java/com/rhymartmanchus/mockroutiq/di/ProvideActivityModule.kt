package com.rhymartmanchus.mockroutiq.di

import android.content.Context
import com.rhymartmanchus.mockroutiq.data.RoutesRemoteService
import com.rhymartmanchus.mockroutiq.data.RoutesRemoteServiceProvider
import com.rhymartmanchus.mockroutiq.domain.FetchRouteDetailsUseCase
import com.rhymartmanchus.mockroutiq.domain.RoutesGateway
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object ProvideActivityModule {

    @Provides
    fun provideFetchRouteDetailsUseCase(
        gateway: RoutesGateway
    ): FetchRouteDetailsUseCase =
        FetchRouteDetailsUseCase(gateway)

    @Provides
    fun provideRoutesRemoteService(
        @ApplicationContext context: Context
    ): RoutesRemoteService = RoutesRemoteServiceProvider(context.assets)

}