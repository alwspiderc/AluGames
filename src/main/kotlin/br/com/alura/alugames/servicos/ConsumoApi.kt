package br.com.alura.alugames.servicos

import com.google.gson.Gson
import org.example.br.com.alura.alugames.modelo.InfoJogo
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ConsumoApi {

    fun buscaJogo(id: String): InfoJogo {
        val endereco = "https://www.cheapshark.com/api/1.0/games?id=$id"

        val client: HttpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder().uri(URI.create(endereco)).build()

        val response = runCatching {
            client.send(request, HttpResponse.BodyHandlers.ofString())
        }.getOrElse {
            throw IOException(it.message)
        }

        if (response.statusCode() != 200) {
            throw IOException("Erro ao procurar jogo: $id")
        }

        val json = response.body()

        val gson = Gson()

        return gson.fromJson(json, InfoJogo::class.java) ?: throw IOException("Jogo não foi achado!")

    }

}