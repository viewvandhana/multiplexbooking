package com.movieapp.rest.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Category;
import com.movieapp.beans.Customer;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.CustomerWrapper;

@Path("/customers")
public class CustomerResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public Response addCustomer(CustomerWrapper customerWrapper) {

		try {
			
			Customer customerObj = customerWrapper.getCustomer();
			HashMap<String,Customer> addedCustomer=new HashMap<String, Customer>();
			addedCustomer.put("customer", new UserAPI().addCustomer(customerObj));
			return Response.ok(addedCustomer).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
		} 

	}

	@DELETE
	@Path("{customer_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(@PathParam("customer_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteCustomer(id);
				return "Customer Deletion Success";
			} else {
				return new ResponseFailureException(
						"Please provide customer Id").getErrorJson();
			}
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateCustomer(@QueryParam("action") String action,
			@QueryParam("email") String emailId) {

		
		try {
			if (action == null) {
				HashMap<String,ArrayList<Customer>> customerList=new HashMap<String,ArrayList<Customer>>();
				customerList.put("customers", new AdminAPI().getCustomers());
				return Response.ok(customerList).build();
				
			}
			if (action.equals("checkCustomer")) {
				HashMap<String,Customer> validatedCustomer=new HashMap<String,Customer>();
				validatedCustomer.put("customer", new UserAPI().checkCustomer(emailId));
				return Response.ok(validatedCustomer).build();
			} else {
				return Response.ok(new ResponseFailureException("No such operation").getErrorJson()).build();
			}
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			//return Response.status(422).entity(error).build();
		}

	}

	@PUT
	@Path("{customer_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(CustomerWrapper customerWrapper,@PathParam("customer_id") Long customerId) {

		try {
			Customer customerObj = customerWrapper.getCustomer();
			customerObj.setId(customerId);
			HashMap<String,Customer> updatedCustomer=new HashMap<String,Customer>();
			updatedCustomer.put("customer", new UserAPI().updateCustomer(customerObj));
		    return Response.ok(updatedCustomer).build();
			
					
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			
		} 

	}
	
	@GET
	@Path("{customer_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@PathParam("customer_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			HashMap<String,Customer> customer=new HashMap<String,Customer>();
			customer.put("customer", new CommonAPI().getCustomerById(id, fields));
			return Response.ok(customer).build();
				
		
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			
		}
	
	}

	
}
