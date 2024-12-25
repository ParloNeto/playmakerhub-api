Feature: Consultar versões e temporadas do FIFA

  Scenario: Buscar temporada inicial pela versão FIFA
    Given que o usuário busca pela versão existente "FIFA 20"
    When o sistema retorna a temporada inicial para esta versão
    Then a temporada retornada deve ser "temporada-19-20"

  Scenario: Obter todas as versões do FIFA
    Given que o usuário deseja obter todas as versões do FIFA
    When o sistema retorna as versões disponíveis
    Then o sistema deve retornar as versões "FIFA 15", "FIFA 16", "FIFA 17"

  Scenario: Obter todas as temporadas do FIFA
    Given que o usuário deseja obter todas as temporadas do FIFA
    When o sistema retorna as temporadas disponíveis
    Then o sistema deve retornar as temporadas "temporada-14-15", "temporada-15-16", "temporada-16-17"

  Scenario: Buscar versão não existente do FIFA
    Given que o usuário busca por uma versão inexistente "FIFA 99"
    When o sistema não encontra a versão
    Then o sistema deve retornar a versão "FIFA 99"
