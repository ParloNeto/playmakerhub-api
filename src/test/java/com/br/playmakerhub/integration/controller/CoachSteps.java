package com.br.playmakerhub.integration.controller;

import com.br.playmakerhub.models.Coach;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CoachSteps {

    private RequestSpecification requestSpec;

    private Response response;
    private Coach coach;
    private String coachId;

    @Given("I have a coach with name {string}, nationality {string}, and URL {string}")
    public void i_have_a_coach_with_name_nationality_and_url(String name, String nationality, String url) {
        coach = new Coach();
        coach.setCoachesName(name);
        coach.setNationality(nationality);
        coach.setUrlImageCoach(url);

        requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(coach);
    }

    @When("I create the coach")
    public void i_create_the_coach() {
        response = requestSpec.when().post("http://localhost:8080/coaches");
        response.then().statusCode(201); // Valida que a resposta tem status 201 (Criado)
        coachId = response.jsonPath().getString("id"); // Armazena o ID do treinador
        assertNotNull(coachId, "O ID do treinador não deve ser nulo");
    }

    @Then("I should retrieve the coach by ID")
    public void i_should_retrieve_the_coach_by_id() {
        response = RestAssured.get("http://localhost:8080/coaches/" + coachId);
    }

    @Then("The coach should be created successfully, and I should receive status {string}")
    public void i_should_be_created_coach_successfully(String expectedStatusCode) {
        response.then()
                .statusCode(Integer.parseInt(expectedStatusCode))
                .body("id", equalTo(coachId))
                .body("coachesName", notNullValue())
                .body("nationality", notNullValue())
                .body("urlImageCoach", notNullValue());
    }

    @And("I delete the coach by ID")
    public void i_delete_the_coach_by_id() {
        Response deleteResponse = RestAssured.delete("http://localhost:8080/coaches/" + coachId);
        deleteResponse.then().statusCode(204);
    }

    @When("o usuário tenta excluir o técnico novamente pelo ID")
    public void usuario_tenta_excluir_tecnico_novamente() {
        response = RestAssured.delete("http://localhost:8080/coaches/" + coachId);
    }

    @Then("o sistema deve retornar um erro com o status {string}")
    public void sistema_retorna_erro_exclusao_tecnico(String expectedStatusCode) {
        response.then()
                .statusCode(Integer.parseInt(expectedStatusCode)); // Valida que o status retornado está correto
    }


    @Given("que o técnico criado existe no sistema")
    public void i_have_a_coach_created_in_application() {


    }

    @When("o usuário solicita os detalhes do técnico pelo ID")
    public void i_should_find_the_coach() {
        response = RestAssured.get("http://localhost:8080/coaches/" + coachId);
    }

    @Then("o sistema deve retornar os detalhes do técnico")
    public void application_must_return_coach_details() {
        response.then().body("id", equalTo(coachId))
                .body("coachesName", notNullValue())
                .body("nationality", notNullValue())
                .body("urlImageCoach", notNullValue());
    }

    @And("a resposta deve conter um código de status {string}")
    public void i_delete_the_coach_by_id(String expectedStatusCode) {
        response.then().statusCode(Integer.parseInt(expectedStatusCode));
    }

    @Given("que o técnico com ID {string} não existe no sistema")
    public void dado_que_nao_existe_tecnico_com_o_id_referenciado(String idInexistente) {
        response = RestAssured.get("http://localhost:8080/coaches/" + idInexistente);
    }

    @When("o usuário solicita os detalhes do técnico com ID {string}")
    public void o_usuario_solicita_detalhes_tecnico_com_id(String idInexistente) {
        response = RestAssured.get("http://localhost:8080/coaches/" + idInexistente);
    }

    @Then("o sistema deve retornar uma mensagem de erro {string}")
    public void sistema_retorna_mensagem_erro(String expectedMessage) {
        response.then()
                .statusCode(404) // Valida que o status retornado é 404
                .body("message", equalTo(expectedMessage)); // Valida que a mensagem de erro está correta
    }

}