package com.netpace.iem.ahmed.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netpace.iem.ahmed.model.Category;
import com.netpace.iem.ahmed.repository.CategoryRepository;
import com.netpace.iem.ahmed.servlet.CategoryServlet;

@Controller
@RequestMapping("/category/*")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository; 
	private static Logger log = LoggerFactory.getLogger(CategoryServlet.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(ModelMap model) {
        return new ModelAndView("category/index");
	}
	
	@RequestMapping(value = "getJson", method = RequestMethod.GET)
	public @ResponseBody String getJson(@RequestParam(required=false, defaultValue="1") Integer page, 
			@RequestParam(required=false, defaultValue="10") Integer rows) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JsonElement json = null;
		if(rows > 0)
			json = gson.toJsonTree(categoryRepository.findAll(new PageRequest(page - 1, rows)).getContent());
		else
			json = gson.toJsonTree(categoryRepository.findAll());
		JsonObject jObj = new JsonObject();
		jObj.add("result", json);
		jObj.addProperty("total", categoryRepository.count());
		return jObj.toString();
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public @ResponseBody String edit(@RequestParam String oper,
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description) {
		Integer idN = parseInt(id);
		Category category = new Category();
		switch(oper) {
			case "del":
				categoryRepository.delete(idN);
				break;
			case "edit":
				category = categoryRepository.findOne(idN);
			case "add":			
				try {				
				category.setName(name);
				category.setDescription(description);
				} catch(Exception ex) {
					log.error(ex.getMessage());
				}
				categoryRepository.save(category);
				break;
		}
		return "";
	}

	private Integer parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

}
