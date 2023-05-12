package ibf2022.batch2.csf.backend.repositories;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	public List<String> upload(MultipartFile file, String name, String title, String comment) throws IOException {

		List<String> urlList = new LinkedList<>();

		try {
			ZipInputStream zip = new ZipInputStream(file.getInputStream());	
            ZipEntry ze;
            while ((ze = zip.getNextEntry()) != null) {
	
				System.out.println("new file.");
                System.out.format("File: %s Size: %d\n",
                        ze.getName(), ze.getSize());
			 // write file content
			//  FileOutputStream fos = new FileOutputStream(newFile);

			//  zip.readNBytes((int) ze.getSize());

			 Map<String, String> userData = new HashMap<>();
			 userData.put("name", name);
			 userData.put("title", title);
			 userData.put("comments",comment);
	 
			 ObjectMetadata metadata = new ObjectMetadata();
			//  metadata.setContentType(ze.); 
			 metadata.setContentLength(ze.getSize());
			 metadata.setUserMetadata(userData);

			 InputStream input = new ByteArrayInputStream(zip.readNBytes((int) ze.getSize()));

			 PutObjectRequest putReq = new PutObjectRequest(DOBucket.trim(), ze.getName(), input,metadata);

			 putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
   
			 s3.putObject(putReq);

			 String url = "https://csiang-tfip.sgp1.digitaloceanspaces.com/" + ze.getName();
			 urlList.add(url);

			//  int len;
			//  while ((len = zip.read(buffer)) > 0) {
			// 	 fos.write(buffer, 0, len);
			//  }
			//  fos.close();
			
			
			// try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
			// BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {

			
			// 	int len;
			// 	while ((len = stream.read(buffer)) > 0) {
			// 		bos.write(buffer, 0, len);
			// 	}
			// }
            }

			zip.closeEntry();
			zip.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}


		//    // Below are to set the metadata.
		//    Map<String, String> userData = new HashMap<>();
		//    userData.put("name", name);
		//    userData.put("title", title);
		//    userData.put("comments",comment);
   
		//    ObjectMetadata metadata = new ObjectMetadata();
		//    metadata.setContentType(file.getContentType()); 
		//    metadata.setContentLength(file.getSize());
		//    metadata.setUserMetadata(userData);
   
		//    PutObjectRequest putReq = new PutObjectRequest(DOBucket.trim(), file.getOriginalFilename(), file.getInputStream(),metadata);
		//    putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
   
		   return urlList;
	}
}
