package org.example.chapter02.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponse {

    @JsonProperty("No")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long folderNo;
    private String title;
    private String description;
    private Long parentNo;
    private Long userNo;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime insertDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime updateDate;
    private List<ChildFolderDto> childDtoList;


    public static FolderResponse fromEntity(Folder e){
        return new FolderResponse();
    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChildFolderDto{
        private Long id;
        private String title;
    }

}
