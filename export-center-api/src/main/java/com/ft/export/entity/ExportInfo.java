package com.ft.export.entity;

import com.ft.export.enums.ExportTypeEnum;
import com.ft.export.enums.ExportTypeProEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mask
 * @date 2019/6/14 18:46
 * @desc
 */
@Data
public class ExportInfo implements Serializable{
    private String operator;
    private Date exportTime;
    private ExportTypeEnum exportTypeEnum;
    private List<String> fieldList;
    private ExportTypeProEnum exportTypeProEnum;
}
