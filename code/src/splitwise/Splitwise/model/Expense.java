package Splitwise.model;

import Splitwise.SplitStrategy;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
class Expense {
    private static final AtomicInteger expenseIdCounter = new AtomicInteger(0);
    private final String expenseId;
    private double amount;
    private final String description;
    private final User expenseAddingUser;
    private final Group group;
    private final List<User> users;
    private final SplitStrategy strategy;


    public Expense(double amount, Group group, String description, User adder, List<User> users, SplitStrategy strategy) {
        this.expenseId = "EXP_" + expenseIdCounter;
        this.amount = amount;
        this.description = description;
        this.expenseAddingUser = adder;
        this.group = group;
        this.users = users;
        this.strategy = strategy;
    }

    public void addUserToExpense(@NonNull User user) {
        users.add(user);
    }

    public void removeUserFromExpense(@NonNull User user) {
        if (users.contains(user)) {
            users.remove(user);
            return;
        }
        System.out.println("[ERROR]: User: " + user.getName() + " is not part of this expense");
    }

    public void updateAmount(double amount) {
        this.amount = amount;
    }

    public Map<User, Double> calculateSplit() {
        return strategy.split(amount, users, expenseAddingUser);
    }
}
