package com.tdmapper.application.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentDto {

    private String id;
    private String filename;

    @Override
    public String toString(){
        return this.filename;
    }
}
