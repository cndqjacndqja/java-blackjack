package blackjack.controller;

import blackjack.domain.Answer;
import blackjack.domain.card.Card;
import blackjack.domain.card.Deck;
import blackjack.domain.gamer.Dealer;
import blackjack.domain.gamer.Gamer;
import blackjack.domain.gamer.Player;
import blackjack.domain.result.BlackJackReferee;

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class BlackJackController {

    public static final int MAX_CARD_VALUE = 21;
    private static final int INIT_DISTRIBUTION_COUNT = 2;
    private static final int ADDITIONAL_DISTRIBUTE_STANDARD = 16;

    private static final String DUPLICATION_NAME_ERROR = "중복된 이름이 존재합니다.";
    private static final String NOT_EXIST_PLAYER_ERROR = "플레이어가 존재하지 않습니다.";

    private final Deck deck;
    private final List<Player> players;
    private final Dealer dealer;

    public BlackJackController(List<String> names) {
        validateDuplicationNames(names);
        this.deck = new Deck(Card.getCards());
        this.dealer = new Dealer();
        this.players = names.stream()
                .map(Player::new)
                .collect(Collectors.toList());
        distributeFirstCards();
    }

    private void validateDuplicationNames(List<String> names) {
        int count = (int) names.stream()
                .distinct()
                .count();
        if (count != names.size()) {
            throw new IllegalArgumentException(DUPLICATION_NAME_ERROR);
        }
    }

    public void distributeFirstCards() {
        for (int i = 0; i < INIT_DISTRIBUTION_COUNT; i++) {
            distributeCard(dealer);
            players.forEach(this::distributeCard);
        }
    }

    public int distributeAdditionalToDealer() {
        int count = 0;
        while (!dealer.isOverThan(ADDITIONAL_DISTRIBUTE_STANDARD)) {
            distributeCard(dealer);
            count++;
        }
        return count;
    }

    private void distributeCard(Gamer gamer) {
        gamer.addCard(deck.draw());
    }

    public void distributeCardToPlayer(String name) {
        findPlayerByName(name).addCard(deck.draw());
    }

    public Player findPlayerByName(String name) {
        return players.stream()
                .filter(player -> player.isSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_PLAYER_ERROR));
    }

    public boolean isBurst(String name) {
        Player player = findPlayerByName(name);
        return player.getCardsNumberSum() > MAX_CARD_VALUE;
    }

    public boolean isDrawPossible(String name, UnaryOperator<String> operator) {
        return !isBurst(name) && Answer.from(operator.apply(name)).isYes();
    }

    public BlackJackReferee createResult() {
        return new BlackJackReferee(players, dealer);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
