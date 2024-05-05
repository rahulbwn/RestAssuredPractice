package ECommercePojo;

import java.util.List;

public class PlaceOrder {
	
	private List<OrderDetails> orders;
	
	public List<OrderDetails> getOrders()
	{
		return orders;
	}
	
	public void setOrders(List<OrderDetails> orderDetailsList)
	{
		this.orders=orderDetailsList;
	}

}
