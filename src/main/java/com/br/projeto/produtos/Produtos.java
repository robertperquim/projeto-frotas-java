package com.br.projeto.produtos;

import com.br.projeto.exeptions.ProdutoNaoEncontradoException;
import com.br.projeto.exeptions.QuantidadeInvalidaException;
import com.br.projeto.util.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Produtos {

    private String nome;
    private double peso;
    private int quantidade;

    public List<Produtos> produtosList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public Produtos() {
    }

    public Produtos(String nome, double peso, int quantidade) {
        this.nome = nome;
        this.peso = peso;
        this.quantidade = quantidade;
    }



    public void getListaDeProdutos() {
        /**/
        JSONArray produtos = JsonReader.lerArquivoJson("src/main/resources/json/produtos.json");
        System.out.println("= Lista de Produtos =");
        for (Object produtosJson : produtos) {
            JSONObject produto = (JSONObject) produtosJson;
            System.out.print("id: ");
            System.out.print(produto.get("id"));
            System.out.print(", nome: ");
            System.out.print(produto.get("nome"));
            System.out.print(", peso: ");
            System.out.println(produto.get("peso"));
        }
    }


    public void adicionarProduto() {
        JSONArray produtos = JsonReader.lerArquivoJson("src/main/resources/json/produtos.json");
        boolean produtoEncontrado = false;
        int idSelecionado = 0, quantidadeProduto = 0;


        idSelecionado = recebeID(scanner);
        quantidadeProduto =  recebeQuantidadeProduto(scanner);


        for (Object produtosJson : produtos) {
            JSONObject produto = (JSONObject) produtosJson;
            int idProduto = Integer.parseInt(produto.get("id").toString());

            if (idSelecionado == idProduto ) {
                String nomeProduto = produto.get("nome").toString();
                double pesoProduto = Double.parseDouble(produto.get("peso").toString());
                Produtos novoProduto = new Produtos(nomeProduto, pesoProduto, quantidadeProduto);
                produtosList.add(novoProduto);
                produtoEncontrado = true;
            }
        }

        if (produtoEncontrado) {
            System.out.println("Produto adicionado com sucesso.");
            System.out.print(produtos.get(idSelecionado -1));
            System.out.println(" Quantidade: " + quantidadeProduto);
        }
    }

    private static int recebeID(Scanner scanner){
        boolean controle = false;
        int idSelecionado = 0;

        while (!controle){
            controle = true;
            try {
                System.out.println("Digite o id do produto: ");
                idSelecionado = scanner.nextInt();
                if(idSelecionado > 8 || idSelecionado < 0){
                    throw new ProdutoNaoEncontradoException("Id de produto nao encontrado!");
                }

            } catch (ProdutoNaoEncontradoException e){
                System.err.println(e.getMessage());
                controle = false;
            }catch (InputMismatchException e){
                System.err.println("Campo digitado invalido! Digite um valor valido");
                scanner.next();
                controle = false;
            }
        }

        return idSelecionado;

    }

    private static int recebeQuantidadeProduto(Scanner scanner){
        boolean controle = false;
        int quantidadeProduto = 0;

        while (!controle){
            controle = true;
            try {
                System.out.println("Digite a quantidade de produtos: ");
                quantidadeProduto = scanner.nextInt();
                if(quantidadeProduto <= 0){
                    throw new QuantidadeInvalidaException("Quantidade Invalida! Por favor, digite uma quantidade valida!");
                }

            } catch (QuantidadeInvalidaException e){
                System.err.println(e.getMessage());
                controle = false;
            }catch (InputMismatchException e){
                System.err.println("Campo digitado invalido! Digite um valor valido");
                scanner.next();
                controle = false;
            }
        }

        return quantidadeProduto;

    }

    public List<Produtos> getProdutosList() {
        return produtosList;
    }

    public double getPeso() {
        return peso;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
