package com.example.demo.dao.asset;

import com.example.demo.entity.asset.AssetAllReportView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author cyb
 * @desc Generate-Dao
 * @date 2019-07-06
 */
public interface AssetAllReportViewDao extends JpaRepository<AssetAllReportView, String> {
    List<AssetAllReportView> findAllByUsername(String username);
}