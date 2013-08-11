package com.example.retrotest;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	List<T> response;
	int count;
	
	public Response()
	{
		response = new ArrayList<T>();
	}
}
