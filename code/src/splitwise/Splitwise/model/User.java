package Splitwise.model;

import Splitwise.Expense;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final List<Expense> expenses;
    private final Map<User, Double> owedAmountPerUser;

    public User(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.expenses = new ArrayList<>();
        this.owedAmountPerUser = new HashMap<>();
    }

    public void addExpense(@NonNull Expense expense) {
        expenses.add(expense);
    }

    public void updateOwedAmount(User expenseAddingUser, Double perUserAmount) {
        owedAmountPerUser.merge(expenseAddingUser, perUserAmount, Double::sum);
    }


}
