package org.example.chapter02.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    public FolderResponse getFolderByNo(Long folderNo){
        return new FolderResponse();
    }

    public List<FolderResponse> getFolderList(){
        return List.of(
          new FolderResponse(),
          new FolderResponse(),
          new FolderResponse()
        );
    }

    public FolderResponse createFolderResponse(FolderRequest folderRequest){
        return new FolderResponse();
    }

}
