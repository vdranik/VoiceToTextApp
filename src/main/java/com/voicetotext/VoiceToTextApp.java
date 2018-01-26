package com.voicetotext;

import com.voicetotext.controller.AudioTextController;
import com.voicetotext.repository.AudioTextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

@SpringBootApplication
public class VoiceToTextApp implements CommandLineRunner{

	private static final String RESOURCES_FILE_PATH = "src/main/resources/wav_files/";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AudioTextRepository audioTextRepository;

	@Autowired
	AudioTextController audioTextController;

	public static void main(String[] args) {
		SpringApplication.run(VoiceToTextApp.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		//Test DB
//		logger.info("All audio texts -> {}", audioTextRepository.findAll());
//		logger.info("AudioText id 0 -> {}", audioTextRepository.findById(0));
//		logger.info("Inserting AudioText id 1 -> {}", audioTextRepository.insert(new AudioText(1, "test1", "test1", 1L,1L)));
//		logger.info("Updating AudioText id 1 -> {}", audioTextRepository.update(new AudioText(1, "test2", "test2", 2L, 2L)));
//		audioTextRepository.deleteById(1);

		//Load file
		String filepath = RESOURCES_FILE_PATH + "example1.wav";
		InputStream input = new FileInputStream(Paths.get(filepath).toFile());

		//Get responce and write info into DB
		audioTextController.process(input);

		//Read info from DB
		logger.info("All audio texts -> {}", audioTextRepository.findAll());
	}
}
