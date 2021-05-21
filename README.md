# Android-TIC-TAC-TOE

__Developed in college.__

__Objetivo:__ Testar o aprendizado dos alunos através da aplicação de conhecimentos adquiridos em sala de aula, em uma prática de desenvolvimento de uma aplicação móvel. 
Forma de realização: Os alunos se dividirão em grupos de no máximo 3 elementos, que realizarão a implementação do projeto descrito a seguir:

__O Projeto:__ Jogo da Velha de Selfies

Deverá ser construída uma versão do jogo da velha, na qual, ao invés de usarmos
“xises” e “bolinhas”, serão utilizadas duas selfies dos jogadores. Assim, para começar a
jogar, cada jogador tirará uma foto sua e a utilizará como “ícone” no jogo. Quando o jogo
terminar, havendo vencedor, a foto do mesmo deverá ser apresentada em uma nova tela.

__Modo de funcionamento do jogo__
O modo de funcionamento do jogo será o seguinte:
Ilustração 1: A tela do jogo e seus botões.

• Logo ao ser aberto o jogo exibirá todos os campos em branco, e deverá apresentar os números 1 e 2 sobre os respectivos botões e a letra “C” sobre o botão 3.

• O primeiro jogador pressiona o botão 1 e tira uma selfie, que passa a ser apresentada sobre o botão 1.

• O segundo jogador pressiona o botão 2 e tira uma selfie, que passa a ser apresentada sobre o botão 2.

• Os jogadores decidem quem começará o jogo. Essa pessoa pressionará o botãocontendo sua foto, e depois selecionará uma posição do tabuleiro (# ).

• Percebam que os botões 1 e 2 tem dois estados : Sem e com foto, e seucomportamento é diferente baseado no seu estado.

• O jogo não pode começar sem que sejam obtidas duas selfies.

• O próximo jogador não precisa clicar sobre sua foto, poderá clicar diretamente notabuleiro.

• Volta para o primeiro jogador, que não precisa mais clicar sobre sua foto, clicandodiretamente no tabuleiro.

• Esses procedimentos se repetem até que haja um vencedor ou um empate:

    ◦ No caso de empate, deverá ser exibida uma mensagem de alerta informando asituação, e depois o tabuleiro deverá ser “limpo” para possibilitar uma novarodada.
   
    ◦ Em caso de vitória de algum jogador, deverá ser exibida durante 3 segundos uma marcação indicando a jogada vencedora, como demonstrado em verde na Ilustração 1. Após esse tempo, deverá ser aberta uma janela mostrando a foto ampliada do vencedor. Ao voltar-se dessa tela, pode-se iniciar uma nova partida.
    
• Para iniciar uma nova partida, basta decidir quem começará jogando, clicar o botão 1 ou 2 com a foto do jogador e depois selecionar uma posição no tabuleiro. O jogo procede normalmente a partir daí.

• O botão 3 serve para reiniciar o jogo, tirando novas selfies dos dois jogadores. Ao pressioná-lo, os botões 1 e 2 voltam a exibir seus números e o tabuleiro é “limpo”.
