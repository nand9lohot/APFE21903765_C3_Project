import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void initialize(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spyRestaurant =  Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:00:00"));
        Mockito.when(spyRestaurant.isRestaurantOpen()).thenReturn(true);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:00:00"));
        Mockito.when(spyRestaurant.isRestaurantOpen()).thenReturn(false);
        assertFalse(spyRestaurant.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,()->restaurant.removeFromMenu("French fries"));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void should_return_some_total_value_when_order_items_are_selected() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Sweet corn soup", 129));
        items.add(new Item("Vegetable lasagne", 269));
        int orderTotal = restaurant.getTotalCost(items);
        assertEquals(399, orderTotal);
    }

    @Test
    public void should_return_zero_when_no_order_items_are_selected(){
        List<Item> items = new ArrayList<>();
        int orderTotal = restaurant.getTotalCost(items);
        assertEquals(0,orderTotal);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}