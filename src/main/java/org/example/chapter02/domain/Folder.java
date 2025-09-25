package org.example.chapter02.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Folder {

    private Long folderNo;
    private String title;
    private String description;
    private Long parentNo;
    private Long userNo;
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;
    private List<Folder> subFolder;

}
