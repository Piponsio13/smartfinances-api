package io.github.piponsio.smartfinances_api.service.recurring;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecurringTransactionScheduler {

    private final RecurringTransactionService recurringTransactionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void generateDailyTransactions() {
        recurringTransactionService.generateDueTransactions();
    }
}
