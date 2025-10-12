package Splitwise.strategy;

import Splitwise.model.User;

import java.util.List;
import java.util.Map;

interface SplitStrategy {
    Map<User, Double> split(double amount, List<User> users, User expenseAddingUser);
}
