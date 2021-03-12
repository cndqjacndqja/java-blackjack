package blackjack.domain.participants;

import blackjack.dto.GameResult;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Players {

    private final List<Player> players;

    public Players(List<Player> players) {
        validateDuplication(players);
        this.players = new ArrayList<>(players);
    }

    private static void validateDuplication(List<Player> players) {
        long removedDuplicationCount = countDistinctPlayers(players);
        if (players.size() != removedDuplicationCount) {
            throw new IllegalArgumentException("중복된 이름은 사용할 수 없습니다.");
        }
    }

    private static long countDistinctPlayers(List<Player> players) {
        return players.stream()
            .distinct()
            .count();
    }

    public List<Player> unwrap() {
        return new ArrayList<>(players);
    }

    public boolean isNotPrepared() {
        return players.stream()
            .anyMatch(Player::isContinue);
    }

    public GameResult match(Dealer dealer) {
        Map<String, Integer> result = new LinkedHashMap<>();
        players.forEach(player -> result.put(player.getName(), player.matchForProfit(dealer)));

        return new GameResult(result);
    }

    public Player nextPlayerToPrepare() {
        return players.stream()
            .filter(Player::isContinue)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("이미 모든 플레이어가 준비가 되었습니다."));
    }
}