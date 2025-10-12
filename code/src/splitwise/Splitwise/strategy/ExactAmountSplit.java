package Splitwise.strategy;

import Splitwise.SplitStrategy;
import Splitwise.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ExactAmountSplit implements SplitStrategy {
    private final Map<User, Double> exactAmountMap;

    public ExactAmountSplit(Map<User, Double> exactAmountMap) {
        this.exactAmountMap = exactAmountMap;
    }

    @Override
    public Map<User, Double> split(double amount, List<User> users, User expenseAddingUser) {
        Map<User, Double> owed = new HashMap<>();
        if (users.isEmpty()) return owed;

        for (User user : users) {
            double userAmount = exactAmountMap.getOrDefault(user, 0.0);
            owed.put(user, userAmount);
            user.updateOwedAmount(expenseAddingUser, userAmount);
        }
        System.out.println("Splitting by exact amounts...");
        return owed;
    }
}
