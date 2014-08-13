package com.berrycloud.uploader;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

@Controller
public class FileUploadController {

	static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file)
			throws AmazonServiceException, AmazonClientException, IOException, InterruptedException {

		LOG.debug("File Name: " + file.getOriginalFilename() + " File Size: " + file.getSize());

		DefaultAWSCredentialsProviderChain credentialProviderChain = new DefaultAWSCredentialsProviderChain();
		TransferManager tx = new TransferManager(credentialProviderChain.getCredentials());

		final ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());

		Upload myUpload = tx.upload("upload.berrycloud.co.uk", file.getOriginalFilename(), file.getInputStream(),
				objectMetadata);

		myUpload.waitForCompletion();

		tx.shutdownNow();

		HttpHeaders headers = new HttpHeaders();

		// probably should return the location of the file

		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}

}