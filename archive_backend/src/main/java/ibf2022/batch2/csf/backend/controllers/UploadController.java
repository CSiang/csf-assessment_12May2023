package ibf2022.batch2.csf.backend.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
public class UploadController {

	@Autowired
	ArchiveRepository arcRepo;

	@Autowired
	private ImageRepository imgRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path = "upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> fileUpload(@RequestPart MultipartFile file, 
	@RequestPart String name, @RequestPart String title, @RequestPart(required=false) String comment) throws IOException{

		System.out.println("At upload file");

		System.out.println("Name: " + name);
		System.out.println("Title: " + title);
		System.out.println("Comment: " + comment);
		System.out.println("FileName: " + file.getOriginalFilename());
		System.out.println("Content Type: " + file.getContentType());

		//Need to do S3 upload here.....
		imgRepo.upload(file, name, title, comment);

		String bundleId = arcRepo.recordBundle(name, title, comment);
		JsonObject jo = Json.createObjectBuilder().add("bundleId", bundleId).build();

		// IF failure occurs...
		if(bundleId == null){
			JsonObject joFail = Json.createObjectBuilder().add("error", "Upload fail.").build();

			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.contentType(MediaType.APPLICATION_JSON).body(joFail.toString());
		}
	
		return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(jo.toString());
	}
	

	// TODO: Task 5
	@GetMapping(path="bundle/{id}")
	public ResponseEntity<String> GetByBundleId(@PathVariable String id){

		Optional<Document> opt = arcRepo.getBundleByBundleId(id);

		if(opt.isPresent()){
			Document doc = opt.get();
			JsonObjectBuilder job = Json.createObjectBuilder();

			if(doc.getString("comments") == null){
				job.add("comments", JsonObject.NULL); 
			} else {
				job.add("comments", doc.getString("comments")); 
			}
				
			job.add("bundleId", doc.getString("bundleId"))
				.add("date", doc.getString("date"))
				.add("title", doc.getString("title"))
				.add("name", doc.getString("name"));


			return ResponseEntity.ok().body(job.build().toString());
		}

		JsonObject joFail = Json.createObjectBuilder().add("error", "File not found.").build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(joFail.toString());
	}
	

	// TODO: Task 6
	@GetMapping(path="bundles")
	public ResponseEntity<String> getBundleList(){

		List<Document> docs=  arcRepo.getBundles();
		JsonArrayBuilder jab = Json.createArrayBuilder();

		if(docs.size()>0){
			
			for(Document doc: docs){
				System.out.println(doc);
				JsonObjectBuilder job = Json.createObjectBuilder();
					
				job.add("bundleId", doc.getString("bundleId"))
					.add("date", doc.getString("date"))
					.add("title", doc.getString("title"));

				jab.add(job.build());
			}

			return ResponseEntity.ok().body(jab.build().toString());
		}

		JsonObject joFail = Json.createObjectBuilder().add("error", "File not found.").build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(joFail.toString());
	}
}
