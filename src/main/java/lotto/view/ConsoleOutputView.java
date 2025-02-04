package lotto.view;

import static lotto.view.PrintMessages.*;
import static lotto.util.Constants.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.domain.model.Lotto;
import lotto.domain.model.LottoNumber;
import lotto.domain.LottoRank;
import lotto.domain.dto.LottoResults;
import lotto.domain.model.Lottos;

public class ConsoleOutputView implements Output {

    @Override
    public void displayInputRequest(String announce) {
        System.out.println(announce);
    }

    @Override
    public void printWinningStatistics(LottoResults winningStatistics) {
        Map<LottoRank, Integer> result = winningStatistics.rankCounts();
        System.out.println(WINNING_STATISTICS_ANNOUNCE.getMessage());
        printEachRankStatistics(result);
    }

    private void printEachRankStatistics(Map<LottoRank, Integer> rankCounts) {
        Arrays.stream(LottoRank.values())
                .forEach(rank -> printRankStatistic(rank, rankCounts.getOrDefault(rank, 0)));
    }

    private void printRankStatistic(LottoRank rank, int count) {
        String formattedMessage = WINNING_STATISTICS_FORMAT.getFormattedWinningStatistics(
                rank.getDescription(), count);
        System.out.println(formattedMessage);
    }

    @Override
    public void printTotalEarningsRate(double earningsRate) {
        System.out.println(TOTAL_EARNINGS_RATE.getFormattedEarningRate(earningsRate));
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(ERROR_PREFIX.getMessage() + errorMessage);
    }


    @Override
    public void printPurchasedLottos(Lottos lottos) {
        List<Lotto> allLotto = lottos.getLottos();
        System.out.println(allLotto.size() + PURCHASED_LOTTOS_COUNT.getMessage());
        allLotto.forEach(lotto -> System.out.println(formatLottoNumbers(lotto)));
    }

    private String formatLottoNumbers(Lotto lotto) {
        return lotto.getNumbers().stream()
                .map(LottoNumber::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(
                        COMMA.getValue() + SPACE.getValue(),
                        LIST_PREFIX.getValue(),
                        LIST_SUFFIX.getValue()));
    }
}
