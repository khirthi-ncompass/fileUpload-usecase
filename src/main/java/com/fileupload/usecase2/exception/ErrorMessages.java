package com.fileupload.usecase2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    WRONG_FORMAT("The storage you specified doesn't exist. Please enter HDD or DB storage"),
    FILE_ID_DOESNT_EXIST("The fileID you entered doesn't exist. Please enter valid fileID"),
    FILE_DOESNT_EXIST("You haven't selected any file. Please select a file");

    private String errorMessage;
}
