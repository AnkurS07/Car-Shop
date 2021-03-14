package ca.mcgill.ecse.carshop.features;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CancelAppointmentsStepDefinitions {

	 @Given("an owner account exists in the system")
	    public void an_owner_account_exists_in_the_system() {
	        // Write code here that turns the phrase above into concrete actions
	        throw new io.cucumber.java.PendingException();
	    }


	    @Given("a business exists in the system")
	    public void a_business_exists_in_the_system() {
	        // Write code here that turns the phrase above into concrete actions
	        throw new io.cucumber.java.PendingException();
	    }
	    @Given("the following services exist in the system:")
	    public void the_following_services_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	        // Write code here that turns the phrase above into concrete actions
	        // For automatic transformation, change DataTable to one of
	        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	        // Double, Byte, Short, Long, BigInteger or BigDecimal.
	        //
	        // For other transformations you can register a DataTableType.
	        throw new io.cucumber.java.PendingException();
	    }
	    @Given("the following service combos exist in the system:")
	    public void the_following_service_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	        // Write code here that turns the phrase above into concrete actions
	        // For automatic transformation, change DataTable to one of
	        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	        // Double, Byte, Short, Long, BigInteger or BigDecimal.
	        //
	        // For other transformations you can register a DataTableType.
	        throw new io.cucumber.java.PendingException();
	    }
	    @Given("the business has the following opening hours")
	    public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) {
	        // Write code here that turns the phrase above into concrete actions
	        // For automatic transformation, change DataTable to one of
	        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	        // Double, Byte, Short, Long, BigInteger or BigDecimal.
	        //
	        // For other transformations you can register a DataTableType.
	        throw new io.cucumber.java.PendingException();
	    }
	    @Given("the following appointments exist in the system:")
	    public void the_following_appointments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	        // Write code here that turns the phrase above into concrete actions
	        // For automatic transformation, change DataTable to one of
	        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	        // Double, Byte, Short, Long, BigInteger or BigDecimal.
	        //
	        // For other transformations you can register a DataTableType.
	        throw new io.cucumber.java.PendingException();
	    }
	    @Given("{string} is logged in to their account")
	    public void is_logged_in_to_their_account(String string) {
	        // Write code here that turns the phrase above into concrete actions
	        throw new io.cucumber.java.PendingException();
	    }
	    @When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	    public void attempts_to_cancel_their_appointment_on_at(String string, String string2, String string3, String string4) {
	        // Write code here that turns the phrase above into concrete actions
	        throw new io.cucumber.java.PendingException();
	    }
	    @Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	    public void s_appointment_on_at_shall_be_removed_from_the_system(String string, String string2, String string3, String string4) {
	        // Write code here that turns the phrase above into concrete actions
	    	
	    	assertEquals(true, true);
	    	// 1st is value expected, 2nd is the value we are checking 
	    	
	    	
	    	
	    	
	        throw new io.cucumber.java.PendingException();
	    }
	    @Then("there shall be {int} less appointment in the system")
	    public void there_shall_be_less_appointment_in_the_system(Integer int1) {
	        // Write code here that turns the phrase above into concrete actions
	        throw new io.cucumber.java.PendingException();
	    }

	
}
