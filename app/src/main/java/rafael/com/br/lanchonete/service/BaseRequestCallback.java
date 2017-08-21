package rafael.com.br.lanchonete.service;

/**
 * Created by rafael-iteris on 18/08/17.
 */

public interface BaseRequestCallback<T, E> {

    void onSuccess(T result);
    void onErro(E err);

    void onStart();
    void onEnd();

}
