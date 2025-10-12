package Splitwise.strategy;

import Splitwise.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PercentageSplit implements SplitStrategy {

    private final Map<User, Double> percentageMap;

    public PercentageSplit(Map<User, Double> percentageMap) {
        this.percentageMap = percentageMap;
    }

    @Override
    public Map<User, Double> split(double amount, List<User> users, User expenseAddingUser) {
        Map<User, Double> owed = new HashMap<>();
        if (users.isEmpty()) return owed;

        for (User user : users) {
            double percent = percentageMap.getOrDefault(user, 0.0);
            double userAmount = amount * percent / 100.0;
            owed.put(user, userAmount);
            user.updateOwedAmount(expenseAddingUser, userAmount);
        }
        System.out.println("Splitting by percentage...");
        return owed;
    }
}
