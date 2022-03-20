package blackjack.domain.card;

import static blackjack.domain.result.BlackJackResult.MAX_CARD_VALUE;

public enum CardNumber {
    ACE(11, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "J"),
    QUEEN(10, "Q"),
    KING(10, "K");

    public static final int LOWER_ACE_VALUE = 1;

    private final int value;
    private final String name;

    CardNumber(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public int selectAceValue(int sum) {
        if (this == ACE && this.getValue() + sum > MAX_CARD_VALUE) {
            return LOWER_ACE_VALUE;
        }
        return this.getValue();
    }

    public String getName() {
        return name;
    }
}
