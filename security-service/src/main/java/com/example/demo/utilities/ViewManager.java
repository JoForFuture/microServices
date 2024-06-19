package com.example.demo.utilities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
//@ToString
public class ViewManager {
	
	
	
	//impostati default a false dal bean
	
	private boolean beforeLoginPage_isVisible;
	private boolean indexPage_isVisible;
	private boolean	formLogin_isVisible;
	private boolean	formAddToPeopleGroup_isVisible;
	private boolean	getPersonDetailsPage_isVisible;
	private boolean getPersonArray_isVisible;
	private boolean getPersonArrayReactive_isVisible;

	private boolean getErrorPage_isVisible;
	private boolean formSearchPerson_isVisible;
	private boolean gestionaleIn_isVisible;
	private boolean updateMemberOfPeopleGroup_isVisible;
	private boolean deleteMemberOfPeopleGroup_isVisible;
	private Map<String,Object> attributesMap;
	
	private ModelsContainer modelsContainer;
	
	
	//aggiorno la sessione ed il modello
	public ViewManager updateView(Model model) 
	{
		return this
				.updateModelView(model);
	}
	

	
	//passo i flag al model
	private ViewManager updateModelView(Model model)
	{

		 try {
			model.addAllAttributes((Map<String,Boolean>)this.getAllViewFlag());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 model.addAllAttributes(this.attributesMap);
		 
		modelsContainer= new ModelsContainer();
		modelsContainer.getNavigationMap().put("CURRENT", model);
		
		System.err.println("InViewManager***"+modelsContainer.getNavigationMap());
		 return this;
		

	}
	
	public ViewManager addAttributeToMap(String attributeName,Object object) {
		
		Consumer<Map<String,Object>> addAttribute= map->map.put(attributeName, object);
		Runnable createMapIfAbsentAndAddAttribute=()->{
									attributesMap=new HashMap<String,Object>();
									addAttribute.accept(attributesMap);;
								};

		Optional.ofNullable(attributesMap)						
								.ifPresentOrElse(
										addAttribute,
										createMapIfAbsentAndAddAttribute
										);
		
		
		return this;
		
	
	}
	
	//prendo tutti i flag con la reflection
	public Map<String, Boolean> getAllViewFlag() throws IllegalAccessException {
		Map<String, Boolean> viewFlags = new HashMap<>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType() == boolean.class) {
				field.setAccessible(true);
				viewFlags.put(field.getName(), (Boolean) field.get(this));
			}
		}
		return viewFlags;
	}
	


}
