package com.yskc.ms.controller.ms;

import cn.hutool.core.util.ZipUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.BatchZipModel;
import com.yskc.ms.models.patent.*;
import com.yskc.ms.models.patentPlan.SetPatentEngineerModel;
import com.yskc.ms.service.ms.PatentBuyingDemandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:24
 * @Description:专利需求购买接口
 */
@Api(tags = "专利需求购买接口", value = "专利需求购买接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentBuying")
public class PatentBuyingController extends BaseController {
    @Autowired
    private PatentBuyingDemandService patentBuyingDemandService;
    @Autowired
    private MsConfig msConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取需求列表", notes = "获取需求列表")
    public PageModel<List<PatentBuyingDemandModel>> getList(QueryDemandModel query) throws OwnerException {
        return patentBuyingDemandService.getList(query, getDataPerm());
    }

    @GetMapping("/getBuyDemandList")
    @PermissionRequired(perms = "patent:buyingList:search")
    @ApiOperation(value = "获取购买需求列表", notes = "获取购买需求列表")
    public PageModel<List<PatentBuyingDemandModel>> getBuyDemandList(QueryDemandModel query) throws OwnerException {
        return patentBuyingDemandService.getBuyDemandList(query, getDataPerm());
    }

    @PostMapping("/saveDemand")
    @SystemLog(description = "保存需求", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:demandList:addDemand,patent:demandList:editDemand")
    @ApiOperation(value = "保存需求", notes = "保存需求")
    public Integer saveDemand(@RequestBody @Validated PatentBuyingDemandModel model) throws OwnerException {
        return patentBuyingDemandService.saveDemand(model, getUserInfo());
    }

    @PostMapping("/delDemand")
    @PermissionRequired(perms = "patent:demandList:delDemand")
    @SystemLog(description = "删除需求", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除需求", notes = "删除需求")
    public Boolean delDemand(@RequestBody @Validated List<PatentBuyingDemandModel> models) throws OwnerException {
        return patentBuyingDemandService.delDemand(models, getUserInfo());
    }

    @PostMapping("/submitDemand")
    @PermissionRequired(perms = "patent:buyingList:submit,patent:buyingList:confirm")
    @SystemLog(description = "提交/确认需求", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "提交/确认需求", notes = "提交/确认需求")
    public Boolean submitDemand(@RequestBody @Validated PatentBuyingDemandModel model) throws OwnerException {
        return patentBuyingDemandService.submitDemand(model, getUserInfo());
    }

    @GetMapping("/getPatentSea")
    @PermissionRequired(perms = "patent:patentData:search")
    @ApiOperation(value = "获取专利公海", notes = "获取专利公海")
    public PageModel<List<PatentSeaModel>> getPatentSea(QueryPatentModel query) throws OwnerException {
        return patentBuyingDemandService.getPatentSea(query, getDataPerm());
    }

    @GetMapping("/limitPatentNo")
    //@PermissionRequired(perms = "patent:patentData:search")
    @ApiOperation(value = "判断专利号唯一", notes = "判断专利号唯一")
    public Boolean limitPatentNo(PatentSeaModel model) throws OwnerException {
        return patentBuyingDemandService.limitPatentNo(model);
    }

    @PostMapping("/savePatentSea")
    @SystemLog(description = "专利公海添加/更新专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:addPatent,patent:buyingList:editPatent")
    @ApiOperation(value = "专利公海添加/更新专利", notes = "专利公海添加/更新专利")
    public Boolean savePatentSea(@RequestBody @Validated PatentSeaModel model) throws OwnerException {
        return patentBuyingDemandService.savePatentSea(model, getUserInfo());
    }

    @PostMapping("/relatedDemand")
    @PermissionRequired(perms = "patent:buyingList:selected")
    @SystemLog(description = "专利公海关联需求", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "专利公海关联需求", notes = "专利公海关联需求")
    public Boolean relatedDemand(@RequestBody @Validated List<PatentSeaModel> models) throws OwnerException {
        return patentBuyingDemandService.relatedDemand(models, getUserInfo());
    }

    @PostMapping("/delPatentSea")
    @SystemLog(description = "删除专利公海专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:delPatent")
    @ApiOperation(value = "删除专利公海专利", notes = "删除专利公海专利")
    public Boolean delPatentSea(@RequestBody @Validated List<Integer> ids) throws OwnerException {
        return patentBuyingDemandService.delPatentSea(ids, getUserInfo());
    }

    @GetMapping("/download")
    @SystemLog(description = "下载文件")
    @ApiOperation(value = "下载文件", notes = "下载文件", response = boolean.class)
    public void download(String filePath) throws OwnerException {
        Path path = Paths.get(msConfig.getUploadConfig().getImportPath(), filePath);
        String fullPath = path.toString();
        String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 14);
        super.download(fullPath, fileName);
    }

    @GetMapping("/getBuyingList")
    //@PermissionRequired(perms = "patent:patentData:search")
    @ApiOperation(value = "获取专利预购列表", notes = "获取专利预购列表")
    public PageModel<List<PatentBuyingModel>> getBuyingList(QueryPatentModel query) throws OwnerException {
        return patentBuyingDemandService.getBuyingList(query, getDataPerm());
    }

    @PostMapping("/buyPatent")
    @SystemLog(description = "选购专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:chooseBuy,patent:buyingList:buy")
    @ApiOperation(value = "选购专利", notes = "选购专利")
    public Boolean buyPatent(@RequestBody @Validated PatentBuyingModel model) throws OwnerException {
        return patentBuyingDemandService.buyPatent(model, getUserInfo());
    }

    /**
     * 购买状态0 删除购买记录，取消公海需求关联 1 状态改为0
     *
     * @param ids 购买专利列表ids
     * @return
     * @throws OwnerException
     */
    @PostMapping("/buyBack")
    @SystemLog(description = "专利返销", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:buyBack")
    @ApiOperation(value = "专利返销", notes = "专利返销")
    public Boolean buyBack(@RequestBody @Validated List<Integer> ids) throws OwnerException {
        return patentBuyingDemandService.buyBack(ids, getUserInfo());
    }

    @PostMapping("/completePurchase")
    @SystemLog(description = "完成购买专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:completed")
    @ApiOperation(value = "完成购买专利", notes = "完成购买专利")
    public Boolean completePurchase(@RequestBody @Validated PatentBuyingModel model) throws OwnerException {
        return patentBuyingDemandService.completePurchase(model, getUserInfo());
    }


    @PostMapping("/updateSellFile")
    @SystemLog(description = "更新转让资料", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:buyingList:chooseBuy,patent:buyingList:buy")
    @ApiOperation(value = "更新转让资料", notes = "更新转让资料")
    public Boolean updateSellFile(@RequestBody @Validated PatentBuyingModel model) throws OwnerException {
        return patentBuyingDemandService.updateSellFile(model, getUserInfo());
    }


    @GetMapping("/exportCanBuy")
    @SystemLog(description = "导出可购列表")
    @PermissionRequired(perms = "patent:buyingList:exportCanBuy")
    @ApiOperation(value = "导出可购列表", notes = "导出可购列表")
    public void exportCanBuy(QueryPatentModel query) throws OwnerException, IOException {
        BatchZipModel batchZip = patentBuyingDemandService.getExportStream(query);
        ZipUtil.zip(outGeneralFile(batchZip.getFileName()),batchZip.getZipFiles().toArray(new String[]{}),batchZip.getInputStreams().toArray(new InputStream[]{}));
    }


    @PostMapping("/setEngineer")
    @PermissionRequired(perms = "patent:demandList:engineer")
    @SystemLog(description = "分配专利需求工程师", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "分配专利需求工程师", notes = "分配专利需求工程师")
    public Boolean setEngineer(@RequestBody SetPatentEngineerModel setEngineer)throws OwnerException {
        return patentBuyingDemandService.setEngineer(setEngineer,getUserInfo().getId());
    }
}
