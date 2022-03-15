package blackjack.domain.result;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.CardShape;
import blackjack.domain.gamer.Dealer;
import blackjack.domain.gamer.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BlackJackRefereeTest {

    @Test
    @DisplayName("아무도 버스트가 아닐 때 플레이어 게임 결과 확인")
    void playerResultCreateNotBurst2() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");
        Player jason = new Player("jason");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.THREE));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.NINE));
        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.EIGHT));

        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TWO));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.EIGHT));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));

        jason.addCard(new Card(CardShape.CLOVER, CardNumber.SEVEN));
        jason.addCard(new Card(CardShape.CLOVER, CardNumber.KING));

        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi, jason), dealer);

        Map<String, BlackJackResult> playerResults = blackJackReferee.getPlayerResult();

        BlackJackResult pobiResult = playerResults.get("pobi");
        BlackJackResult jasonResult = playerResults.get("jason");

        assertThat(pobiResult).isEqualTo(BlackJackResult.WIN);
        assertThat(jasonResult).isEqualTo(BlackJackResult.LOSE);
    }

    @Test
    @DisplayName("딜러가 버스트일 때, 플레이어 게임 결과 확인")
    void playerResultCreateBurst() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");
        Player jason = new Player("jason");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.THREE));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.NINE));
        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.ACE));

        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.FOUR));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.EIGHT));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TEN));

        jason.addCard(new Card(CardShape.CLOVER, CardNumber.SEVEN));
        jason.addCard(new Card(CardShape.CLOVER, CardNumber.KING));

        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi, jason), dealer);

        Map<String, BlackJackResult> playerResults = blackJackReferee.getPlayerResult();

        BlackJackResult pobiResult = playerResults.get("pobi");
        BlackJackResult jasonResult = playerResults.get("jason");

        assertThat(pobiResult).isEqualTo(BlackJackResult.LOSE);
        assertThat(jasonResult).isEqualTo(BlackJackResult.WIN);
    }

    @Test
    @DisplayName("딜러 게임 결과 확인")
    void dealerResultCreate() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");
        Player jason = new Player("jason");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.THREE));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.NINE));
        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.EIGHT));

        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TWO));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.EIGHT));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));

        jason.addCard(new Card(CardShape.CLOVER, CardNumber.SEVEN));
        jason.addCard(new Card(CardShape.CLOVER, CardNumber.KING));

        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi, jason), dealer);

        Map<BlackJackResult, Integer> dealerResult = blackJackReferee.getDealerResult();
        int winningCount = dealerResult.get(BlackJackResult.WIN);
        int losingCount = dealerResult.get(BlackJackResult.LOSE);
        int drawingCount = dealerResult.get(BlackJackResult.DRAW);

        assertThat(List.of(winningCount, losingCount, drawingCount))
                .containsExactly(1, 1, 0);
    }

    @Test
    @DisplayName("플레이어가 블랙잭이 아닌 21이지만, 딜러가 블랙잭일 경우 딜러의 승리")
    void blackJackDealerWin() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));

        pobi.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TEN));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));
        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi), dealer);

        Map<BlackJackResult, Integer> dealerResult = blackJackReferee.getDealerResult();
        int winningCount = dealerResult.get(BlackJackResult.WIN);
        int losingCount = dealerResult.get(BlackJackResult.LOSE);
        int drawingCount = dealerResult.get(BlackJackResult.DRAW);

        assertThat(List.of(winningCount, losingCount, drawingCount))
                .containsExactly(1, 0, 0);
    }

    @Test
    @DisplayName("딜러가 블랙잭이 아닌 21이지만, 플레이어가 블랙잭일 경우 딜러의 승리")
    void blackJackPlayerWin() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));

        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TEN));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));
        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi), dealer);

        Map<BlackJackResult, Integer> dealerResult = blackJackReferee.getDealerResult();
        int winningCount = dealerResult.get(BlackJackResult.WIN);
        int losingCount = dealerResult.get(BlackJackResult.LOSE);
        int drawingCount = dealerResult.get(BlackJackResult.DRAW);

        assertThat(List.of(winningCount, losingCount, drawingCount))
                .containsExactly(0, 1, 0);
    }

    @Test
    @DisplayName("플레이어, 딜러 모두 블랙잭이면 무승부이다.")
    void blackJackDraw() {
        Dealer dealer = new Dealer();
        Player pobi = new Player("pobi");

        dealer.addCard(new Card(CardShape.DIAMOND, CardNumber.TEN));
        dealer.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));

        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.TEN));
        pobi.addCard(new Card(CardShape.CLOVER, CardNumber.ACE));
        BlackJackReferee blackJackReferee = new BlackJackReferee(List.of(pobi), dealer);

        Map<BlackJackResult, Integer> dealerResult = blackJackReferee.getDealerResult();
        int winningCount = dealerResult.get(BlackJackResult.WIN);
        int losingCount = dealerResult.get(BlackJackResult.LOSE);
        int drawingCount = dealerResult.get(BlackJackResult.DRAW);

        assertThat(List.of(winningCount, losingCount, drawingCount))
                .containsExactly(0, 0, 1);
    }
}
