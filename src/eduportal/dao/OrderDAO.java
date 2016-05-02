package eduportal.dao;

import java.util.*;
import static com.googlecode.objectify.ObjectifyService.ofy;
import eduportal.dao.entity.*;


public class OrderDAO {

	public static List<Order> getAll() {
		return  Arrays.asList((Order[]) ofy().load().kind("Order").list().toArray());
	}

}