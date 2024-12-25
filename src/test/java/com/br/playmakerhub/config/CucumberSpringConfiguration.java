package com.br.playmakerhub.config;
import com.br.playmakerhub.PlaymakerHubApiApplication;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;

@CucumberContextConfiguration
@SpringBootTest(classes = PlaymakerHubApiApplication.class)
public class CucumberSpringConfiguration {
}
