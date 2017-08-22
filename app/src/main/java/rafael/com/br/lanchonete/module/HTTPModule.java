package rafael.com.br.lanchonete.module;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rafael.com.br.lanchonete.api.API;
import rafael.com.br.lanchonete.BuildConfig;
import rafael.com.br.lanchonete.net.NetworkUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.*;

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
    public OkHttpClient provideOkHttp(final Cache cache, Dispatcher dispatcher){
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(Level.BASIC);

            builder.addInterceptor(logging);
        }

        return   builder
                .dispatcher(dispatcher)
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .addInterceptor(new OfflineResponseCacheInterceptor())
                .cache(cache)
                .build();
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

    private static class OfflineResponseCacheInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7) // 1 week
                        .build();
            }

            return chain.proceed(request);
        }

    }

    private static class ResponseCacheInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            if(response.headers().get("Cache-Control") != null){
                return response;
            }

            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 3) // 3 seconds
                    .build();
        }

    }

}
