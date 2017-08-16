package rafael.com.br.lanchonete.module;

import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.net.NetworkUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rafael-iteris on 14/08/2017.
 */

@Module
public class HTTPModule {

    @Provides
    @Singleton
    public Cache provideCache(Context context){
        File file = new File(context.getCacheDir(), "response");
        return new Cache(file, 50000000); //50mb de cache
    }

    @Provides @Singleton
    public OkHttpClient provideOkHttp(final Cache cache, final Interceptor interceptor){
        return new OkHttpClient().newBuilder()
                .addNetworkInterceptor(interceptor)
                .cache(cache)
                .build();
    }

    @Provides @Singleton
    public Interceptor provideCacheInterceptor(final Context context){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if(NetworkUtils.isConnected(context)){
                    request = request.newBuilder().addHeader("Cache-Control", "max-age=" + (60*60*24)).build(); // 1 day
                } else {
                    request = request.newBuilder()
                            .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + (60*60*24*10))
                            .build(); // 10 days
                }

                Response response = chain.proceed(request);
                response = response.newBuilder()
                        .removeHeader("Cache-Control")
                        .addHeader("Cache-Control", "max-age=" + (60*60*24)).build();

                return response;
            }
        };
    }

    @Provides @Singleton
    public API provideAPI(Retrofit retrofit){
        return retrofit.create(API.class);
    }

    @Provides @Singleton
    public Retrofit provideRetrofit(OkHttpClient http, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL).client(http)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    @Provides @Singleton
    public Picasso providePicasso(Context context, OkHttpClient client){
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
    }

}