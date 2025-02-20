package com.sqli.isc.iut.courses.cucumber;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bar {
    private int currentOccupancy;
    private final int maxCapacity;
    private final Map<String, Integer> bills = new HashMap<>();
    private final Set<String> coveredByLeblanc = new HashSet<>(); // Clients que Leblanc paiera

    public Bar(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.currentOccupancy = 0;
    }

    public boolean enter(int people) {
        if (currentOccupancy + people > maxCapacity) {
            return false;
        }
        currentOccupancy += people;
        return true;
    }

    public boolean isFull() {
        return currentOccupancy >= maxCapacity;
    }
    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }

    public void order(String customer, int quantity, int price) {
        int total = quantity * price;
        bills.put(customer, bills.getOrDefault(customer, 0) + total);
    }

    public int getBill(String customer) {
        return bills.getOrDefault(customer, 0);
    }

    public void markAsPaidByLeblanc(String customer) {
        coveredByLeblanc.add(customer);
    }

    public boolean payBill(String customer) {
        if (bills.containsKey(customer)) {
            bills.remove(customer);
            return true;
        }
        return false;
    }

    public boolean leblancPays() {
        int totalAmount = 0;

        for (String customer : coveredByLeblanc) {
            totalAmount += bills.getOrDefault(customer, 0);
        }

        if (totalAmount > 0) {
            for (String customer : coveredByLeblanc) {
                bills.remove(customer); // Supprime les factures payées
            }
            bills.remove("Mr. Leblanc"); // Supprime aussi la sienne
            coveredByLeblanc.clear();
            return true;
        }
        return false;
    }
    public boolean hasOnlyOrderedOnce(String customer) {
        return bills.getOrDefault(customer, 0) <= 1;
    }

}


