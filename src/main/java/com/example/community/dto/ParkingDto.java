package com.example.community.dto;

import com.example.community.entity.Parking;
import com.example.community.entity.Parkinghouse;
import lombok.Data;

import java.util.List;

@Data
public class ParkingDto extends Parking {

    private String categoryName;
}
