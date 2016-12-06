package com.example.converters;

import org.springframework.core.convert.converter.Converter;

import com.example.model.Group;
import com.example.service.GroupService;

public class GroupConverter implements Converter<String, Group>{

	private GroupService groupService;
	
	public GroupConverter(GroupService groupService){
		this.groupService = groupService;
	}
	
	@Override
	public Group convert(String id) {
		return groupService.findOne(Long.parseLong(id));
	}

}
