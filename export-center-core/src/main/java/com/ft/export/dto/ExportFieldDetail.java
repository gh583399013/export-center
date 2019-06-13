package com.ft.export.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author mask
 * @date 2019/6/13 20:12
 * @desc
 */
@Data
public class ExportFieldDetail implements Serializable{
    private String dateFormatPattern;
    private Integer precision;
    private Map<String, String> valueDescMap;
}
