package Splitwise;

import java.util.Map;

import Splitwise.model.*;
import Splitwise.orchestration.SplitwiseOrchestration;


public class SplitwiseInterface {
    private final SplitwiseOrchestration orchestration;

    public SplitwiseInterface(){
        this.orchestration = SplitwiseOrchestration.getInstance();
    }

    public void addUser(String name, String email, String phone){
        orchestration.addUser(name, email, phone);
    }

    public void viewUser(String userId){
        orchestration.viewUser(userId);
    }

    public void createGroup(String name){
        orchestration.createGroup(name);
    }

    public void addUserToGroup(String userId, String groupId){
        orchestration.addUserToGroup(userId, groupId);
    }

    public void removeUserFromGroup(String userId, String groupId){
        orchestration.removeUserFromGroup(userId, groupId);
    }

    public void showGroupDetails(String groupId){
        orchestration.showGroupDetails(groupId);
    }

    public void showGroupsForUser(String userId){
        orchestration.showGroupsForUser(userId);
    }

    public void addExpense(String groupId, double expenseAmount, String description, String adderId, String participants, String strategy){
        orchestration.addExpense(groupId, expenseAmount, description, adderId, participants, strategy);
    }

    public void addExpense(String groupId, double expenseAmount, String description, String adderId, String participants, String strategy, Map<String, Double> splitMap){
        orchestration.addExpense(groupId, expenseAmount, description, adderId, participants, strategy, splitMap);
    }

    public void addUsersToExpense(String groupId, String expId, String newUsers){
        orchestration.updateExpense(ExpenseUpdateType.ADD_USER, groupId, expId, newUsers);
    }
    public void removeUsersFromExpense(String groupId, String expId, String newUsers){
        orchestration.updateExpense(ExpenseUpdateType.ADD_USER, groupId, expId, newUsers);
    }
    public void updateAmountForExpense(String groupId, String expId, double amount){
        orchestration.updateExpense(ExpenseUpdateType.UPDATE_AMOUNT, groupId, expId, amount);
    }

    public void viewExpenseSummaryForUser(String userId)
    {
        orchestration.viewExpenseSummaryForUser(userId);
    }

    public void showTransactionHistoryForUser(String userId){
        orchestration.showTransactionHistoryForUser(userId);
    }

    public void showGroupBalanceSummary(String groupId){
        orchestration.showGroupBalanceSummary(groupId);
    }
}


