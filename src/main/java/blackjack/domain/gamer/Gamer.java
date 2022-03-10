package blackjack.domain.gamer;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static blackjack.domain.BlackJackGame.POSSIBLE_MAX_VALUE;

public class Gamer {
    private final Name name;

    private final List<Card> cards;

    public Gamer(String name) {
        this.name = new Name(name);
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int getCardsNumberSum() {
        int sum = getSumExceptAce();
        List<Card> aces = getAces();
        return getSumNotToBurst(sum, aces);
    }

    private int getSumExceptAce() {
        return cards.stream()
                .filter(card -> !card.isAce())
                .mapToInt(Card::getValue)
                .sum();
    }

    private List<Card> getAces() {
        return cards.stream()
                .filter(Card::isAce)
                .collect(Collectors.toList());
    }

    private int getSumNotToBurst(int sum, List<Card> aces) {
        for (Card ace : aces) {
            sum += selectAceValue(sum, ace);
        }
        return sum;
    }

    private int selectAceValue(int sum, Card ace) {
        validateAceCard(ace);
        if (ace.getValue() + sum > POSSIBLE_MAX_VALUE) {
            return CardNumber.LOWER_ACE_VALUE;
        }
        return ace.getValue();
    }

    private void validateAceCard(Card ace) {
        if (!ace.isAce()) {
            throw new IllegalArgumentException("입력받은 값이 에이스 카드가 아닙니다.");
        }
    }

    public Name getName() {
        return name;
    }
}