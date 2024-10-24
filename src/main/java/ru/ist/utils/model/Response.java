package ru.ist.utils.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Response<T> {
    private final Long count;
    private final List<T> results;
}
