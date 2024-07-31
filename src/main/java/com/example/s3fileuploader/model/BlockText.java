package com.example.s3fileuploader.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Entry")
public class BlockText {
    @Id
    @Column(name="block_id")
    private String blockId;

    @Column(name="text")
    private String text;
}
