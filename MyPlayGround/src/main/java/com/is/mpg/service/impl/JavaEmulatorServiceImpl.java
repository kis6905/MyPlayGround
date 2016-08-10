package com.is.mpg.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.is.mpg.commons.Utility;
import com.is.mpg.service.JavaEmulatorService;
import com.is.mpg.service.modules.CommandThread;

@Service(value = "javaEmulatorServiceImpl")
public class JavaEmulatorServiceImpl implements JavaEmulatorService {

	private static final Logger logger = LoggerFactory.getLogger(JavaEmulatorServiceImpl.class);
	
	private static final int LIMIT_RUNNING_SECONDS = 3;
	
	/**
	 * 자바 코드 실행 및 결과 리턴
	 * 
	 * @param javaCode
	 * @param resourcesPath resources 디렉토리 경로
	 * @return 자바 코드 실행 결과 리턴
	 */
	@Override
	public String runJava(String javaCode, String resourcesPath) {
		String result = null;
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		File tempJavaFile = null;
		File tempClassFile = null;

		// 만들어질 java 파일도 프로젝트 내에 저장하려고 했으나, java 파일은 톰캣이 삭제하는거 같다.
		// 우선 어쩔수 없이 {catalina.home} 밑에 저장...
		String javafilesPath = resourcesPath + "/javafiles/";
		String savePath = System.getProperty("catalina.home") + "/";
		
		// Java 코드를 실행하기 위해 임시로 만드는 .java .class 명
		String className = "TempClass" + Utility.getCurrentDateToString("yyyyMMddHHmmss");
		
		try {
			// Template.txt 파일을 읽어 클래스 명과 main() 내용 치환해 "TempClass + 현재시간.java" 형식으로 저장
			String tempJavaCode = "";
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(javafilesPath + "Template.txt"), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null)
				tempJavaCode += line;
			
			tempJavaCode = tempJavaCode.replace("#className", className);
			tempJavaCode = tempJavaCode.replace("#code", javaCode);
			
			writer = new BufferedWriter(new FileWriter(savePath + className + ".java", true));
			writer.write(tempJavaCode);
			writer.flush();
			writer.close();
			
			// 컴파일 (.java -> .class)
			logger.info("~~ [Compiling...]");
			CommandThread compileThread = new CommandThread("javac " + savePath + className + ".java");
			compileThread.start();
			compileThread.join();
			
			tempClassFile = new File(savePath + className + ".class");
			if (tempClassFile.exists()) {
				logger.info("~~ [Successful compiling have done.]");
				
				// 실행
				logger.info("~~ [Run the class file...]");
				CommandThread runThread = new CommandThread("java -cp " + savePath + " " + className);
				runThread.start();
				
				int runningSeconds = 0;
				boolean forcedTermination = false;
				while (runThread.isAlive()) {
					// 3초 동안 프로세스가 쓰레드가 살아있으면 인터럽트를 발생시킨다.
					if (runningSeconds == LIMIT_RUNNING_SECONDS) {
						runThread.interrupt();
						forcedTermination = true;
						break;
					}
					Thread.sleep(1000);
					runningSeconds++;
				}
				
				// 쓰레드에 인터럽트를 발생시켜도 서버자체에 실행중인 java 프로세스는 죽지 않는다. 때문에 pid를 찾아 직접 kill 한다.
				if (forcedTermination) {
					result = "Timeout";
					
					// PID 찾는 쉘 스크립트 실행
//					String getPidCommand = resourcesPath + "/sh/get-pid.sh " + className;
					
					String[] getPidCommands = { 
							"/bin/sh",
							"-c",
							"ps aux | grep " + className + " | grep -v grep | awk '{print $2}'" };
					
					CommandThread getPidThread = new CommandThread(getPidCommands);
					getPidThread.start();
					getPidThread.join();
					String runningPid = getPidThread.getResult();
					
					logger.info("~~ [Kill the running process, pid = {}]", runningPid);
					
					String killCommand = "kill -9 " + runningPid;
					CommandThread killThread = new CommandThread(killCommand);
					killThread.start();
					killThread.join();
					
					logger.info("~~ [Kill result = {}]", killThread.getResult());
				}
				else {
					result = runThread.getResult();
				}
				logger.info("~~ [Terminate process.]");
				// class 파일을 실행시켜 발생한 에러는 그냥 화면에 출력한다.
			}
			else {
				logger.info("~~ [Compiling fails.]");
				// TODO 컴파일 실패 시 어떻게 처리??
				result = "Failed Compiling.";
			}
		} catch (Exception e) {
			logger.error("~~ An error occurred!", e);
			result = "Server error occurred!";
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// 사용한 java, class 파일 삭제
			tempJavaFile = new File(savePath + className + ".java");
			if (tempJavaFile.exists())
				tempJavaFile.delete();
			if (tempClassFile != null && tempClassFile.exists())
				tempClassFile.delete();
		}
		return result;
	}

}
