package com.cdw.web;


import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cdw.business.piece.AmazonClient;
import com.cdw.business.piece.Piece;
import com.cdw.business.piece.PieceRepository;
import com.cdw.util.CDWMaintenanceReturn;

@CrossOrigin
@Controller    
@RequestMapping(path="/Pieces")
public class PieceController extends BaseController{

	private AmazonClient amazonClient;
	
	@Autowired 
	private PieceRepository pieceRepository;
	
	@Autowired
	 PieceController(AmazonClient amazonClient) {
	        this.amazonClient = amazonClient;
	    }

	@GetMapping(path="/Publications")
	public @ResponseBody Iterable<Piece> getAllPublications() {
		return pieceRepository.findAllByPublication(true);
	}
	
	@GetMapping(path="/Workshop")
	public @ResponseBody Iterable<Piece> getAllWorkshops() {
		return pieceRepository.findAllByPublication(false);
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody List<Piece> getPiece(@RequestParam int id) {
		Optional<Piece> u = pieceRepository.findById(id);
		return getReturnArray(u);
	}
	
	@PostMapping(path = "/ViewPiece")
	public @ResponseBody String sendFile(@RequestParam String fileName) {
		String url = "https://s3.us-east-2.amazonaws.com/com.cdiywriters/" + fileName;
		
		return url;
	}
	//for S3 Bucket
    @PostMapping("/FileUpload")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }
    
    @DeleteMapping("/RemoveFile")
    public String deleteFile(@RequestPart(value = "fileName") String fileName) {
        return this.amazonClient.deleteFileFromS3Bucket(fileName);
    }
	
	@PostMapping(path="/Add") 
	public @ResponseBody CDWMaintenanceReturn addNewPiece (@RequestBody Piece piece) {
		try {
			System.out.println();
			LocalDateTime now = LocalDateTime.now();
			Timestamp ts = Timestamp.valueOf(now.minusHours(4));
			piece.setSubmitted(ts);
			pieceRepository.save(piece);
			return CDWMaintenanceReturn.getMaintReturn(piece);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(piece, dive.getRootCause().toString());
		}
		catch (Exception e) {
			e.printStackTrace();
			return CDWMaintenanceReturn.getMaintReturnError(piece, e.getMessage());
		}
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody CDWMaintenanceReturn deletePiece (@RequestParam int id) {
		
		Optional<Piece> piece = pieceRepository.findById(id);
		String path = "pieceUploads/" + piece.get().getFileName();
		try {
			File file = new File(path);
        	
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation failed.");
    		}
			pieceRepository.delete(piece.get());
			return CDWMaintenanceReturn.getMaintReturn(piece.get());
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(piece, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(piece, e.toString());
		}
		
	}

	@PostMapping(path="/Change") 
	public @ResponseBody CDWMaintenanceReturn updatePiece (@RequestBody Piece piece) {
		try {
			pieceRepository.save(piece);
			return CDWMaintenanceReturn.getMaintReturn(piece);
		}
		catch (DataIntegrityViolationException dive) {
			return CDWMaintenanceReturn.getMaintReturnError(piece, dive.getRootCause().toString());
		}
		catch (Exception e) {
			return CDWMaintenanceReturn.getMaintReturnError(piece, e.toString());
		}
		
	}

}
