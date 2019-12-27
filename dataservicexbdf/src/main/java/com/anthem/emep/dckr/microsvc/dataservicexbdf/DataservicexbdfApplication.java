package com.anthem.emep.dckr.microsvc.dataservicexbdf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anthem.emep.dckr.microsvc.dataservicexbdf.task.StudentTask;

@SpringBootApplication
public class DataservicexbdfApplication implements CommandLineRunner {

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private StudentTask studentTask;

	public static void main(String[] args) {
		SpringApplication.run(DataservicexbdfApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Runnable> tasks = new ArrayList<>();
		tasks.add(studentTask);

		for (Runnable task : tasks) {
			executorService.submit(task);
		}

		executorService.shutdown();
	}

}
