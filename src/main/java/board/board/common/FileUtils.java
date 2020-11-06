package board.board.common;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;
import board.board.entity.BoardFileEntity;

@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequests) throws IllegalStateException, IOException{
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequests)) {
			return null;
		}
		
		List<BoardFileDto> fileList = new ArrayList<BoardFileDto>();
		
		// 파일이 업로드 될 폴더 생성
		// images/yyyyMMdd 형식으로 폴더 생성(폴더가 없을 경우에만)	
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequests.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequests.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				
				if(multipartFile.isEmpty() == false) {
					
					// 피일 형식을 확인하고 그에 따라 이미지의 확장자를 지정
					// 파일의 이름에서 가져오면 X 
					contentType = multipartFile.getContentType();
					
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					// 서버에 저장될 중복되지 않을 이름 생성(밀리초 이용 경우 중복 가능성 있으므로 나노초 이용함) 
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					// DB에 저장할 정보 세팅
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					// 업로드 된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장함
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		
		return fileList;
		
	}
	
	
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequests) throws IllegalStateException, IOException{
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequests)) {
			return null;
		}
		
		List<BoardFileEntity> fileList = new ArrayList<>();
		
		// 파일이 업로드 될 폴더 생성
		// images/yyyyMMdd 형식으로 폴더 생성(폴더가 없을 경우에만)	
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequests.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequests.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				
				if(multipartFile.isEmpty() == false) {
					
					// 피일 형식을 확인하고 그에 따라 이미지의 확장자를 지정
					// 파일의 이름에서 가져오면 X 
					contentType = multipartFile.getContentType();
					
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					// 서버에 저장될 중복되지 않을 이름 생성(밀리초 이용 경우 중복 가능성 있으므로 나노초 이용함) 
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					// DB에 저장할 정보 세팅
					BoardFileEntity boardFile = new BoardFileEntity();
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					// 업로드 된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장함
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		
		return fileList;
		
	}
}
