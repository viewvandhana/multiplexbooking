package com.movieapp.rest.APIResponse;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListResponse<T> {
			
	    @JsonProperty("rootname")
		private ArrayList<T> objList;

		public ArrayList<T> getObjList() {
			return objList;
		}

		public void setObjList(ArrayList<T> objList) {
			this.objList = objList;
		}
		
		
}

