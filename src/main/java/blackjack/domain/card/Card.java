package blackjack.domain.card;

import java.util.Objects;

public class Card {
    private final CardShape cardShape;
    private final CardNumber cardNumber;

    public Card(CardShape cardShape, CardNumber cardNumber) {
        this.cardShape = cardShape;
        this.cardNumber = cardNumber;
    }

    public boolean isAce() {
        return cardNumber.equals(CardNumber.ACE);
    }

    public int getValue() {
        return cardNumber.getValue();
    }

    public int getAceValue(int sum) {
        return cardNumber.selectAceValue(sum);
    }

    public String getName() {
        return cardNumber.getName();
    }

    public String getShape() {
        return cardShape.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Card card = (Card) o;
        return cardShape == card.cardShape && cardNumber == card.cardNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardShape, cardNumber);
    }
}
