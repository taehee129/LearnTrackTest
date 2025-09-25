package org.example.chapter02.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFolderResultResponse {

    private boolean success;
    private String message;
}
