package ibf2022.batch2.csf.backend.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;

@RestController
public class UploadController {

	@Autowired
	ArchiveRepository arcRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> fileUpload(@RequestPart MultipartFile file, 
	@RequestPart String name, @RequestPart String title, @RequestPart String comment){

		System.out.println("At upload file");

		System.out.println("Name: " + name);
		System.out.println("Title: " + title);
		System.out.println("Comment: " + comment);
		System.out.println("FileName: " + file.getOriginalFilename());
		System.out.println("Content Type: " + file.getContentType());

		//Need to do S3 upload here.....

		String bundleId = arcRepo.recordBundle(name, title, comment);

		



		
	

		return null;
	}
	

	// TODO: Task 5
	

	// TODO: Task 6

}
