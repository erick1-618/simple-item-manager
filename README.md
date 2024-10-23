# SimpleManager

 Um crud simples com GUI, implementado em Java, com o fim de realizar registros de itens com nome, preço unitário, quantidade (para cálculo de total) e data. O programa foi implementado de modo a fornecer um meio rápido e eficaz de persistir itens em uma base de dados, onde, em uma única linha, o usuário insere todas as informações necessárias facilitando a entrada dos itens, com a possibilidade de, ao final, gerar um relatório em pdf de forma automatizada com todos os dados do registro.

## Como utilizar

Para executar o SimpleManager, execute o "SimpleManager.jar".
1. Ele irá abrir uma janela para você inserir o nome do registro, que será o arquivo criado contendo os itens você irá salvar, e será o mesmo arquivo que você irá consultar os itens salvos. Você pode criar quantos registros você quiser. (O arquivo a ser buscado deve estar no mesmo caminho do executável .jar)
2. Ao abrir um registro novo, ele aparecerá vazio, e com uma linha para digitar os dados na parte inferior. Nessa linha você irá digitar os dados no formato: [NOME] [PREÇO UNITÁRIO] [QUANTIDADE] [DATA NO FORMATO DDMMAAAA]
3. Aperte enter ou o botão "adicionar item" para salvar esse item.
4. Para excluir um item, basta apertar o botão em vermelho "x" para excluí-lo do registro. Caso queira remover todos, pressione o botão "Remover todos".
5. Você pode gerar um pdf com um relatório contendo a relação de valores em todos os anos e meses inseridos no registro, com média anual, mensal e total. Para isso basta selecionar o botão "Gerar Relatório" e escolher a pasta de destino em que será gerado o documento.
6. Se você quiser voltar e continuar a inserir em um registro mais tarde, basta abrir o executável e escrever o nome do registro que você quer abrir, conforme a observação do passo 1.

Obs: caso o programa não siga ao passo 2, tente executar o .jar pelo cmd, utilizado o comando:
```
java -jar SimpleManager.jar
```
### Tela principal
<div style='display:inline'>
 <a href="https://ibb.co/FhTpJqr"><img src="https://i.ibb.co/S6CS07j/Imagem-do-Whats-App-de-2024-10-23-s-10-17-24-ab07c1f2.jpg" alt="Imagem-do-Whats-App-de-2024-10-23-s-10-17-24-ab07c1f2" border="0" width="476" height="310"></a><br /><a target='_blank' href='https://pt-br.imgbb.com/'></a><br />

### Relatório gerado
<a href="https://ibb.co/F3vqkhP"><img src="https://i.ibb.co/XLBkfWw/Captura-de-tela-2024-10-23-102038.png" alt="Captura-de-tela-2024-10-23-102038" border="0" width="279" height="368"></a><br /><a target='_blank' href='https://pt-br.imgbb.com/'></a><br/>
 
</div>

## Tecnologias Utilizadas
- **Java 21**
- **Swing**: para a interface gráfica.
- **iTextPDF**: para gerar relatórios em formato PDF.
- **Maven**: para gerenciamento de dependências e build do projeto.

## Limitações
A principal limitação do SimpleManager é o fato de utilizar a serialização como meio de persistência, que não é o mais otimizado, e que pode acarretar em um desempenho lento mediante o aumento no volume de itens registrados.

