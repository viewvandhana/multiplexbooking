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
import com.movieapp.bo.admin.AdminAPI;
import com.movieapp.bo.admin.CommonAPI;
import com.movieapp.util.ObjectMapperUtil;
import com.movieapp.wrapperbeans.CategoryWrapper;

@Path("/categories")
public class CategoryResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
		public Response addCategory(CategoryWrapper categoryWrapper) {

		try {
			Category categoryObj =categoryWrapper.getCategory();
			HashMap<String,Category> addedCategory=new HashMap<String, Category>();
			addedCategory.put("category", new AdminAPI().addCategory(categoryObj));
			return Response.ok(addedCategory).build();
		}
		catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}
		
		

	}

	@DELETE
	@Path("{category_id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCategory(@PathParam("category_id") Long id) {

		try {
			if (id != null) {
				new AdminAPI().deleteCategory(id);
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
	public Response getAllCategories(@QueryParam("fields") String fields) {
		try{
			HashMap<String,ArrayList<Category>> categoryList=new HashMap<String, ArrayList<Category>>();
			categoryList.put("categories",new CommonAPI().getCategories(fields));
			return Response.ok(categoryList).build();
		 
		}
		catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}

	}

	
	@GET
	@Path("{category_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryById(@PathParam("category_id") Long id,@QueryParam("fields") String fields) {
		
		try {
			HashMap<String,Category> category=new HashMap<String, Category>();
			category.put("category", new CommonAPI().getCategoryById(id,fields));
		    return Response.ok(category).build();
		} catch (ResponseFailureException e) {
			return Response.ok(e.getErrorJson()).build();
			
		}
	
	}

	@PUT
	@Path("{category_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCategory(CategoryWrapper categoryWrapper,@PathParam("category_id") Long categoryId) {

		try {
			Category categoryObj = categoryWrapper.getCategory();
			categoryObj.setId(categoryId);
			HashMap<String,Category> updatedCategory=new HashMap<String, Category>();
			updatedCategory.put("category", new AdminAPI().updateCategory(categoryObj));
			return Response.ok(updatedCategory).build();
	
	
		}
		catch(ResponseFailureException e)
		{
			return Response.ok(e.getErrorJson()).build();
		}
	
	}
}
