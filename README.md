# Lanchonete App

Este aplicativo foi desenvolvido para uma entrevista de emprego

---

**Arquitetura** 

MVP 

**Tecnologias utilizadas**

* iconify + fontawesome
* number picker
* okhttp 3
* retrofit 2
* picasso
* gson
* butterknife
* dagger 2
* RXAndroid

**Test** 

* JUnit
* Mockito
* Robolectric

**TEST COVERAGE**

66% classes 65% lines covered (by JaCoCo) (atualizado em 30/08/2017) 

**DESCRIÇÃO**

Somos uma startup do ramo de alimentos e precisamos de uma aplicativo para nossos clientes realizarem pedidos, uma vez que já temos uma aplicação web, e um servidor (feito às pressas por outro fornecedor). Nossa especialidade é a venda de lanches, de modo que alguns lanches são opções de cardápio e podem conter ingredientes personalizados.
 
A seguir, apresentamos a lista de ingredientes disponíveis:

| Ingrediente         | Valor   |
|---------------------|---------|
| Alface              | R$ 0.40 |
| Bacon               | R$ 2,00 |
| Hambúrguer de carne | R$ 3,00 |
| Ovo                 | R$ 0,80 |
| Queijo              | R$ 1,50 |
| Pão com Gergelim    | R$ 1,00 |

Segue as opções de cardápio e seus respectivos ingredientes:

| Lanche              | Ingredient   |
|---------------------|---------|
| X-Bacon             | Pão com gergelim, bacon, hambúrguer de carne e queijo |
| X-Burger | Pão com gergelim, hambúrguer de carne e queijo |
| X-Egg | Pão com gergelim, ovo, hambúrguer de carne e queijo |
| X-Egg Bacon | Pão com gergelim, alface, ovo, bacon, hambúrguer de carne e queijo |

O valor de cada opção do cardápio é dado pela soma dos ingredientes que compõe o lanche. Além destas
opções, o cliente pode personalizar seu lanche e escolher os ingredientes que desejar. Nesse caso, o preço
do lanche também será calculado pela soma dos ingredientes.
 
Existe uma exceção à regra para o cálculo de preço, quando o lanche pertencer à uma promoção. A seguir,
apresentamos a lista de promoções e suas respectivas regras de negócio:

| Lanche              | Ingredient   |
|---------------------|---------|
| Light | Se o lanche tem alface e não tem bacon, ganha 10% de desconto. |
| Muita Carne | A cada 3 porções de carne o cliente só paga 2. Se o lanche tiver 6 porções, o cliente pagará 4. Assim por diante |
| Muito Queijo | A cada 3 porções de queijo o cliente só paga 2. Se o lanche tiver 6 porções, o cliente pagará 4. Assim por diante |

**REQUISITOS**

Para o lançamento do aplicativo (MVP - Minimum Viable Product), deseja-se que ele tenha:
* Acesso aos dados fornecidos pelo servidor já pronto
* Uma tela com a lista dos lanches, com foto, nome, preço e ingredientes
* Para obter os lanches
  * GET http://localhost:8080/api/lanche 
* Para obter os ingredientes do lanche, e calcular seu preço: 
  * GET http://localhost:8080/api/ingrediente/de/<id_lanche> 
* Uma tela para escolha do lanche, com foto, nome, preço, ingredientes, descrição, botão para adicionar ao carrinho de compras e botão para customizar (que pode abrir um popup, ou outra tela, e escolhe-se quantos ingredientes a mais quiser, e ao finalizar a customização, volta para a tela anterior, atualizando o preço, ingredientes e nome, adicionando o sufixo “ - do seu jeito “)
  * Para obter dados de um lanche GET http://localhost:8080/api/lanche/<id_lanche> 
* Para obter todos os ingredientes
  * GET http://localhost:8080/api/ingrediente
* Para adicionar um lanche ao carrinho de compras
  * PUT http://localhost:8080/api/pedido/<id_lanche>
* Para adicionar um lanche personalizado carrinho de compras
  * PUT  http://localhost:8080/api/pedido/<id_lanche> e, no corpo da requisição, um único parâmetro extras, que é um JsonArray com ids dos ingredientes extras (extras=[1,2,3], por exemplo)
* Uma tela com as promoções
  * Para obter as promoções http://localhost:8080/api/promocao 
* Uma tela de carrinho de compras, onde se lista todos os itens pedidos
  * Para obter os itens pedidos http://localhost:8080/api/pedido 
* Os cálculos de preço devem levar em conta as promoções. Para o MVP, a regra do texto deve ser implementada no código do aplicativo.
* O layout de acordo com sua criatividade, não precisando gastar muito esforço nisso.
* Um tratamento para retornos vazios (de pedidos e promoções, por exemplo)

### TODO

* subir o servidor pra AWS
* adicionar os testes com expresso
* passar o projeto para o algum servidor de Countinous Delivery
