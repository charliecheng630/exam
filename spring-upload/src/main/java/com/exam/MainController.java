package com.exam;

import com.exam.service.GoogleDriveService;
import com.google.api.services.drive.Drive;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class MainController {
	private GoogleDriveService googleDriveService;
	private final String UPLOAD_FOLDER = "C:\\Users\\yuchen\\IdeaProjects\\Exam\\spring-upload\\files";

	@GetMapping("/")
	public String uploadFiles() {
		return "upload";
	}

	@PostMapping("upload/files")
	public String handleFilesUpload(@RequestParam("files") MultipartFile files[], Model map) throws IOException {
		StringBuilder sb = new StringBuilder();
		Drive service = googleDriveService.getDriveService();
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					if (!Files.exists(Paths.get(UPLOAD_FOLDER))) {
						try {
							Files.createDirectories(Paths.get(UPLOAD_FOLDER));
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}

					Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER, file.getOriginalFilename()));
					sb.append("You successfully uploaded " + file.getOriginalFilename() + "!\n");

					map.addAttribute("msg", sb.toString());
				} catch (IOException | RuntimeException e) {
					sb.append("Failued to upload " + (file != null ? file.getOriginalFilename() : "") + " => "
							+ e.getMessage() + String.valueOf(e) + "\n");

					map.addAttribute("msg", sb.toString());
				}
			} else {
				sb.append("Failed to upload file\n");
				map.addAttribute("msg", sb.toString());
			}
			sb.append(file.getOriginalFilename());
		}

		googleDriveService.uploadFilesAndGetURIs(service, "./files");
		map.addAttribute("msg", sb.toString());
		return "upload";
	}
}
