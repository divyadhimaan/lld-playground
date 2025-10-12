package Splitwise.factory;

import Splitwise.model.Expense;
import Splitwise.model.Group;
import Splitwise.model.User;
import Splitwise.strategy.EqualSplit;
import Splitwise.strategy.ExactAmountSplit;
import Splitwise.strategy.PercentageSplit;
import Splitwise.strategy.SplitStrategy;

import java.util.List;
import java.util.Map;

class ExpenseFactory {
    public Expense createExpense(Group group, double expenseAmount, String description, User expenseAddingUser,
                                 List<User> users, String strategy, Map<User, Double> splitMap) {
        SplitStrategy splitStrategy = null;

        switch (strategy.toLowerCase()) {
            case "equal" -> splitStrategy = new EqualSplit();
            case "percentage" -> {
                if (splitMap == null || splitMap.isEmpty()) {
                    System.out.println("[ERROR] | Percentage map cannot be empty for percentage split");
                    return null;
                }
                splitStrategy = new PercentageSplit(splitMap);
            }
            case "exact" -> {
                if (splitMap == null || splitMap.isEmpty()) {
                    System.out.println("[ERROR] | Exact amount map cannot be empty for exact split");
                    return null;
                }
                splitStrategy = new ExactAmountSplit(splitMap);
            }
            default -> {
                System.out.println("[ERROR] | Pick a valid strategy: equal/percentage/exact");
                return null;
            }
        }

        return new Expense(expenseAmount, group, description, expenseAddingUser, users, splitStrategy);
    }
}
