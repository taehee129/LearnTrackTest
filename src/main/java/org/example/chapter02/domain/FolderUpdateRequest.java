package org.example.chapter02.domain;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class FolderUpdateRequest {

    @Size(min=0,max=100)
    private String title;
    @Size(min=0,max=1000)
    private String description;
}
