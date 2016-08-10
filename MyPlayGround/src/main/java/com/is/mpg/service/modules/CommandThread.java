package com.is.mpg.service.modules;

import com.is.mpg.commons.Utility;

/**
 * Command 실행 쓰레드
 * 
 * @author iskwon
 */
public class CommandThread extends Thread {

	private String command = null;
	private String[] commands = null;
	private String result = null;
	
	public CommandThread(String command) {
		this.command = command;
	}
	
	public CommandThread(String[] commands) {
		this.commands = commands;
	}
	
	@Override
	public void run() {
		if (command != null)
			result = Utility.runCommand(command);
		else if (commands != null)
			result = Utility.runCommand(commands);
	}
	
	public String getResult() {
		return result;
	}
	
}
