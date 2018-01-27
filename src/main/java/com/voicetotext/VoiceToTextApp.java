package com.voicetotext;

import com.voicetotext.controller.ATController;
import com.voicetotext.service.ATService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import static com.voicetotext.conf.ATConfigurationProps.*;

@SpringBootApplication
public class VoiceToTextApp implements CommandLineRunner{

	private static final String RESOURCES_FILE_PATH = "src/main/resources/wav_files/";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ATService atService;

	@Autowired
	private ATController atController;

	public static void main(String[] args) {
		SpringApplication.run(VoiceToTextApp.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		// Load file
		String filepath = RESOURCES_FILE_PATH + "example1.wav";
		InputStream input = new FileInputStream(Paths.get(filepath).toFile());

		// Set Bing Conf
		atController.setFormat(OutputFormat.Simple);
		atController.setLanguage(Language.en_US);
		atController.setMode(RecognitionMode.Interactive);

		// Get responce and write info into DB
		atController.process(input);

		// Read info from DB
		logger.info("All audio texts -> {}", atService.findAll());
	}
}
