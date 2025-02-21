package com.sqli.isc.iut.courses.cucumber;

import java.util.*;

public class Bar {
    private int currentOccupancy;
    private final int maxCapacity;
    private final Map<String, List<Integer>> bills = new HashMap<>();
    private final Map<String, Integer> drinksConsumed = new HashMap<>();
    private final Set<String> coveredByLeblanc = new HashSet<>();

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
        bills.computeIfAbsent(customer, k -> new ArrayList<>()).add(total);
        drinksConsumed.put(customer, drinksConsumed.getOrDefault(customer, 0) + quantity);
    }

    public int getBill(String customer) {
        return bills.getOrDefault(customer, Collections.emptyList())
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void markAsPaidByLeblanc(String customer) {
        if (!customer.equals("Mr. Leblanc")) {
            int amount = getBill(customer);
            bills.computeIfAbsent("Mr. Leblanc", k -> new ArrayList<>()).add(amount);
            bills.remove(customer); // Supprime la facture de l'autre personne
        }
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
        int totalAmount = coveredByLeblanc.stream()
                .mapToInt(this::getBill)
                .sum();

        if (totalAmount > 0) {
            coveredByLeblanc.forEach(bills::remove);
            bills.remove("Mr. Leblanc");
            coveredByLeblanc.clear();
            return true;
        }
        return false;
    }

    public boolean isSad(String customer) {
        return drinksConsumed.getOrDefault(customer, 0) > 1;
    }
}
