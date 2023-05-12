package ibf2022.batch2.csf.backend.repositories;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

public class ImageRepository {

	//TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public Object upload(MultipartFile file) {

		try {
			ZipInputStream zip = new ZipInputStream(file.getInputStream());	
            ZipEntry ze;
			
            while ((ze = zip.getNextEntry()) != null) {
	
				System.out.println("new file.");
                System.out.format("File: %s Size: %d\n",
                        ze.getName(), ze.getSize());
                        // LocalDate.ofEpochDay(ze.getTime()));
			
			
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
		return null;
	}
}
