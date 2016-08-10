package com.is.mpg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.is.mpg.dao.MyServiceMapper;
import com.is.mpg.service.MyServiceService;
import com.is.mpg.vo.MyService;

@Service(value = "myServiceServiceImpl")
public class MyServiceServiceImpl implements MyServiceService {

	@Autowired
	private MyServiceMapper myServiceMapper;
	
	/**
	 * 서비스 목록 리턴
	 */
	@Override
	public List<MyService> getMyServiceList() {
		return myServiceMapper.getMyServiceList();
	}

	/**
	 * MyService 리턴
	 * 
	 * @param serviceId
	 * @return MyService
	 */
	@Override
	public MyService getMyService(Integer serviceId) {
		return myServiceMapper.getMyServiceOfServiceId(serviceId);
	}
	
}
