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

import org.json.JSONObject;

import Exception.ResponseFailureException;

import com.movieapp.beans.Category;
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;

@Path("/categories")
public class CategoryResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public String addCategory(String category) {

		try {
			JSONObject j = new JSONObject(category);
			JSONObject categoryJson = j.optJSONObject("category");
			Category categoryObj = ObjectMapperUtil.getMapper().readValue(
					categoryJson.toString(), Category.class);
			return ObjectMapperUtil.getCustomMappedString("category",
					new AdminAPI().addCategory(categoryObj));
		}
		catch(ResponseFailureException e)
		{
			return e.getErrorJson();
		}
		catch(Exception e)
		{
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}
		

	}

	@DELETE
	@Path("{category_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCategory(@PathParam("category_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteCustomer(id);
				//return "Category Deletion Success";
				return null;
			} else {
				throw new ResponseFailureException("Please provide category id");
			}
		} catch (ResponseFailureException e) {

			return e.getErrorJson();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories(@QueryParam("fields") String fields) {
		try{
		return ObjectMapperUtil.getCustomMappedString("categories",
				new CommonAPI().getCategories(fields));
		}
		catch(ResponseFailureException e)
		{
			return e.getErrorJson();
		}

	}

	
	@GET
	@Path("{category_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategoryById(@PathParam("category_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			return ObjectMapperUtil.getCustomMappedString("category",
					new CommonAPI().getCategoryById(id,fields));
		} catch (ResponseFailureException e) {
			return e.getErrorJson();
		}
	
	}

	@PUT
	@Path("{category_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategory(String category,@PathParam("category_id") Long categoryId) {

		try {
			JSONObject j = new JSONObject(category);
			JSONObject categoryJson = j.optJSONObject("category");
			Category categoryObj = ObjectMapperUtil.getMapper().readValue(
					categoryJson.toString(), Category.class);
			categoryObj.setId(categoryId);
			return ObjectMapperUtil.getCustomMappedString("category",
					new AdminAPI().updateCategory(categoryObj));

		}
		catch(ResponseFailureException e)
		{
			return e.getErrorJson();
		}
		catch (Exception e) {
			return new ResponseFailureException(e.getMessage()).getErrorJson();
		}
	
	}
}
