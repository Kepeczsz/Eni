package com.TaskBoard.TaskBoard.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUsers {
    private int pageSize;
    private int pageIndex;
    private String fieldName;
    private String searchValue;
}
