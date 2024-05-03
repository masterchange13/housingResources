package com.example.community.dto;

import com.example.community.entity.House;
import lombok.Data;

@Data
public class HouseDto extends House {

    private String categoryName;

    private Integer copies;
}
