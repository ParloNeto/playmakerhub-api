package com.br.playmakerhub.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.br.playmakerhub.integration.controller", "com.br.playmakerhub.config"},
        plugin = {"pretty"}
)
public class CucumberRunner {
}