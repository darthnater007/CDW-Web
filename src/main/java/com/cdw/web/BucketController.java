package com.cdw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cdw.business.piece.AmazonClient;

@CrossOrigin
@Controller
	@RequestMapping("/Pieces")
	public class BucketController {

	    private AmazonClient amazonClient;

	    @Autowired
	    BucketController(AmazonClient amazonClient) {
	        this.amazonClient = amazonClient;
	    }

	    @PostMapping("/FileUpload")
	    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
	        return this.amazonClient.uploadFile(file);
	    }

	    @DeleteMapping("/RemoveFile")
	    public String deleteFile(@RequestPart(value = "fileName") String fileName) {
	        return this.amazonClient.deleteFileFromS3Bucket(fileName);
	    }
	}
