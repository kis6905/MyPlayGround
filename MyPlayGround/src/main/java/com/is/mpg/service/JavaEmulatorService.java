package com.is.mpg.service;

import org.springframework.stereotype.Service;

@Service
public interface JavaEmulatorService {
	
	public String runJava(String javaCode, String savePath);
	
}
