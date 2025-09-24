package org.example.chapter02.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    public FolderResponse getFolderByNo(Long folderNo){
        return new FolderResponse(folderNo, "test");
    }

    public List<FolderResponse> getFolderList(){
        return List.of(
          new FolderResponse(1L,"Documents"),
          new FolderResponse(2L, "Pictures"),
          new FolderResponse(3L, "Music")
        );
    }

    public FolderResponse createFolderResponse(FolderRequest folderRequest){
        return new FolderResponse(1L,"test");
    }

}
