package com.abbtech.dto.request;

public record UpdateModelRequest(
        String name,
        String category,
        Integer yearFrom,
        Integer yearTo) {
}

