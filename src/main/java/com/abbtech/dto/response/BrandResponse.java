package com.abbtech.dto.response;

import com.abbtech.annotations.LogIgnore;
import com.abbtech.dto.ModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse{
        Integer id;
        String name;
        @LogIgnore
        String country;
        @LogIgnore
        List<ModelDto> models;

}

