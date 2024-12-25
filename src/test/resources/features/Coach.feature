Feature: Gerenciamento de Técnicos

  Scenario: Criar um Coach com sucesso
    Given I have a coach with name "Felipao", nationality "Brazil", and URL "http://example.com/felipao.jpg"
    When I create the coach
    Then The coach should be created successfully, and I should receive status "201"

  Scenario: Obter um técnico existente pelo ID
    Given que o técnico criado existe no sistema
    When o usuário solicita os detalhes do técnico pelo ID
    Then o sistema deve retornar os detalhes do técnico
    And a resposta deve conter um código de status "200"

  Scenario: Tentar obter um técnico inexistente pelo ID
    Given que o técnico com ID "999" não existe no sistema
    When o usuário solicita os detalhes do técnico com ID "999"
    Then o sistema deve retornar uma mensagem de erro "No technician found with this ID"
    And a resposta deve conter um código de status "404"

  Scenario: Tentar excluir um técnico já removido
    Given I have a coach with name "Felipao", nationality "Brazil", and URL "http://example.com/felipao.jpg"
    When I create the coach
    And I delete the coach by ID
    When o usuário tenta excluir o técnico novamente pelo ID
    Then o sistema deve retornar um erro com o status "404"