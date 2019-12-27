package com.anthem.emep.dckr.microsvc.dataservicexbdf.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.service.IProcessor;

@Component
public class StudentTask implements Runnable {

	@Autowired
	@Qualifier("studentProcessor")
	private IProcessor processor;

	public StudentTask() {
	}

	@Override
	public void run() {
		System.out.println(" I am running job ");
		processor.process();
		System.out.println(" I am done with job");
	}

}
