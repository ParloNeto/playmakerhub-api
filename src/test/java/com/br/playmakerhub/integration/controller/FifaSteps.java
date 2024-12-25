package com.br.playmakerhub.integration.controller;

import com.br.playmakerhub.models.response.SeasonResponse;
import com.br.playmakerhub.services.FIFAService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

import java.util.List;

public class FifaSteps {

    @Autowired
    private FIFAService fifaService;

    private String version;
    private ResponseEntity<?> response;

    @Given("^que o usuário busca pela versão existente \"([^\"]*)\"$")
    public void que_o_usuário_busca_pela_versão(String version) {
        this.version = version;
    }

    @When("o sistema retorna a temporada inicial para esta versão")
    public void o_sistema_retorn_a_temporada_inicial_para_esta_versão() {
        String initialSeason = fifaService.getInitialSeasonByFIFAVersion(version);
        response = ResponseEntity.ok(new SeasonResponse(initialSeason));
    }

    @Then("a temporada retornada deve ser {string}")
    public void a_temporada_retornada_deve_ser(String expectedSeason) {
        assertNotNull(response);
        assertEquals(expectedSeason, ((SeasonResponse) response.getBody()).getSeason());
    }

    @Given("que o usuário deseja obter todas as versões do FIFA")
    public void que_o_usuário_deseja_obter_todas_as_versões_do_fifa() {
    }

    @When("o sistema retorna as versões disponíveis")
    public void o_sistema_retorn_as_versões_disponíveis() {
        List<String> versions = fifaService.getFifaVersions();
        response = ResponseEntity.ok(versions);
    }

    @Then("o sistema deve retornar as versões {string}, {string}, {string}")
    public void o_sistema_deve_retornar_as_versões(String version1, String version2, String version3) {
        assertNotNull(response);

        List<String> returnedVersions = (List<String>) response.getBody();
        assertTrue(returnedVersions.contains(version1));
        assertTrue(returnedVersions.contains(version2));
        assertTrue(returnedVersions.contains(version3));
    }

    @Given("que o usuário deseja obter todas as temporadas do FIFA")
    public void que_o_usuário_deseja_obter_todas_as_temporadas_do_fifa() {
    }

    @When("o sistema retorna as temporadas disponíveis")
    public void o_sistema_retorn_as_temporadas_disponíveis() {
        List<String> seasons = fifaService.getFifaSeasons();
        response = ResponseEntity.ok(seasons);
    }

    @Then("o sistema deve retornar as temporadas {string}, {string}, {string}")
    public void o_sistema_deve_retornar_as_temporadas(String season1, String season2, String season3) {
        assertNotNull(response);

        List<String> returnedSeasons = (List<String>) response.getBody();
        assertTrue(returnedSeasons.contains(season1));
        assertTrue(returnedSeasons.contains(season2));
        assertTrue(returnedSeasons.contains(season3));
    }

    @Given("^que o usuário busca por uma versão inexistente \"([^\"]*)\"$")
    public void que_o_usuário_busca_pela_versão_nao_existente(String version) {
        this.version = version;
    }

    @When("o sistema não encontra a versão")
    public void o_sistema_nao_encontra_a_versao() {
        String result = fifaService.getInitialSeasonByFIFAVersion(version);
        response = ResponseEntity.ok(new SeasonResponse(result));
    }

    @Then("o sistema deve retornar a versão {string}")
    public void o_sistema_deve_retornar_a_versao_nao_encontrada(String expectedVersion) {
        assertNotNull(response);
        assertEquals(expectedVersion, ((SeasonResponse) response.getBody()).getSeason());
    }
}
