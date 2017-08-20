package rafael.com.br.lanchonete.module;

import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Dispatcher;
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

    @Provides
    @Singleton
    public Dispatcher provideDispatcher(){
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(3);

        return dispatcher;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp(final Cache cache, final Interceptor interceptor, Dispatcher dispatcher){
        return new OkHttpClient().newBuilder()
                .dispatcher(dispatcher)
                .addNetworkInterceptor(interceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
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

    @Provides
    @Singleton
    public API provideAPI(Retrofit retrofit){
        return retrofit.create(API.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient http, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL).client(http)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public Picasso providePicasso(Context context, OkHttpClient client){
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
    }

}
