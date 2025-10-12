import Splitwise.SplitwiseInterface;

import java.util.HashMap;
import java.util.Map;

public class SplitwiseSimulation {
    public static void main(String[] args) {
        SplitwiseInterface splitwiseInterface = new SplitwiseInterface();

        splitwiseInterface.addUser("Alice", "alice@gmail.com", "1234567890");
        splitwiseInterface.addUser("Bob", "bob@gmail.com", "0987654321");
        splitwiseInterface.addUser("Charlie", "charlie@gmail.com", "1122334455");
        splitwiseInterface.addUser("Dan", "dan@gmail.com", "1323234455");
        splitwiseInterface.addUser("Eve", "eve@gmail.com", "1323234878");



        splitwiseInterface.viewUser("Alice_7890_0");

        splitwiseInterface.createGroup("Friends");
        splitwiseInterface.addUserToGroup("Alice_7890_0", "Friends_0");
        splitwiseInterface.addUserToGroup("Dan_4455_3", "Friends_0");
        splitwiseInterface.addUserToGroup("Charlie_4455_2", "Friends_0");

        splitwiseInterface.showGroupDetails("Friends_0");
        splitwiseInterface.showGroupDetails("Friens_0"); // Invalid group


        splitwiseInterface.createGroup("Trip");

        splitwiseInterface.addUserToGroup("Dan_4455_3", "Trip_1");
        splitwiseInterface.addUserToGroup("Charlie_4455_2", "Trip_1");
        splitwiseInterface.addUserToGroup("Eve_4878_4", "Trip_1");




        splitwiseInterface.removeUserFromGroup("Dan_4455_3", "Friends_0");
        splitwiseInterface.showGroupsForUser("Charlie_4455_2");


        splitwiseInterface.addExpense("Trip_1", 2346, "Cab 1", "Dan_4455_3", "Dan_4455_3,Charlie_4455_2", "equal");
        splitwiseInterface.addUsersToExpense("Trip_1", "EXP_0", "Eve_4878_4");
        splitwiseInterface.updateAmountForExpense("Trip_1", "EXP_0", 600);


        splitwiseInterface.addExpense("Trip_1", 890, "Cab 2", "Charlie_4455_2", "Dan_4455_3,Charlie_4455_2", "equal");



        Map<String, Double> exactMap = new HashMap<>();
        exactMap.put("Dan_4455_3", 50.0);
        exactMap.put("Eve_4878_4", 30.0);
        splitwiseInterface.addExpense("Trip_1", 200, "Cab 1", "Dan_4455_3", "Dan_4455_3,Charlie_4455_2", "exact", exactMap);


        Map<String, Double> percMap = new HashMap<>();
        percMap.put("Alice_7890_0", 60.0);
        percMap.put("Charlie_4455_2", 30.0);
        splitwiseInterface.addExpense("Friends_0", 780, "Food", "Charlie_4455_2", "Alice_7890_0,Charlie_4455_2", "percentage", percMap);


        splitwiseInterface.viewExpenseSummaryForUser("Charlie_4455_2");

        splitwiseInterface.showTransactionHistoryForUser("Dan_4455_3");

        splitwiseInterface.showGroupBalanceSummary("Trip_1");

    }
}
