package com.example.demo.utilities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import lombok.Data;

@Data
public class ModelsContainer {
	
	private Map<String,Model> navigationMap;
	
	public ModelsContainer()
	{
		navigationMap=new HashMap<String,Model>();
		navigationMap.put("BACK", null);
		navigationMap.put("CURRENT", null);
		navigationMap.put("FORWARD", null);

	}

//	public void UpdateNavigationMap(Model model,ViewManager viewManager)
//	{
//	}
	
	
}
