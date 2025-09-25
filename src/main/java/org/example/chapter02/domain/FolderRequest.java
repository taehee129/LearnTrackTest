package org.example.chapter02.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class FolderRequest {

    private String title;
    private String description;
    private Long parentNo;
}
