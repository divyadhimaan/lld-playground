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

    public void addExpense(String groupId, double expenseAmount, String description, String participants, String strategy){
        orchestration.addExpense(groupId, expenseAmount, description, participants, strategy);
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

    public void addExpense(String groupId, double expenseAmount, String description, String participants, String strategy){
        Group group = groupInventory.getGroup(groupId);

        if(group == null)
            return;

        List<User> users = getParticipantsInExpense(group, participants);
        if(users.isEmpty()){
            System.out.println("[ERROR]: Failed adding the expense - Invalid User provided");
            return;
        }


        Expense expense = expenseFactory.createExpense(group, expenseAmount, description, users, strategy);
        if(expense == null) {
            System.out.println("[ERROR]: Failed adding the expense");
            return;
        }
        group.addExpense(expense);

        logger.displayExpense(ExpenseType.CREATED,expense);
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
                System.out.println("[ERROR]: User not part of the specified group");
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
        logger.displayExpense(ExpenseType.UPDATED, expense);
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

    public User(String id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
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
    private final Map<String,Expense> expenses;
//    private final Map<String, double> memberExpenses;

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
    }
    public void updateExpense(String expId, Expense exp){
        expenses.replace(expId, expenses.get(expId), exp);
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

class ExpenseFactory{
    public Expense createExpense(Group group, double expenseAmount, String description, List<User> users, String strategy){
        SplitStrategy splitStrategy = null;
        switch(strategy){
            case "equal" -> splitStrategy = new EqualSplit();
            case "percentage" -> splitStrategy = new PercentageSplit();
            case "exact" -> splitStrategy = new ExactAmountSplit();
            default -> System.out.println("Pick a valid strategy out of equal/percentage/exact");
        }
        if(splitStrategy == null)
            return null;
        return new Expense(expenseAmount, group, description, users, splitStrategy);
    }
}

@Getter
class Expense {
    private static final AtomicInteger expenseIdCounter = new AtomicInteger(0);
    private final String expenseId;
    private double amount;
    private final String description;
    private final Group group;
    private final List<User> users;
    private final SplitStrategy strategy;


    public Expense(double amount, Group group, String description, List<User> users,SplitStrategy strategy){
        this.expenseId = "EXP_"+expenseIdCounter;
        this.amount = amount;
        this.description = description;
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
        return strategy.split(amount, users);
    }
}

interface SplitStrategy{
    Map<User, Double> split(double amount, List<User> users);
}

class EqualSplit implements SplitStrategy {
    @Override
    public Map<User, Double> split(double amount, List<User> users){
        Map<User, Double> owed = new HashMap<>();
        System.out.println("Splitting equally...");
        if(users.isEmpty())
            return owed;

        double perUserAmount = amount/users.size();
        for(User user: users){
            owed.put(user, perUserAmount);
        }
        return owed;
    }
}

class PercentageSplit implements SplitStrategy {

    @Override
    public Map<User, Double> split(double amount, List<User> users){
        Map<User, Double> owed = new HashMap<>();
        System.out.println("Percentage Split...");
        if(users.isEmpty())
            return owed;

        double perUserAmount = amount/users.size();
        for(User user: users){
            owed.put(user, perUserAmount);
        }
        return owed;
    }
}

class ExactAmountSplit implements SplitStrategy {
    @Override
    public Map<User, Double> split(double amount, List<User> users){
        Map<User, Double> owed = new HashMap<>();
        System.out.println("Exact Amount Split...");
        if(users.isEmpty())
            return owed;

        double perUserAmount = amount/users.size();
        for(User user: users){
            owed.put(user, perUserAmount);
        }
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
                .append("[GROUP DETAILS]\n")
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

    public void displayExpense(ExpenseType type,Expense expense) {

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("EXPENSE DETAILS ["+type+"]\n")
                .append("ID        : ").append(expense.getExpenseId()).append("\n")
                .append("Amount    : ").append(expense.getAmount()).append("\n")
                .append("Group     : ").append(expense.getGroup().getName())
                .append(" (").append(expense.getGroup().getId()).append(")\n")
                .append("SplitType : ").append(expense.getStrategy().getClass().getSimpleName()).append("\n")
                .append("Users     : ");

        Map<User, Double> splitAmounts = expense.calculateSplit();
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


}
