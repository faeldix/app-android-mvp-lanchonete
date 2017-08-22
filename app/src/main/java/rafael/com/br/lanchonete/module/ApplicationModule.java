package rafael.com.br.lanchonete.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rafael.com.br.lanchonete.api.API;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, getDateSerializer());
        builder.setDateFormat("dd/MM/yyyy");

        return builder.create();
    }

    public JsonDeserializer<Date> getDateSerializer(){
        return new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                long time = json.getAsLong();
                Date date = new Date(time);

                return date;
            }

        };
    }

}