package com.cdw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdw.business.piece.AmazonClient;

@CrossOrigin
@RestController
	@RequestMapping("/Pieces")
	public class BucketController {

	    private AmazonClient amazonClient;

	    @Autowired
	    BucketController(AmazonClient amazonClient) {
	        this.amazonClient = amazonClient;
	    }

	    @PostMapping("/FileUpload")
	    public String[] uploadFile(@RequestPart(value = "file") MultipartFile file) {
	    	String[] rtrnArr = new String[1];
	        rtrnArr[0] = this.amazonClient.uploadFile(file);
	        return rtrnArr;
	    }

	    @DeleteMapping("/RemoveFile")
	    public String[] deleteFile(@RequestParam String fileName) {
	    	String[] rtrnArr = new String[1];
	        rtrnArr[0] = this.amazonClient.deleteFileFromS3Bucket(fileName);
	        return rtrnArr;
	    }
	}
