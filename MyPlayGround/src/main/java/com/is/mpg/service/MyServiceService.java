package com.is.mpg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.is.mpg.vo.MyService;

@Service
public interface MyServiceService {
	
	public List<MyService> getMyServiceList();
	public MyService getMyService(Integer serviceId);
	
}
