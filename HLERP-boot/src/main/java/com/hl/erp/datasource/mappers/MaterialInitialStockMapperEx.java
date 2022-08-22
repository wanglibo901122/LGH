package com.hl.erp.datasource.mappers;

import com.hl.erp.datasource.entities.MaterialInitialStock;

import java.util.List;

public interface MaterialInitialStockMapperEx {

    int batchInsert(List<MaterialInitialStock> list);

}