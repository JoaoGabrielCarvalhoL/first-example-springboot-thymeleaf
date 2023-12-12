package br.com.joaogabriel.expenses.service.impl;

import br.com.joaogabriel.expenses.entity.Expense;
import br.com.joaogabriel.expenses.exception.ResourceNotFoundException;
import br.com.joaogabriel.expenses.mapper.ExpenseMapper;
import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import br.com.joaogabriel.expenses.repository.ExpenseRepository;
import br.com.joaogabriel.expenses.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public ExpenseGetResponse findById(UUID id) {
        logger.info("Getting expense by id {}", id);
        return this.expenseRepository.findById(id).filter(Expense::getActive)
                .map(expenseMapper::toExpenseGetResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found into database. Id: " + id));
    }

    @Override
    public Expense findEntityById(UUID id) {
        logger.info("Getting entity by id {}", id);
        return this.expenseRepository.findById(id)
                .filter(Expense::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found into database. Id: " + id));
    }

    @Override
    public ExpenseGetResponse save(ExpensePostRequest expensePostRequest) {
        logger.info("Saving expense into database {}", expensePostRequest);
        Expense saved = this.expenseRepository.save(expenseMapper.toExpense(expensePostRequest));
        return expenseMapper.toExpenseGetResponse(saved);
    }

    @Override
    public ExpenseGetResponse update(ExpensePutRequest expensePutRequest) {
        logger.info("Updating expense into database {}", expensePutRequest);
        Expense updated = this.expenseRepository.save(expenseMapper.toExpense(expensePutRequest));
        return expenseMapper.toExpenseGetResponse(updated);
    }

    @Override
    public ExpenseGetResponse findByName(String name) {
        logger.info("Getting expense by name {}", name);
        Expense result = this.expenseRepository.findByNameAndIsActive(name, true);
        if(Objects.isNull(result)) {
            throw new ResourceNotFoundException("Expense not found into database. Name: " + name);
        }
        return expenseMapper.toExpenseGetResponse(result);
    }

    @Override
    public ExpenseGetResponse findByDescription(String description) {
        logger.info("Getting expense by description {}", description);
        Expense result = this.expenseRepository.findByDescriptionAndIsActive(description, true);
        if(Objects.isNull(result)) {
            throw new ResourceNotFoundException("Expense not found into database. Description: " + description);
        }
        return expenseMapper.toExpenseGetResponse(result);
    }

    @Override
    public Page<ExpenseGetResponse> findAll(Pageable pageable) {
        logger.info("Getting all expenses (pageable)");
        List<ExpenseGetResponse> result = this.expenseRepository.findAll(pageable)
                .filter(Expense::getActive)
                .map(expenseMapper::toExpenseGetResponse)
                .toList();
        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public List<ExpenseGetResponse> findAll() {
        logger.info("Getting all expenses");
        return this.expenseRepository.findAll()
                .stream()
                .filter(Expense::getActive)
                .map(expenseMapper::toExpenseGetResponse).toList();
    }

    @Override
    public void delete(UUID id) {
        logger.info("Deleting expense by id {}", id);
        Expense result = findEntityById(id);
        result.setActive(false);
        this.expenseRepository.save(result);
    }
}
