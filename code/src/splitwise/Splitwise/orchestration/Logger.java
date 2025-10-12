package Splitwise.orchestration;

import Splitwise.inventory.GroupInventory;
import Splitwise.model.Expense;
import Splitwise.model.ExpenseType;
import Splitwise.model.Group;
import Splitwise.model.User;

import java.util.List;
import java.util.Map;

class Logger {
    private static Logger logger;

    public static synchronized Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public void displayGroupDetails(Group group) {
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

    public void displayUserDetails(User user) {
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

    public void displayGroupsForUser(User user, List<Group> groups) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("[GROUP DETAILS For User: " + user.getId() + "] \n")
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

    public void displayExpense(ExpenseType type, Expense expense, Map<User, Double> splitAmounts) {

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n==============================\n")
                .append("EXPENSE DETAILS [" + type + "]\n")
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
