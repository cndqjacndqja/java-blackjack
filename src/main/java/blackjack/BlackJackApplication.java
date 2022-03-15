package blackjack;

import blackjack.domain.BlackJackGame;
import blackjack.domain.gamer.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;

import java.util.List;

import static blackjack.domain.gamer.Gamer.INIT_DISTRIBUTION_COUNT;

public class BlackJackApplication {
    public static void main(String[] args) {
        List<String> names = InputView.getNames();
        BlackJackGame blackJackGame = new BlackJackGame(names);
        OutputView.printFirstCards(blackJackGame.getDealer(), blackJackGame.getPlayers());

        drawAdditionalCard(blackJackGame);

        printAdditionalDrawDealer(blackJackGame);
        OutputView.printFinalCards(blackJackGame.getDealer(), blackJackGame.getPlayers());
        OutputView.printFinalResult(blackJackGame.createResult());
    }

    private static void printAdditionalDrawDealer(BlackJackGame blackJackGame) {
        blackJackGame.distributeAdditionalToDealer();
        OutputView.printAdditionalDrawDealer(blackJackGame.getDealerCardSize() - INIT_DISTRIBUTION_COUNT);
    }

    private static void drawAdditionalCard(BlackJackGame blackJackGame) {
        for (Player player : blackJackGame.getPlayers()) {
            printPlayerDrawCard(blackJackGame, player);
        }
    }

    private static void printPlayerDrawCard(BlackJackGame blackJackGame, Player player) {
        while (blackJackGame.isDrawPossible(player, InputView::getAnswerOfAdditionalDraw)) {
            blackJackGame.distributeCardToPlayer(player);
            OutputView.printPlayerCard(player);
        }
    }
}
