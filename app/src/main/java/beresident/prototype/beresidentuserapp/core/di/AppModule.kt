package beresident.prototype.beresidentuserapp.core.di

import android.content.Context
import beresident.prototype.beresidentuserapp.BaseApplication
import beresident.prototype.beresidentuserapp.core.services.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule { // Makes out MainActivity ready for dagger hilt instances

    //Production url server
    private const val DEV_BASE_URL = "http://192.168.11.196:2400"

    // MAIN APPLICATION ----------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    //NETWORK --------------------------------------------------------------------------------------

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{ //Sets our connection structure
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(DEV_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService { //Returns the connection of our server
        return retrofit.create(ApiService::class.java)
    }
}