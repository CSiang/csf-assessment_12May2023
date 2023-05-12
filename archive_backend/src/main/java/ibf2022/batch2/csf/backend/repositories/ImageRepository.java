package ibf2022.batch2.csf.backend.repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Value("${db.storage.bucketName}")
    private String DOBucket;
    
    @Autowired
	// @Qualifier("S3")
    private AmazonS3 s3;

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public Object upload(MultipartFile file, String name, String title, String comment) throws IOException {

		try {
			ZipInputStream zip = new ZipInputStream(file.getInputStream());	
            ZipEntry ze;
            while ((ze = zip.getNextEntry()) != null) {
	
				System.out.println("new file.");
                System.out.format("File: %s Size: %d\n",
                        ze.getName(), ze.getSize());
			
			
			// try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
			// BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {

			
			// 	int len;
			// 	while ((len = stream.read(buffer)) > 0) {
			// 		bos.write(buffer, 0, len);
			// 	}
			// }
            }
			
		} catch (IOException e){
			e.printStackTrace();
		}

		   // Below are to set the metadata.
		   Map<String, String> userData = new HashMap<>();
		   userData.put("name", name);
		   userData.put("title", title);
		   userData.put("comments",comment);
   
		   ObjectMetadata metadata = new ObjectMetadata();
		   metadata.setContentType(file.getContentType()); 
		   metadata.setContentLength(file.getSize());
		   metadata.setUserMetadata(userData);
   
		   PutObjectRequest putReq = new PutObjectRequest(DOBucket.trim(), file.getOriginalFilename(), file.getInputStream(),metadata);
		   putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
   
		   return s3.putObject(putReq);
	}
}
