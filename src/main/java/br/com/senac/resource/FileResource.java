package br.com.senac.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senac.constantes.Messages;
import br.com.senac.model.FileInfo;
import br.com.senac.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/arquivo")
public class FileResource {
	
	@Autowired
	FileStorageService storageService;
	
	@Operation(description = Messages.SWAGGER_INSERT)
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file){
		try {
			storageService.save(file);
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			
			String urlDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/uploads/")
					.path(fileName)
					.toUriString();
			
			return ResponseEntity.ok().body("Arquivo enviado com sucesso: " + urlDownload);
		
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Não pode ser feito o upload");
		}
	}
	
	@Operation(description = Messages.SWAGGER_GET)
	@GetMapping("/files/{filename:..+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename){
		Resource file = storageService.load(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@Operation(description = Messages.SWAGGER_GET_ALL)
	@GetMapping("/arquivos")
	public ResponseEntity<List<FileInfo>> listarTodosArquivos(){
		List<FileInfo> fileInfos = storageService.listarTodosArquivos().map(
				path -> {
					String filename = path.getFileName().toString();
					String url = MvcUriComponentsBuilder.fromMethodName(FileResource.class, "getFile", 
							path.getFileName().toString()).build().toString();
				
				return new FileInfo(filename, url);
						
				}).collect(Collectors.toList());
		return ResponseEntity.ok().body(fileInfos);		
		
	}
	
	@Operation(description = Messages.SWAGGER_DELETE)
	@GetMapping("/excluirTodosArquivos")
	public ResponseEntity<String> excluirArquivos(){
		storageService.deleteAll();
		return ResponseEntity.ok().body("Arquivos excluídos com sucesso!");
	}
	
	
}
