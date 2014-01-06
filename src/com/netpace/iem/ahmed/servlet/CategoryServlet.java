package com.netpace.iem.ahmed.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.netpace.iem.ahmed.config.ContextProvider;
import com.netpace.iem.ahmed.model.Category;
import com.netpace.iem.ahmed.repository.CategoryRepository;

/**
 * Application Lifecycle Listener implementation class CategoryServlet
 *
 */
@WebServlet("/Category")
public class CategoryServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationContext context;
	private CategoryRepository categoryRepository; 
	private static Logger log = LoggerFactory.getLogger(CategoryServlet.class);
	/**
     * Default constructor. 
     */
    public CategoryServlet() {
    	context = ContextProvider.getContext();
    	categoryRepository = (CategoryRepository) context.getBean(CategoryRepository.class);
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("In Get = " + Joiner.on(",").join(Arrays.asList(request.getParameterNames())));
		if(request.getParameter("delete") != null)
			categoryRepository.delete(Integer.parseInt(request.getParameter("delete")));	
		if(request.getParameter("output") != null && request.getParameter("output").equals("json")) {
			jsonOutput(response.getWriter(), request.getParameterMap());
			return;
		}
		request.setAttribute("categories", categoryRepository.findAll());
		log.info("Hello World");
		request.getRequestDispatcher("/ListCategory.jsp").forward(request, response);
	}

	private void jsonOutput(PrintWriter writer, Map<String, String[]> map) {
		int rows = 0, pageNumber = 0;
		if(map.containsKey("page")) {
			pageNumber = Integer.parseInt(map.get("page")[0]) - 1;
			rows = Integer.parseInt(map.get("rows")[0]);
		}
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JsonElement json = null;
		if(rows > 0)
			json = gson.toJsonTree(categoryRepository.findAll(new PageRequest(pageNumber, rows)).getContent());
		else
			json = gson.toJsonTree(categoryRepository.findAll());
		JsonObject jObj = new JsonObject();
		jObj.add("result", json);
		jObj.addProperty("total", categoryRepository.count());
		writer.write(jObj.toString());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> requestParams = request.getParameterMap();
		Category category = new Category();
		int id = 0;
		switch(requestParams.get("oper")[0]) {
			case "del":
				id = Integer.parseInt(requestParams.get("id")[0]);
				categoryRepository.delete(id);
				break;
			case "edit":
				id = Integer.parseInt(requestParams.get("id")[0]);
				category = categoryRepository.findOne(id);
			case "add":
				category.setName(requestParams.get("name")[0]);
				category.setDescription(requestParams.get("description")[0]);
				categoryRepository.save(category);
				break;
		}
		/*String[] names = request.getParameterValues("name");
		String[] descriptions = request.getParameterValues("description");
		for(int i = 0; i < names.length; i++) {
			Category category = new Category();
			category.setName(names[i]);
			category.setDescription(descriptions[i]);
			if(!category.getName().isEmpty())
				categoryRepository.save(category);
		}*/
		doGet(request, response);
	}
}