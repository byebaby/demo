package com.example.demo.service;

import com.example.demo.entity.AccountingTips;

import java.util.List;

public interface AccountingTipsService {
    List<AccountingTips> findByUsername(String username);
}
