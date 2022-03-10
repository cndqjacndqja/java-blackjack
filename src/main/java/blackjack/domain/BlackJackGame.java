package blackjack.domain;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardFactory;
import blackjack.domain.gamer.Dealer;
import blackjack.domain.gamer.Gamer;
import blackjack.domain.gamer.Player;
import blackjack.domain.result.GameResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BlackJackGame {

    public static final int POSSIBLE_MAX_VALUE = 21;
    private static final int INIT_DISTRIBUTION_COUNT = 2;
    private static final int ADDITIONAL_DISTRIBUTE_STANDARD = 16;

    private final CardFactory cardFactory;
    private final List<Player> players;
    private final Dealer dealer;

    public BlackJackGame(List<String> names) {
        validateDuplicationNames(names);
        this.cardFactory = new CardFactory(Card.getCards());
        this.dealer = new Dealer();
        this.players = names.stream()
                .map(Player::new)
                .collect(Collectors.toList());
    }

    private void validateDuplicationNames(List<String> names) {
        int count = (int) names.stream()
                .distinct()
                .count();
        if (count != names.size()) {
            throw new IllegalArgumentException("중복된 이름이 존재합니다.");
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
        gamer.addCard(cardFactory.draw());
    }

    public void distributeCardToPlayer(String name) {
        findPlayerByName(name).addCard(cardFactory.draw());
    }

    public Player findPlayerByName(String name) {
        return players.stream()
                .filter(player -> player.isSameName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("플레이어가 존재하지 않습니다."));
    }

    public GameResult createResult(Dealer dealer, List<Player> players) {
        return new GameResult(players, dealer);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public boolean isBurst(String name) {
        Player player = findPlayerByName(name);
        return player.getCardsNumberSum() > POSSIBLE_MAX_VALUE;
    }

    public GameResult createResult() {
        return new GameResult(players, dealer);
    }
}
