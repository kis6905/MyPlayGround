package com.is.mpg.dao;

import java.util.List;

import com.is.mpg.vo.MyService;

/**
 * @author iskwon
 */
public interface MyServiceMapper {
	
	List<MyService> getMyServiceList();
	MyService getMyServiceOfServiceId(Integer serviceId);
	
}
