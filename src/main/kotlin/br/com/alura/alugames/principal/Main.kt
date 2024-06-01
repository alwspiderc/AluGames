package org.example.br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Gamer
import br.com.alura.alugames.servicos.ConsumoApi
import org.example.br.com.alura.alugames.modelo.Jogo
import transformarEmIdade
import java.util.*

fun main() {

    val leitura = Scanner(System.`in`)
    val gamer = Gamer.criarGamer(leitura)
    println("Cadastro concluído com sucesso. Dados do gamer:")

    println(gamer)
    println("Idade do gamer: " + gamer.dataNascimento?.transformarEmIdade())

    do {
        println("Digite um código de jogo para buscar:")
        val busca = leitura.nextLine()

        val buscaApi = ConsumoApi()
        val informacaoJogo = buscaApi.buscaJogo(busca)

        var meuJogo: Jogo? = null

        meuJogo = Jogo(informacaoJogo.info.title, informacaoJogo.info.thumb)

        println("Deseja inserir umas descrição para o jogo? (s/n)")
        val opcao = leitura.nextLine()

        meuJogo.descricao = when(opcao.lowercase(Locale.getDefault())) {
            "s" -> {
                println("Digite a descrição do jogo:")
                leitura.nextLine()
            }
            else ->  meuJogo.titulo
        }

        gamer.jogosBuscados.add(meuJogo)

        println("Deseja buscar um novo jogo? (s/n)")
        val resposta = leitura.nextLine()

    } while(resposta.equals("s", true))

    println("Jogos buscados:")
    println(gamer.jogosBuscados)

    println("\nJogos ordenados por título:")
    gamer.jogosBuscados.sortBy {
        it?.titulo
    }


    gamer.jogosBuscados.forEach {
        println("Titulo: " + it?.titulo)
    }

    val jogosFiltrados = gamer.jogosBuscados.filter {
        it?.titulo?.contains("batman", true) ?: false
    }
    println("\nJogos filtrados: ")
    println(jogosFiltrados)

    println("Deseja excluir algum jogo da lista original? S/N")
    val opcao = leitura.nextLine()
    if (opcao.equals("s", true)) {
        println(gamer.jogosBuscados)
        println("\n Informe a posição do jogo que deseja excluir: ")
        val posicao = leitura.nextInt()
        gamer.jogosBuscados.removeAt(posicao)
    }

    println("\n Lista atualizada:")
    println(gamer.jogosBuscados)

    println("Busca finalizada com sucesso.")

}
