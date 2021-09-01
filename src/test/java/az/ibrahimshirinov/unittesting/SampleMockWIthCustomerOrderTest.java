package az.ibrahimshirinov.unittesting;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author IbrahimShirinov
 * @since 31.08.2021
 */
public class SampleMockWIthCustomerOrderTest {


    @Test
    void addOrderToCustomer() {
        final Inventory inventoryMock = Mockito.mock(Inventory.class);

        Mockito.when(inventoryMock.isStockAvailable("Item1","Item2")).thenReturn(true);
        Mockito.when(inventoryMock.isStockAvailable("Item3")).thenReturn(false);

        Customer customer = new Customer(inventoryMock);

        assertTrue(customer.addOrder(new Order(List.of("Item1","Item2"))));
        assertFalse(customer.addOrder(new Order(List.of("Item3"))));

        Mockito.verify(inventoryMock,Mockito.times(1)).isStockAvailable("Item1","Item2");
        Mockito.verify(inventoryMock,Mockito.times(1)).isStockAvailable("Item3");
    }

    private class Customer {

        final Inventory inventory;

        public Customer(Inventory inventory) {
            this.inventory = inventory;
        }

        boolean addOrder(Order order) {
            if (!inventory.isStockAvailable(order.items.toArray(new String[]{}))) {
                return false;
            }

            return true;
        }
    }

    private class Order {

        List<String> items = new ArrayList<>();

        Order(List<String> items) {
            this.items = items;
        }
    }

    private interface Inventory {
        boolean isStockAvailable(String... items);
    }
}
