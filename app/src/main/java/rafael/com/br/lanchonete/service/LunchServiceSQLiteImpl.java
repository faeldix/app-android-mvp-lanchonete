package rafael.com.br.lanchonete.service;

/**
 * Created by rafaelfreitas on 8/16/17.
 */

public class LunchServiceSQLiteImpl implements LunchService {

    @Override
    public void getListOfLunchs(LunchServiceRESTImpl.OnRequestListOfLunchsFinished callback) {
        // aqui caso fosse necessario utilizar uma implentacao em SQLite para acesso offline de dados de negocio
        // que não cacheados via GET HTTP, quando percebido que o usuario estivesse offline usa-se uma implentacao dessa classe
        // e nao a implentacao REST
    }

}
