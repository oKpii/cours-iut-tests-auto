package com.sqli.isc.iut.courses.cucumber;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {
    private Bar bar;
    private boolean entryAllowed;
    private int initialPeople;

    @Given("A bar named {string} with a maximum capacity of {int} seats")
    public void a_bar_named_with_a_maximum_capacity_of_seats(String name, Integer capacity) {
        bar = new Bar(capacity);
    }

    @Given("There are already {int} people in the bar")
    public void there_are_already_people_in_the_bar(Integer count) {
        initialPeople = count;
        for (int i = 0; i < count; i++) {
            bar.enter(1);  // Ajoute les personnes une par une
        }
    }
    @Given("There are {int} people in the bar")
    public void there_are_people_in_the_bar(Integer numberOfPeople) {
        bar.setCurrentOccupancy(numberOfPeople);
    }

    @Given("Mr. Pignon and Mr. Leblanc want to go to the bar")
    public void mr_pignon_and_mr_leblanc_want_to_go_to_the_bar() {

        System.out.println("Mr. Pignon and Mr. Leblanc are heading to the bar.");
    }


    @When("Mr. Pignon and Mr. Leblanc arrive")
    public void guestsArrive() {
        entryAllowed = bar.enter(2);
    }

    @Then("They are denied entry")
    public void verifyEntryDenied() {
        assertFalse(entryAllowed, "They should be denied entry because the bar is almost full");
    }

    @Then("The person behind them is denied entry")
    public void the_person_behind_them_is_denied_entry() {
        boolean nextEntryAllowed = bar.enter(1);
        assertFalse(nextEntryAllowed, "The next person should be denied entry because the bar is full");
    }

    @Then("The bar displays {string}")
    public void the_bar_displays(String message) {
        boolean isFull = bar.isFull();
        if (isFull) {
            assertEquals("Full", message, "The bar should display 'Full' when it reaches capacity");
        } else {
            assertNotEquals("Full", message, "The bar should not display 'Full' when it is not at capacity");
        }
    }

    @When("They each order the cocktail of the month for {int}€")
    public void they_each_order_the_cocktail_of_the_month_for_€(Integer price) {
        bar.order("Mr. Pignon", 1, price);
        bar.order("Mr. Leblanc", 1, price);
    }

    @When("Mr. Leblanc pays for both")
    public void mr_leblanc_pays_for_both() {
        bar.markAsPaidByLeblanc("Mr. Pignon");
        bar.markAsPaidByLeblanc("Mr. Leblanc");
    }

    @Then("At the end of their drink, they check the bill")
    public void at_the_end_of_their_drink_they_check_the_bill() {
        int expectedBill = 20;
        int actualBill = bar.getBill("Mr. Leblanc");

        assertEquals(expectedBill, actualBill, "Mr. Leblanc should have a total bill of 20€ before paying.");
    }

    @Then("Mr. Leblanc pays")
    public void mr_leblanc_pays() {
        boolean paymentSuccess = bar.leblancPays();
        assertTrue(paymentSuccess, "Mr. Leblanc should be able to pay for the total bill.");
        assertEquals(0, bar.getBill("Mr. Leblanc"), "After payment, Mr. Leblanc's bill should be 0.");
        assertEquals(0, bar.getBill("Mr. Pignon"), "After payment, Mr. Pignon's bill should be 0.");
    }

    @Then("Mr. Pignon is happy because they only had one drink")
    public void mr_pignon_is_happy_because_they_only_had_one_drink() {
        assertFalse(bar.isSad("Mr. Pignon"), "Mr. Pignon should be happy because he only had one drink");
    }
    @Then("They check their individual bills")
    public void they_check_their_individual_bills() {
        int billPignon = bar.getBill("Mr. Pignon");
        int billLeblanc = bar.getBill("Mr. Leblanc");

        assertEquals(10, billPignon, "Mr. Pignon should have a total bill of 10€.");
        assertEquals(10, billLeblanc, "Mr. Leblanc should have a total bill of 10€.");
    }
    @When("Mr. Pignon pays his bill")
    public void mr_pignon_pays_his_bill() {
        boolean paymentSuccess = bar.payBill("Mr. Pignon");
        assertTrue(paymentSuccess, "Mr. Pignon should be able to pay his bill");
    }

    @When("Mr. Leblanc insists on ordering another cocktail of the month")
    public void mr_leblanc_insists_on_ordering_another_cocktail_of_the_month() {
        bar.order("Mr. Pignon", 1, 10);
    }

    @When("He orders {int} more cocktails for his bill")
    public void he_orders_more_cocktails_for_his_bill(Integer count) {
        bar.order("Mr. Leblanc", count, 10);
    }

    @Then("At the end of his drinks, Mr. Leblanc checks his bill and pays")
    public void at_the_end_of_his_drinks_mr_leblanc_checks_his_bill_and_pays() {
        int billAmount = bar.getBill("Mr. Leblanc");
        assertEquals(30, billAmount, "Mr. Leblanc should have a total bill of 30€ after additional orders");
        assertTrue(bar.payBill("Mr. Leblanc"), "Mr. Leblanc should be able to pay his total bill");
    }

    @Then("Mr. Pignon is sad because drinking more than one cocktail makes him sick")
    public void mr_pignon_is_sad_because_drinking_more_than_one_cocktail_makes_him_sick() {
        assertTrue(bar.isSad("Mr. Pignon"), "Mr. Pignon should be sad if he drinks more than one cocktail");
    }
}
