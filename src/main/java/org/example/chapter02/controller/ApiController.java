package org.example.chapter02.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.chapter02.domain.Folder;
import org.example.chapter02.domain.FolderRequest;
import org.example.chapter02.domain.FolderResponse;
import org.example.chapter02.domain.FolderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/folders")
public class ApiController {
    private final FolderService folderService;
    public ApiController(FolderService folderService){ this.folderService = folderService; }

    @GetMapping(value = "/{folderNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FolderResponse> getFolderByNo(@PathVariable Long folderNo){
        return ResponseEntity.ok(folderService.getFolderByNo(folderNo));
    }

    @GetMapping()
    public ResponseEntity<List<FolderResponse>> getFolderList() {
        return ResponseEntity.ok(folderService.getFolderList());
    }

    @PostMapping()
    public ResponseEntity<FolderResponse> createFolder(
            @RequestBody FolderRequest folderRequest
            ){

        System.out.println(folderRequest.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(folderService.createFolderResponse(folderRequest));
    }
}
