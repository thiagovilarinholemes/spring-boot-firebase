package com.firebasecrud.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class StorageService {
	
	private String bucketName = "course-kubernet.appspot.com";
	
	// Usado para upload de arquivos
	private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName); // Inserir dentro da pasta "images/"+fileName
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./serviceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/"+bucketName+"/o/%s?alt=media", URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
	
	// Usado para converter arquivo MultipartFile
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    // Pega a extensão do arquivo
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    // Upload File
    public Object upload(MultipartFile multipartFile) {

        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name. 

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            return TEMP_URL;                     // Your customized response
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
    
    // Download File
    public String download(String fileName) throws IOException {
        String destFileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));// to set random strinh for destination file name
        String destFilePath = "/home/thiago/Downloads/" + destFileName; // to set destination file path
        
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./serviceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        blob.downloadTo(Paths.get(destFilePath));
        return "Download com sucesso!!!";
    }
    
    // Delete File
    public String delete(String fileName) throws IOException {
    	Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./serviceAccountKey.json"));
    	Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    	try {
    		boolean blob = storage.delete(BlobId.of(bucketName, fileName));
    		return "Deleted com sucesso!!!";
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}    	
    }
}
