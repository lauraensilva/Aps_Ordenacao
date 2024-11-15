# Análise de Performance de Algoritmos de Ordenação

Este trabalho foi desenvolvido e apresentado para obtenção de nota na matéria de Atividades Práticas Supervisionadas. O trabalho desenvolvido semestralmente pelos alunos de Ciência da Computação da UNIP teve como tema:

*Desenvolvimento de sistema para análise de performance de algoritmos de ordenação de dados.*

## Foram integradas as seguintes matérias:

- Banco de Dados;

- Linguagem de Programação Orientada a Objetos;

- Estrutura de Dados.

## Componentes do Grupo:
- [@Laura Silva](https://github.com/lauraensilva)

- [@Davi Barbosa](https://github.com/davibcavalcante)

- Savio Lutyeres

## Tecnologias utilizadas:
- Java

- MySQL

## Sobre:
O trabalho consistiu em desenvolver um sistema computacional completo que obtenha imagens do cerrado goiano para fins de fiscalização de crimes ambientais. Essas imagens deveriam ser armazenadas e catalogadas em um banco de dados com campo tipo BLOB, mínimo de 100.000. Em seguida, o grupo deveria selecionar 3 ou mais técnicas de ordenação de dados, efetuar a ordenação das imagens e comparar o desempenho entre elas. Os estágios do desenvolvimento do sistema envolveram os seguintes tópicos:

### Geração/obtenção dos dados para ordenação:
Para fins de ordenação, efetuamos a inclusão das imagens no banco de dados por meio de procedure e trigger. Foi criada uma tabela auxiliar usada apenas no processo de geração dos dados, onde, por meio da procedure, ao inserir uma imagem na tabela auxiliar, esse registro seria repetido outras 100.000 vezes na tabela principal usando estrutura de repetição “While” conforme descrito abaixo:

sql
DELIMITER $$

CREATE PROCEDURE duplicar_registro(
    IN p_nome_imagem VARCHAR(100),
    IN p_localizacao VARCHAR(200),
    IN p_imagem LONGBLOB
)
BEGIN
    DECLARE contador INT DEFAULT 1;
    WHILE contador <= 5000 DO
        INSERT INTO imagens (nome_imagem, localizacao, imagem)
        VALUES (p_nome_imagem, p_localizacao, p_imagem);
        SET contador = contador + 1;
    END WHILE;
END $$

DELIMITER ;


A procedure é chamada dentro da trigger acionada após cada insert feito na tabela auxiliar:

sql

DELIMITER $$

CREATE TRIGGER duplicar_linhas AFTER INSERT ON imagens_aux
FOR EACH ROW
BEGIN
    CALL duplicar_registros(NEW.nome_imagem, NEW.localizacao, NEW.imagem);
END $$

DELIMITER ;


### Processo de Ordenação dos dados:
Foram escolhidos os algoritmos Bubble Sort, Insertion Sort e Quick Sort. Após realizar a ordenação por cada um dos métodos, o algoritmo Quick Sort obteve o melhor desempenho, tanto para dados semiordenados quanto aleatórios, provando ser o mais eficiente entre o grupo.
