package hu.tuku13.spacex_compose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.spacex_compose.Constants.BASE_URL
import hu.tuku13.spacex_compose.data.network.SpaceXApi
import hu.tuku13.spacex_compose.repository.SpaceXRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesSpaceXRepository(api: SpaceXApi) = SpaceXRepository(api)

    @Singleton
    @Provides
    fun providesSpaceXApi(): SpaceXApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }
}