package io.github.piponsio.smartfinances_api.service.transaction;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.piponsio.smartfinances_api.dto.request.TransactionFilterDto;
import io.github.piponsio.smartfinances_api.dto.request.TransactionRequestDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionResponseDto;
import io.github.piponsio.smartfinances_api.dto.response.TransactionSummaryDto;
import io.github.piponsio.smartfinances_api.entity.Category;
import io.github.piponsio.smartfinances_api.entity.Transaction;
import io.github.piponsio.smartfinances_api.entity.User;
import io.github.piponsio.smartfinances_api.enums.type;
import io.github.piponsio.smartfinances_api.repository.CategoryRepository;
import io.github.piponsio.smartfinances_api.repository.TransactionRepository;
import io.github.piponsio.smartfinances_api.utils.AuthUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AuthUser authUser;

    @Override
    @Transactional
    public void createTransaction(TransactionRequestDto transactionRequestDto) {
        User user = authUser.getAuthenticatedUser();
        Transaction transaction = new Transaction();
        Category category = getCategory(transactionRequestDto.getCategoryId(), user.getId());
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setCategory(category);
        transaction.setDescription(transactionRequestDto.getDescription());
        transaction.setType(transactionRequestDto.getType());
        transaction.setUser(user);
        transaction.setDate(transactionRequestDto.getDate());
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactions(TransactionFilterDto filterDto) {
        User user = authUser.getAuthenticatedUser();
        Specification<Transaction> spec = getTransactionSpecification(filterDto, user.getId());

        return transactionRepository.findAll(spec)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public TransactionResponseDto getTransaction(Long id) {
        Transaction transaction = findTransactionByIdAndUserId(id, authUser.getAuthenticatedUser().getId());

        return mapToResponseDto(transaction);
    }

    @Override
    @Transactional
    public void updateTransaction(Long id, TransactionRequestDto request) {
        User user = authUser.getAuthenticatedUser();
        Transaction transaction = findTransactionByIdAndUserId(id, user.getId());

        Category category = getCategory(request.getCategoryId(), user.getId());

        transaction.setAmount(request.getAmount());
        transaction.setCategory(category);
        transaction.setDate(request.getDate());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());

        transactionRepository.save(transaction);

    }

    @Override
    @Transactional
    public void deleteTransaction(Long id) {
        User user = authUser.getAuthenticatedUser();
        Transaction transaction = findTransactionByIdAndUserId(id, user.getId());

        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionSummaryDto getSummary(int month, int year) {
        User user = authUser.getAuthenticatedUser();
        List<Transaction> allTransactions = transactionRepository.findByUserId(user.getId());
        BigDecimal totalIncome = new BigDecimal(0);
        BigDecimal totalExpenses = new BigDecimal(0);

        List<Transaction> filteredTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getDate().getMonthValue() == month
                        && transaction.getDate().getYear() == year)
                .toList();

        for (Transaction transaction : filteredTransactions) {
            if (transaction.getType() == type.INCOME) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else if (transaction.getType() == type.EXPENSE) {
                totalExpenses = totalExpenses.add(transaction.getAmount());
            }
        }

        BigDecimal balance = totalIncome.subtract(totalExpenses);

        TransactionSummaryDto summaryDto = new TransactionSummaryDto();
        summaryDto.setBalance(balance);
        summaryDto.setTotalExpenses(totalExpenses);
        summaryDto.setTotalIncome(totalIncome);
        summaryDto.setTransactionCount(allTransactions.size());

        return summaryDto;
    }

    private TransactionResponseDto mapToResponseDto(Transaction transaction) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        Category category = transaction.getCategory();
        responseDto.setId(transaction.getId());
        responseDto.setAmount(transaction.getAmount());
        responseDto.setCategoryName(category.getName());
        responseDto.setDate(transaction.getDate());
        responseDto.setDescription(transaction.getDescription());
        responseDto.setType(transaction.getType());
        return responseDto;
    }

    private Transaction findTransactionByIdAndUserId(Long id, Long userId) {
        return transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transaction not found or does not belong to user"));
    }

    private Category getCategory(Long categoryId, Long userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found or does not belong to user"));
    }

    private Specification<Transaction> getTransactionSpecification(TransactionFilterDto filterDto, Long userId) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            predicates.getExpressions().add(cb.equal(root.get("user").get("id"), userId));

            // Apply optional filters
            if (filterDto.getCategoryId() != null) {
                predicates.getExpressions().add(cb.equal(root.get("category").get("id"), filterDto.getCategoryId()));
            }
            if (filterDto.getType() != null) {
                predicates.getExpressions().add(cb.equal(root.get("type"), filterDto.getType()));
            }
            if (filterDto.getDateFrom() != null) {
                predicates.getExpressions().add(cb.greaterThanOrEqualTo(root.get("date"), filterDto.getDateFrom()));
            }
            if (filterDto.getDateTo() != null) {
                predicates.getExpressions().add(cb.lessThanOrEqualTo(root.get("date"), filterDto.getDateTo()));
            }
            if (filterDto.getMinAmount() != null) {
                predicates.getExpressions().add(cb.greaterThanOrEqualTo(root.get("amount"), filterDto.getMinAmount()));
            }
            if (filterDto.getMaxAmount() != null) {
                predicates.getExpressions().add(cb.lessThanOrEqualTo(root.get("amount"), filterDto.getMaxAmount()));
            }
            if (filterDto.getDescription() != null && !filterDto.getDescription().isEmpty()) {
                predicates.getExpressions().add(
                        cb.like(cb.lower(root.get("description")),
                                "%" + filterDto.getDescription().toLowerCase() + "%"));
            }

            return predicates;
        };
    }
}
