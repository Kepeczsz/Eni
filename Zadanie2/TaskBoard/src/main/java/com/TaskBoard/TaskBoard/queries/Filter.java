package com.TaskBoard.TaskBoard.queries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Filter {
    public String fieldName;
    public String search;
}
