package com.movieapp.rest.resources;

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

import com.movieapp.beans.Customer;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.bo.admin.UserAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/customers")
public class CustomerResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public String addCustomer(String customer) {

		try {
			JSONObject j = new JSONObject(customer);
			JSONObject customerJson = j.optJSONObject("customer");
			Customer customerObj = ObjectMapperUtil.getMapper().readValue(
					customerJson.toString(), Customer.class);
			return ObjectMapperUtil.getCustomMappedString("customer",
					new UserAPI().addCustomer(customerObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
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
				String resp= ObjectMapperUtil.getCustomMappedString("customers",
						new AdminAPI().getCustomers());
				return Response.ok(resp).build();

			}
			if (action.equals("checkCustomer")) {
				String resp= new UserAPI().checkCustomer(emailId);
				return Response.ok(resp).build();
			} else {
				String error=new ResponseFailureException("No such operation").getErrorJson();
				return Response.status(422).entity(error).build();
			}
		} catch (ResponseFailureException e) {
			String error= e.getErrorJson();
			return Response.status(422).entity(error).build();
		}

	}

	@PUT
	@Path("{customer_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCustomer(String customer,@PathParam("customer_id") Long customerId) {

		try {
			JSONObject j = new JSONObject(customer);
			JSONObject customerJson = j.optJSONObject("customer");
			Customer customerObj = ObjectMapperUtil.getMapper().readValue(
					customerJson.toString(), Customer.class);
			customerObj.setId(customerId);
			return ObjectMapperUtil.getCustomMappedString("customer",
					new UserAPI().updateCustomer(customerObj));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		} catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}

	}
	
	@GET
	@Path("{customer_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomerById(@PathParam("customer_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			return ObjectMapperUtil.getCustomMappedString("customer",
					new CommonAPI().getCustomerById(id, fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	
	}

}
