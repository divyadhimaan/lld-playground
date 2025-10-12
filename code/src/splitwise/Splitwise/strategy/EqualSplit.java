package Splitwise.strategy;

import Splitwise.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EqualSplit implements SplitStrategy {
    @Override
    public Map<User, Double> split(double amount, List<User> users, User expenseAddingUser) {
        Map<User, Double> owed = new HashMap<>();
        System.out.println("Splitting equally...");
        if (users.isEmpty())
            return owed;

        double perUserAmount = amount / users.size();
        for (User user : users) {
            owed.put(user, perUserAmount);
            user.updateOwedAmount(expenseAddingUser, perUserAmount);
        }
        return owed;
    }
}
