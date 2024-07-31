package com.example.s3fileuploader.repository;

import com.example.s3fileuploader.model.BlockText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTextRepository extends JpaRepository<BlockText, String> {
    List<BlockText> findAllByBlockId(String blockId);
}
