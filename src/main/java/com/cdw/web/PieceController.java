package com.cdw.web;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cdw.business.piece.Piece;
import com.cdw.business.piece.PieceRepository;
import com.cdw.util.CDWMaintenanceReturn;

@CrossOrigin
@Controller    
@RequestMapping(path="/Pieces")
public class PieceController extends BaseController{
	@Autowired 
	private PieceRepository pieceRepository;

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
	
	@GetMapping(path = "/ViewPiece", produces = "application/pdf")
	public @ResponseBody FileSystemResource sendFile(@RequestParam String fileName) {
		String path = "pieceUploads/" + fileName;
		
		return new FileSystemResource(path);
	}
	
	@PostMapping(path="/FileUpload")
	public @ResponseBody
	String[] uploadFileHandler(@RequestParam String name, @RequestParam("file") MultipartFile file) {
		String[] returnArr = new String[1];
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File("pieceUploads");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();


				returnArr[0] = "success";
				return returnArr;
			} catch (Exception e) {
				returnArr[0] =  "failure";
				return returnArr;
			}
		} else {
			returnArr[0] = "empty";
			return returnArr;
		}
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
	
	@GetMapping(path="/RemoveFile")
	public @ResponseBody String[] deleteFile (@RequestParam String fileName) {
		String[] returnArr = new String[1];
		String path = "pieceUploads/" + fileName;
		try {
			File file = new File(path);
        	
    		if(file.delete()){
    			returnArr[0] = "success";
    			return returnArr;
    		}else{
    			System.out.println("Delete operation failed.");
    			returnArr[0] = "failure";
    			return returnArr;
    		}
		}
		catch (Exception e) {
			returnArr[0] = "failure";
			return returnArr;
		}
		
	}

	@PostMapping(path="/Change") // add logic-- if(file has been changed){ delete old file and create new} -- maybe do this from front end
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
