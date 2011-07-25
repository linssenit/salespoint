package test.order;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.salespointframework.core.accountancy.Accountancy;
import org.salespointframework.core.accountancy.AbstractAccountancyEntry;
import org.salespointframework.core.database.Database;
import org.salespointframework.core.order.OrderEntry;
import org.salespointframework.core.order.OrderIdentifier;
import org.salespointframework.core.order.OrderManager;

public class OrderTest {
	private OrderManager om;
	private List<OrderIdentifier> oi = new ArrayList<OrderIdentifier>();
	private EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	
	private DateTime from;
	private DateTime to;

	@BeforeClass
	public static void classSetup() {
		Database.INSTANCE.initializeEntityManagerFactory("SalespointFramework");
	}

	@Before
	public void testSetup() {
		om = new OrderManager();
		OrderEntry oe;
		for (int year = 2000; year < 2005; year++) {
			oe = new OrderEntry();
			oi.add(oe.getOrderIdentifier());
			om.addOrder(oe);
			if(year == 2001)
				from = new DateTime();
			if(year == 2004)
				to = new DateTime();
		}
	}

	
	public void select() {
		Iterable<OrderEntry> i = om.findOrders(from, to);
		
		//TODO not really a test, because the Iterable is always non-null.
		//Instead, we need to test for non-emptyness of the Iterable, or three
		//elements.
		assertNotNull(i);

		for(OrderEntry e : i) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void remove() {
		OrderIdentifier e = oi.get(3);
		System.out.println("Now:");
		select();
		System.out.println("removing ... " + e.toString());
		om.remove(oi.get(3));
		System.out.println("after:");
		select();
	}
}
