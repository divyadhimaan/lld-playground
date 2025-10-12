package Splitwise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.NonNull;


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

class SplitwiseOrchestration {
    private static SplitwiseOrchestration orchestration;
    private final UserInventory userInventory;
    private final GroupInventory groupInventory;

    private final UserFactory userFactory;
    private final GroupFactory groupFactory;
    private final ExpenseFactory expenseFactory;

    private final Logger logger;


    private SplitwiseOrchestration(){
        this.userInventory = UserInventory.getInstance();
        this.groupInventory = GroupInventory.getInstance();
        this.logger = Logger.getInstance();

        this.userFactory = new UserFactory();
        this.groupFactory = new GroupFactory();
        this.expenseFactory = new ExpenseFactory();
    }

    public static synchronized SplitwiseOrchestration getInstance() {
        if(orchestration == null){
            orchestration = new SplitwiseOrchestration();
        }
        return orchestration;
    }

    public void addUser(String name, String email, String phone){
        User user = userFactory.createUser(name, email, phone);
        userInventory.addUser(user);
        System.out.println("[INFO] | User added successfully: " + user.getId());

    }
    public void viewUser(String userId){
        User user = userInventory.getUser(userId);
        if(user != null){
            logger.displayUserDetails(user);
        }
    }

    public void createGroup(String name){
        Group group = groupFactory.createGroup(name);
        groupInventory.addGroup(group);
        System.out.println("[INFO] | Group created successfully: " + group.getId());
    }

    public void addUserToGroup(String userId, String groupId)
    {
        User user = userInventory.getUser(userId);
        Group group = groupInventory.getGroup(groupId);
        if(user != null && group != null){
            group.addMember(user);
            userInventory.addGroupForUser(user,group);
            System.out.println("[INFO] | User " + userId + " added to group " + groupId + " successfully.");
        }
    }

    public void removeUserFromGroup(String userId, String groupId){
        User user = userInventory.getUser(userId);
        Group group = groupInventory.getGroup(groupId);
        if(user != null && group != null){
            group.removeMember(user);
            System.out.println("[INFO] | User " + userId + " removed from group " + groupId + " successfully.");
        }
    }

    public void showGroupDetails(String groupId) {
        Group group = groupInventory.getGroup(groupId);
        if(group != null){
            logger.displayGroupDetails(group);
        }
    }

    public void showGroupsForUser(String userId){
        User user = userInventory.getUser(userId);
        if(user != null){
            List<Group> groups = userInventory.getGroupsForUser(user);
            if(!groups.isEmpty()){
                logger.displayGroupsForUser(user, groups);
            }
        }
    }

    // No split map (for Equal split)

    public void addExpense(String groupId, double expenseAmount, String description, String adderId,
                           String participants, String strategy) {
        addExpenseInternal(groupId, expenseAmount, description, adderId, participants, strategy, null);
    }

    // With split map (for Percentage/Exact splits)
    public void addExpense(String groupId, double expenseAmount, String description, String adderId,
                           String participants, String strategy, Map<String, Double> splitMap) {
        addExpenseInternal(groupId, expenseAmount, description, adderId, participants, strategy, splitMap);
    }

    public void addExpenseInternal(String groupId, double expenseAmount, String description, String adderId, String participants,
                           String strategy, Map<String, Double> splitMap){
        Group group = groupInventory.getGroup(groupId);
        User expenseAddingUser = userInventory.getUser(adderId);

        if(group == null || expenseAddingUser==null)
            return;

        List<User> users = getParticipantsInExpense(group, participants);
        if(users.isEmpty()){
            System.out.println("[ERROR]: Failed adding the expense - Invalid User provided");
            return;
        }

        Map<User, Double> userSplitMap = null;
        if (splitMap != null && !splitMap.isEmpty()) {
            userSplitMap = new HashMap<>();
            for (Map.Entry<String, Double> entry : splitMap.entrySet()) {
                User user = userInventory.getUser(entry.getKey());
                if (user == null || !verifyUserInGroup(group, user)) {
                    System.out.println("[ERROR]: Invalid user in split map: " + entry.getKey());
                    return; // or skip invalid user
                }
                userSplitMap.put(user, entry.getValue());
            }
        }


        Expense expense = expenseFactory.createExpense(group, expenseAmount, description, expenseAddingUser, users, strategy, userSplitMap);
        if(expense == null) {
            System.out.println("[ERROR]: Failed adding the expense");
            return;
        }
        group.addExpense(expense);
        Map<User, Double> splitAmounts = expense.calculateSplit();


        logger.displayExpense(ExpenseType.CREATED,expense, splitAmounts);
    }

    private List<User> getParticipantsInExpense(Group group, String participants){
        List<User> users = new ArrayList<>();
        List<String> participantsList = List.of(participants.split(","));

        for(String participantId: participantsList){
            User currentUser = userInventory.getUser(participantId);

            if(currentUser == null){
                System.out.println("[ERROR]: User id("+participantId+") not valid.");
                return new ArrayList<>();
            }
            if(!verifyUserInGroup(group, currentUser)){
                System.out.println("[ERROR]: User "+participantId+" not part of the specified group");
            }
            users.add(currentUser);
        }
        return users;
    }

    private boolean verifyUserInGroup(Group group, User user){
        if(group.getMembers().containsKey(user.getId())){
            return true;
        }
        return false;
    }

    public void updateExpense(ExpenseUpdateType expenseUpdateType, String groupId, String expenseId, Object value) {
        Group group = groupInventory.getGroup(groupId);
        if (group == null) return;

        Expense expense = group.getExpenseById(expenseId);
        if (expense == null) {
            System.out.println("[ERROR]: Trying to update an invalid expense");
            return;
        }

        switch (expenseUpdateType) {
            case ADD_USER -> {
                if (value instanceof String usersStr) {
                    List<User> newUsers = getParticipantsInExpense(group, usersStr);
                    for (User user : newUsers) expense.addUserToExpense(user);
                }
            }
            case REMOVE_USER -> {
                if (value instanceof String usersStr) {
                    List<User> removeUsers = getParticipantsInExpense(group, usersStr);
                    for (User user : removeUsers) expense.removeUserFromExpense(user);
                }
            }
            case UPDATE_AMOUNT -> {
                if (value instanceof Double newAmount) {
                    expense.updateAmount(newAmount); // Add setter in Expense
                }
            }
            default -> System.out.println("[ERROR]: Invalid expense update type");
        }

        group.updateExpense(expenseId, expense);
        Map<User, Double> splitAmounts = expense.calculateSplit();
        logger.displayExpense(ExpenseType.UPDATED, expense, splitAmounts);
    }

    public void viewExpenseSummaryForUser(String userId){
        User user = userInventory.getUser(userId);
        if(user == null)
            return;
        Map<User, Double> expensesPerUser = user.getOwedAmountPerUser();
        logger.displayExpenseForUser(expensesPerUser, user);
    }

    public void showTransactionHistoryForUser(String userId){
        User user = userInventory.getUser(userId);
        if(user == null)
            return;

        List<Expense> expenses = user.getExpenses();
        logger.displayAllExpensesForUser(expenses, user);
    }

    public void showGroupBalanceSummary(String groupId) {
        Group group = groupInventory.getGroup(groupId);
        if (group == null) {
            System.out.println("[ERROR] | Group not found: " + groupId);
            return;
        }

        Map<String, Double> balanceMap = new HashMap<>(); // key = "UserA->UserB"

        for (Expense expense : group.getExpenses().values()) {
            Map<User, Double> splitAmounts = expense.calculateSplit();
            User payer = expense.getExpenseAddingUser();

            for (Map.Entry<User, Double> entry : splitAmounts.entrySet()) {
                User participant = entry.getKey();
                double amount = entry.getValue();
                if (participant.equals(payer)) continue; // skip payer themselves

                String key = participant.getId() + "->" + payer.getId();
                balanceMap.put(key, balanceMap.getOrDefault(key, 0.0) + amount);
            }
        }

        logger.displayGroupBalances(balanceMap, group);
    }



}

class UserFactory {
    private final AtomicInteger userIdCounter = new AtomicInteger(0);
    public User createUser(String name, String email, String phone){
        String id = generateUserId(name, phone);
        return new User(id, name, email, phone);
    }

    private String generateUserId(String name, String phone){
        int count = userIdCounter.getAndIncrement();
        return name+"_"+ phone.substring(phone.length()-4) + "_" + count;
    }
}

@Getter
class User {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;
    private final List<Expense> expenses;
    private final Map<User, Double> owedAmountPerUser;

    public User(String id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.expenses = new ArrayList<>();
        this.owedAmountPerUser = new HashMap<>();
    }
    public void addExpense(@NonNull Expense expense){
        expenses.add(expense);
    }

    public void updateOwedAmount(User expenseAddingUser, Double perUserAmount){
        owedAmountPerUser.merge(expenseAddingUser, perUserAmount, Double::sum);
    }


}

class UserInventory {
    private final Map<String, User> userMap;
    private final Map<User, List<Group>> userGroupMap;
    private static UserInventory inventory;

    private UserInventory(){
        this.userMap = new HashMap<>();
        this.userGroupMap = new HashMap<>();
    }

    public static synchronized UserInventory getInstance(){
        if(inventory == null){
            inventory = new UserInventory();
        }
        return inventory;
    }

    public void addUser(User user){
        userMap.put(user.getId(), user);
    }

    public User getUser(String id){
        if(!userMap.containsKey(id)){
            System.out.println("[ERROR] | User not found: " + id);
            return null;
        }
        return userMap.get(id);
    }

    public void addGroupForUser(User user,Group group){
        userGroupMap.putIfAbsent(user, new ArrayList<>());
        userGroupMap.get(user).add(group);
    }

    public List<Group> getGroupsForUser(User user){
        return userGroupMap.getOrDefault(user, new ArrayList<>());
    }

}

class GroupFactory {
    private final AtomicInteger groupIdCounter = new AtomicInteger(0);
    public Group createGroup(String name){
        String id = generateUserId(name);
        return new Group(id, name);
    }

    private String generateUserId(String name){
        int count = groupIdCounter.getAndIncrement();
        return name+"_" + count;
    }
}

@Getter
class Group {
    private final String id;
    private final String name;
    private final Map<String, User> members;
    private final Map<String, Expense> expenses;

    public Group(String id, String name){
        this.id = id;
        this.name = name;
        this.members = new HashMap<>();
        this.expenses = new HashMap<>();
    }
    public void addMember(@NonNull User user){
        members.put(user.getId(), user);
    }
    public void removeMember(@NonNull User user){
        members.remove(user.getId());
    }
    public void addExpense(@NonNull Expense exp){
        expenses.put(exp.getExpenseId(), exp);
        for(User user: exp.getUsers()){
            user.addExpense(exp);
        }
    }
    public void updateExpense(String expId, Expense exp){
        expenses.put(expId, exp);
    }

    public Expense getExpenseById(String expId){
        return expenses.getOrDefault(expId, null);
    }

}

class GroupInventory {
    private final Map<String, Group> groupMap = new HashMap<>();
    private static GroupInventory inventory;
    private GroupInventory(){}

    public static synchronized GroupInventory getInstance(){
        if(inventory == null){
            inventory = new GroupInventory();
        }
        return inventory;
    }

    public void addGroup(Group group){
        groupMap.put(group.getId(), group);
    }

    public Group getGroup(String id){
        if(!groupMap.containsKey(id)){
            System.out.println("[ERROR] | Group not found: " + id);
            return null;
        }
        return groupMap.get(id);
    }
}

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


    public Expense(double amount, Group group, String description, User adder, List<User> users,SplitStrategy strategy){
        this.expenseId = "EXP_"+expenseIdCounter;
        this.amount = amount;
        this.description = description;
        this.expenseAddingUser = adder;
        this.group = group;
        this.users = users;
        this.strategy = strategy;
    }

    public void addUserToExpense(@NonNull User user){
        users.add(user);
    }

    public void removeUserFromExpense(@NonNull User user){
        if(users.contains(user)){
            users.remove(user);
            return;
        }
        System.out.println("[ERROR]: User: "+user.getName()+" is not part of this expense");
    }

    public void updateAmount(double amount){
        this.amount = amount;
    }

    public Map<User, Double> calculateSplit(){
        return strategy.split(amount, users, expenseAddingUser);
    }
}

interface SplitStrategy{
    Map<User, Double> split(double amount, List<User> users, User expenseAddingUser);
}

class EqualSplit implements SplitStrategy {
    @Override
    public Map<User, Double> split(double amount, List<User> users, User expenseAddingUser){
        Map<User, Double> owed = new HashMap<>();
        System.out.println("Splitting equally...");
        if(users.isEmpty())
            return owed;

        double perUserAmount = amount/users.size();
        for(User user: users){
            owed.put(user, perUserAmount);
            user.updateOwedAmount(expenseAddingUser, perUserAmount);
        }
        return owed;
    }
}

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



class Logger {
    private static Logger logger;

    public static synchronized Logger getInstance() {
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }
    public void displayGroupDetails(Group group){
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[GROUP DETAILS]\n")
                .append("ID      : ").append(group.getId()).append("\n")
                .append("Name    : ").append(group.getName()).append("\n")
                .append("Members : ");

        if (group.getMembers() == null || group.getMembers().isEmpty()) {
            logBuilder.append("No members found.\n");
        } else {
            logBuilder.append(group.getMembers().size()).append(" member(s)\n");
            logBuilder.append("------------------------------\n");
            for (User user : group.getMembers().values()) {
                logBuilder.append("• ID: ").append(user.getId()).append("\n")
                        .append("  Name : ").append(user.getName()).append("\n")
                        .append("  Email: ").append(user.getEmail()).append("\n")
                        .append("  Phone: ").append(user.getPhone()).append("\n")
                        .append("------------------------------\n");
            }
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }

    public void displayUserDetails(User user){
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[USER DETAILS]\n")
                .append("ID      : ").append(user.getId()).append("\n")
                .append("Name    : ").append(user.getName()).append("\n")
                .append("Email   : ").append(user.getEmail()).append("\n")
                .append("Phone   : ").append(user.getPhone()).append("\n");

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }

    public void displayGroupsForUser(User user, List<Group> groups){
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[GROUP DETAILS For User: " +user.getId()+"] \n")
                .append("Groups : ");

        logBuilder.append(groups.size()).append(" group(s)\n");
        logBuilder.append("------------------------------\n");
        for (Group group : groups) {
            logBuilder.append("  Name : ").append(group.getName()).append("\n")
                    .append("------------------------------\n");
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }

    public void displayExpense(ExpenseType type,Expense expense, Map<User, Double> splitAmounts) {

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("EXPENSE DETAILS ["+type+"]\n")
                .append("ID        : ").append(expense.getExpenseId()).append("\n")
                .append("Amount    : ").append(expense.getAmount()).append("\n")
                .append("Group     : ").append(expense.getGroup().getName())
                .append(" (").append(expense.getGroup().getId()).append(")\n")
                .append("SplitType : ").append(expense.getStrategy().getClass().getSimpleName()).append("\n")
                .append("Users     : ");

        if (splitAmounts.isEmpty()) {
            logBuilder.append("No users added.\n");
        } else {
            logBuilder.append(splitAmounts.size()).append(" user(s)\n")
                    .append("------------------------------\n");
            for (Map.Entry<User, Double> entry : splitAmounts.entrySet()) {
                User user = entry.getKey();
                Double amountOwed = entry.getValue();
                logBuilder.append("• Name : ").append(user.getName())
                        .append(" | Amount Owed: ").append(amountOwed).append("\n")
                        .append("------------------------------\n");
            }
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }


    public void displayExpenseForUser(Map<User, Double> expenses, User user) {
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("[INFO] | " + user.getName() + " owes nothing to anyone.");
            return;
        }

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[EXPENSE SUMMARY FOR ").append(user.getName()).append("]\n");

        for (Map.Entry<User, Double> entry : expenses.entrySet()) {
            User owedTo = entry.getKey();
            Double amount = entry.getValue();
            logBuilder.append("Owes ").append(owedTo.getName())
                    .append(" : ").append(amount).append("\n");
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }

    public void displayAllExpensesForUser(List<Expense> expenses, User user) {
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("[INFO] | No transaction history found for " + user.getName());
            return;
        }

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[TRANSACTION HISTORY FOR ").append(user.getName()).append("]\n");

        for (Expense expense : expenses) {
            Map<User, Double> splitAmounts = expense.calculateSplit(); // current owed amounts
            Double amountOwed = splitAmounts.getOrDefault(user, 0.0);

            logBuilder.append("Expense ID   : ").append(expense.getExpenseId()).append("\n")
                    .append("Group        : ").append(expense.getGroup().getName())
                    .append(" (").append(expense.getGroup().getId()).append(")\n")
                    .append("Description  : ").append(expense.getDescription()).append("\n")
                    .append("Total Amount : ").append(expense.getAmount()).append("\n");

            if (expense.getExpenseAddingUser().equals(user)) {
                logBuilder.append("You added this expense.\n");
            } else {
                logBuilder.append("You owe      : ").append(amountOwed)
                        .append(" to ").append(expense.getExpenseAddingUser().getName()).append("\n");
            }

            logBuilder.append("------------------------------\n");
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }

    public void displayAllExpensesForGroup(List<Expense> expenses, Group group) {
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("[INFO] | No transaction history found for " + group.getName());
            return;
        }

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[TRANSACTION HISTORY FOR GROUP: ").append(group.getName()).append("]\n");

        for (Expense expense : expenses) {
            Map<User, Double> splitAmounts = expense.calculateSplit();

            logBuilder.append("\nExpense ID   : ").append(expense.getExpenseId()).append("\n")
                    .append("Description  : ").append(expense.getDescription()).append("\n")
                    .append("Total Amount : ").append(expense.getAmount()).append("\n")
                    .append("Added By     : ").append(expense.getExpenseAddingUser().getName()).append("\n")
                    .append("Participants : ").append(expense.getUsers().size()).append("\n")
                    .append("------------------------------\n");

            for (Map.Entry<User, Double> entry : splitAmounts.entrySet()) {
                User user = entry.getKey();
                Double owed = entry.getValue();

                if (user.equals(expense.getExpenseAddingUser())) {
                    logBuilder.append(user.getName()).append(" paid ₹")
                            .append(String.format("%.2f", expense.getAmount()))
                            .append("\n");
                } else {
                    logBuilder.append(user.getName()).append(" owes ₹")
                            .append(String.format("%.2f", owed))
                            .append(" to ").append(expense.getExpenseAddingUser().getName()).append("\n");
                }
            }

            logBuilder.append("------------------------------\n");
        }

        logBuilder.append("==============================\n");
        System.out.println(logBuilder.toString());
    }


    public void displayGroupBalances(Map<String, Double> balances, Group group) {
        System.out.println("\n==============================");
        System.out.println("[GROUP BALANCES: " + group.getName() + "]");
        if (balances.isEmpty()) {
            System.out.println("No outstanding balances.");
            System.out.println("==============================");
            return;
        }

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            String[] ids = entry.getKey().split("->");
            User from = GroupInventory.getInstance().getGroup(group.getId()).getMembers().get(ids[0]);
            User to = GroupInventory.getInstance().getGroup(group.getId()).getMembers().get(ids[1]);
            System.out.printf("%s owes ₹%.2f to %s%n", from.getName(), entry.getValue(), to.getName());
        }

        System.out.println("==============================");
    }



}
