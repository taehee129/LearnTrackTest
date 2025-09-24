package org.example.chapter02.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
//@AllArgsConstructor
public class FolderResponse {
    @JsonProperty("No")
    Object a = null;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long folderNo;
    private String title;
//    private String description;
//    private Long parentNo;
//    private Long userNo;
//
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    private LocalDateTime insertDate;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    private LocalDateTime updateDate;
    private List<Folder> children;
    public FolderResponse(Long folderNo, String title){
        this.folderNo = folderNo;
        this.title = title;
    }

    public static FolderResponse fromEntity(Folder e){
        return new FolderResponse(e.getFolderNo(), e.getTitle());
    }
}
