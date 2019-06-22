package com.example.demo.controller.asset;

import com.example.demo.entity.AssetMain;
import com.example.demo.entity.AssetModel;
import com.example.demo.entity.AssetView;
import com.example.demo.mapper.AccountingTipsMapper;
import com.example.demo.mapper.dto.AccountingTipsDto;
import com.example.demo.service.AssetDetailService;
import com.example.demo.service.AssetMainService;
import com.example.demo.service.AssetModelService;
import com.example.demo.service.AssetViewService;
import com.example.demo.vo.Json;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AccountDebtController {
    private final AssetMainService assetMainService;
    private final AssetViewService assetViewService;
    private final AssetDetailService assetDetailService;
    private final AssetModelService assetModelService;
    private final AccountingTipsMapper accountingTipsMapper;

    public AccountDebtController(AssetViewService assetViewService, AssetDetailService assetDetailService, AssetModelService assetModelService, AccountingTipsMapper accountingTipsMapper, AssetMainService assetMainService) {
        this.assetMainService = assetMainService;
        this.assetViewService = assetViewService;
        this.assetDetailService = assetDetailService;
        this.assetModelService = assetModelService;
        this.accountingTipsMapper = accountingTipsMapper;
    }

    /**
     * 表单模板配置页面
     *
     * @return
     */
    @GetMapping("/asset/defaultModel")
    public String defaultModel() {
        return "/asset/defaultModel";
    }

    /**
     * 获取模板数据
     *
     * @return
     */
    @GetMapping("/asset/getModelData")
    @ResponseBody
    public Json getModelData() {
        List<AssetModel> tipsList = assetModelService.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
        List<AccountingTipsDto> dtos = accountingTipsMapper.toDTO(tipsList);
        return Json.succ("viewData", dtos).data("count", dtos.size());
    }

    /**
     * 表单模板配置保存
     *
     * @param assetModels
     * @return
     */
    @PostMapping("/asset/saveModelData")
    @ResponseBody
    public Json saveModelData(@RequestBody List<AssetModel> assetModels) {
        List<AssetModel> tipsList = assetModelService.save(assetModels);
        return Json.succ("viewData", tipsList).data("count", tipsList.size());
    }

    /**
     * 表单模板配置删除
     *
     * @param tips
     * @return
     */
    @PostMapping("/asset/delModelData")
    @ResponseBody
    public Json delModelData(@RequestBody List<AssetModel> tips) {

        assetModelService.del(tips);
        return Json.succ("deleteData").data("count", tips.size());
    }

    /**
     * 用户资产页面
     *
     * @return
     */
    @GetMapping("/asset/assetsView")
    public String assetsView() {
        return "/asset/assetsView";
    }


    /**
     * 获取用户资产数据
     *
     * @return
     */
    @GetMapping("/asset/getAssetsViewData")
    @ResponseBody
    public Json getAssetsViewData() {
        List<AssetView> accountingViews = assetViewService.findViewDataByUserName(SecurityUtils.getSubject().getPrincipal().toString());
        return Json.succ("viewData", accountingViews).data("count", accountingViews.size());
    }

    /**
     * 用户资产数据 新增修改页面
     *
     * @param dataId
     * @param modelMap
     * @return
     */
    @GetMapping("/asset/assetsForm")
    public String assetsForm(Long dataId, ModelMap modelMap) {
        if (dataId != null) {
            modelMap.addAttribute("dataId", dataId);
        }
        return "/asset/assetsForm";
    }

    /**
     * 保存用户资产数据
     *
     * @param assetMain
     * @return
     */
    @PostMapping("/asset/saveAssetsFormData")
    @ResponseBody
    public Json saveAssetsFormData(@RequestBody AssetMain assetMain) {
        assetMainService.save(assetMain);
        return Json.succ("saveDetail");
    }

    /**
     * 保存用户资产数据
     *
     * @param assetMain
     * @return
     */
    @PostMapping("/asset/delAssetsFormData")
    @ResponseBody
    public Json delAssetsFormData( AssetMain assetMain) {
        assetMainService.save(assetMain);
        return Json.succ("saveDetail");
    }

    /**
     * 获取用户模型数据
     *
     * @param id
     * @return
     */
    @GetMapping("/asset/getUserModelData")
    @ResponseBody
    public Json getUserModelData(Long id) {
        List<AssetModel> assetModels = assetModelService.findByUsername(SecurityUtils.getSubject().getPrincipal().toString());
        if (id == null) {
            List<AccountingTipsDto> dtos = accountingTipsMapper.toDTOIgnoreId(assetModels);
            AssetMain assetMain = new AssetMain();
            assetMain.setAssetDetail(accountingTipsMapper.toAssetDetail(dtos));
            return Json.succ("viewData", assetMain).data("count", assetMain.getAssetDetail().size());
        } else {
            AssetMain assetMain = assetMainService.findById(id);
            return Json.succ("viewData", assetMain).data("count", assetMain.getAssetDetail().size());
        }


    }

}
