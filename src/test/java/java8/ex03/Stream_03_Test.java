package java8.ex03;

import java8.data.Data;
import java8.data.domain.Customer;
import java8.data.domain.Gender;
import java8.data.domain.Order;
import java8.data.domain.Pizza;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 03 - Collectors
 */
public class Stream_03_Test {

	@Test
	public void test_max() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Retrouver la commande avec le prix le plus élevé
		/*Optional<Order> result = orders.stream().max(new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				// TODO Auto-generated method stub
				double diff = o1.getPrice() - o2.getPrice();
				return (int)Math.signum(diff);
			}

			
		});*/
		Comparator<Order> comp = Comparator.comparing(o -> o.getPrice()); //ou if else
		Optional<Order> result = orders.stream().max((o1, o2) -> (int)Math.signum(o1.getPrice() - o2.getPrice()));

		assertThat(result.isPresent(), is(true));
		assertThat(result.get().getPrice(), is(2200.0));
	}

	@Test
	public void test_min() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Retrouver la commande avec le prix le plus élevé
		Optional<Order> result = orders.stream().min((o1, o2) -> (int)Math.signum(o1.getPrice() - o2.getPrice()));

		assertThat(result.isPresent(), is(true));
		assertThat(result.get().getPrice(), is(1000.0));
	}

	@Test
	public void test_map_collect_joining() throws Exception {

		List<Customer> customers = new Data().getCustomers();

		// TODO construire une chaîne contenant les prénoms des clients triés et séparés
		// par le caractère "|"
		String result = customers.stream().map(Customer::getFirstname).sorted().collect(Collectors.joining("|"));
		/*String resultTest = customers.stream()
				.reduce("",
						(partialConcat, nextCustomer) -> partialConcat + nextCustomer.getFirstname(),
						String::concat)
				.toString();*/

		assertThat(result, is("Alexandra|Cyril|Johnny|Marion|Sophie"));
	}

	@Test
	public void test_flatMap() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Extraire la liste des pizzas de toutes les commandes
		/*BinaryOperator<List<Pizza>> concat = new BinaryOperator<List<Pizza>>() {

			@Override
			public List<Pizza> apply(List<Pizza> t, List<Pizza> u) {
				t.addAll(u);
				return t;
			}
		};
		
		List<Pizza> result = orders.stream()
				.map(o -> o.getPizzas())
				.reduce(new ArrayList<Pizza>(), concat);*/
		/*Function<Order, Stream<Pizza>> flatener = new Function<Order, Stream<Pizza>>() {

			@Override
			public Stream<Pizza> apply(Order t) {
				// TODO Auto-generated method stub
				return t.getPizzas().stream();
			}
		};*/
		List<Pizza> result = orders.stream().flatMap(o -> o.getPizzas().stream()).toList();

		assertThat(result.size(), is(9));
	}

	@Test
	public void test_flatMap_distinct() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO Extraire la liste des différentes pizzas de toutes les commandes
		List<Pizza> result = orders.stream().flatMap(o -> o.getPizzas().stream()).distinct().toList();

		assertThat(result.size(), is(4));
	}

	@Test
	public void test_grouping() throws Exception {

		List<Order> orders = new Data().getOrders();

		// TODO construire une Map <Client, Commandes effectuées par le client
		Map<Customer, List<Order>> result = orders.stream()
				.collect(Collectors.groupingBy(Order::getCustomer));

		assertThat(result.size(), is(2));
		assertThat(result.get(new Customer(1)), hasSize(4));
		assertThat(result.get(new Customer(2)), hasSize(4));
	}

	@Test
	public void test_partitionning() throws Exception {
		List<Pizza> pizzas = new Data().getPizzas();

		// TODO Séparer la liste des pizzas en 2 ensembles :
		// TODO true -> les pizzas dont le nom commence par "L"
		// TODO false -> les autres
		Map<Boolean, List<Pizza>> result = pizzas.stream()
				.collect(Collectors.partitioningBy(p -> p.getName().startsWith("L")));

		assertThat(result.get(true), hasSize(6));
		assertThat(result.get(false), hasSize(2));
	}
}
