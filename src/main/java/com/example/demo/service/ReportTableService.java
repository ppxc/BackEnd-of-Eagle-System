package com.example.demo.service;

import com.example.demo.entity.CurGzlTableRy;
import java.util.List;

public interface ReportTableService {
    List<CurGzlTableRy> getCurGzlData(String startDate, String endDate, String comName, String groups, String userName);

    String getMaxTjDate();

}