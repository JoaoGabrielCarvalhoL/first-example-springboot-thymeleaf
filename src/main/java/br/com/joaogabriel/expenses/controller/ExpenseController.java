package br.com.joaogabriel.expenses.controller;

import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import br.com.joaogabriel.expenses.payload.request.ExpensePutRequest;
import br.com.joaogabriel.expenses.payload.request.QueryRequest;
import br.com.joaogabriel.expenses.payload.response.ExpenseGetResponse;
import br.com.joaogabriel.expenses.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public String showExpensesList(Model model) {
        model.addAttribute("expenses", this.expenseService.findAll());
        model.addAttribute("query", new QueryRequest(""));
        model.addAttribute("total", this.expenseService.allExpenses());
        return "expenses-list";
    }

    @GetMapping("/create")
    public String showExpenseForm(Model model) {
        model.addAttribute("expense", new ExpensePostRequest("", "",
                BigDecimal.ZERO, LocalDate.now()));
        return "expense-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("expense") ExpensePostRequest expensePostRequest) {
        this.expenseService.save(expensePostRequest);
        return "redirect:/expenses";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") UUID id) {
        this.expenseService.delete(id);
        return "redirect:/expenses";
    }

    @GetMapping("/update-form")
    public String showUpdateForm(Model model, @RequestParam("id") UUID id) {
        ExpenseGetResponse response = this.expenseService.findById(id);
        model.addAttribute("expense", new ExpensePutRequest(id, response.name(), response.description(),
                response.amount(), response.paidIn()));
        return "expense-form-update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("expense") ExpensePutRequest expensePutRequest) {
        this.expenseService.update(expensePutRequest);
        return "redirect:/expenses";
    }

    @GetMapping("/query")
    public String showQueryForm(Model model) {
        model.addAttribute("query", new QueryRequest(""));
        return "expenses-query-list";
    }

    @GetMapping("/find-by-name")
    public String findByName(@ModelAttribute("query") QueryRequest queryRequest, Model model) {
        List<ExpenseGetResponse> expenses = this.expenseService.findByNameContaining(queryRequest.value());
        model.addAttribute("expenses", expenses);
        return "expenses-query-list";
    }

    @GetMapping("/find-by-description")
    public String findByDescription(@ModelAttribute("query") QueryRequest queryRequest, Model model) {
        model.addAttribute("expenses", this.expenseService.findByDescription(queryRequest.value()));
        return "expenses-query-list";
    }
}
