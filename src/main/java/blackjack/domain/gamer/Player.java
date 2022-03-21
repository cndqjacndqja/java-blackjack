package blackjack.domain.gamer;

import blackjack.domain.card.Card;
import blackjack.domain.result.BlackJackResult;

import java.util.List;

import static blackjack.domain.result.BlackJackResult.MAX_CARD_VALUE;

public class Player extends Gamer {
    private final BettingMoney bettingMoney;

    public Player(String name, List<Card> cards, int value) {
        super(name);
        for (Card card : cards) {
            addCard(card);
        }
        bettingMoney = new BettingMoney(value);
    }

    public BlackJackResult match(Dealer dealer) {
        return BlackJackResult.findResult(this, dealer);
    }

    public int getBettingMoney() {
        return bettingMoney.getValue();
    }

    @Override
    public boolean canDraw() {
        return getCardsNumberSum() <= MAX_CARD_VALUE;
    }

    public int getProfit(Dealer dealer) {
        BlackJackResult blackJackResult = match(dealer);
        return blackJackResult.getProfit(bettingMoney.getValue());
    }
}
