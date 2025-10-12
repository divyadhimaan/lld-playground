package Splitwise.model;

import Splitwise.Expense;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
class Group {
    private final String id;
    private final String name;
    private final Map<String, User> members;
    private final Map<String, Expense> expenses;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.members = new HashMap<>();
        this.expenses = new HashMap<>();
    }

    public void addMember(@NonNull User user) {
        members.put(user.getId(), user);
    }

    public void removeMember(@NonNull User user) {
        members.remove(user.getId());
    }

    public void addExpense(@NonNull Expense exp) {
        expenses.put(exp.getExpenseId(), exp);
        for (User user : exp.getUsers()) {
            user.addExpense(exp);
        }
    }

    public void updateExpense(String expId, Expense exp) {
        expenses.put(expId, exp);
    }

    public Expense getExpenseById(String expId) {
        return expenses.getOrDefault(expId, null);
    }

}
