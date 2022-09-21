package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProductDao;
import com.yskc.rs.dao.ProductYearDao;
import com.yskc.rs.entity.ProductEntity;
import com.yskc.rs.entity.ProductYearEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.product.ProductModel;
import com.yskc.rs.models.product.ProductYearModel;
import com.yskc.rs.models.product.QueryPcodeModel;
import com.yskc.rs.models.product.QueryProductModel;
import com.yskc.rs.service.ProductService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: rs
 * @description: 企业产品
 * @author: wjy
 * @create: 2022-06-09 09:13
 **/
@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductYearDao productYearDao;

    @Override
    public PageResult getList(QueryProductModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("creationDate");
            page.setDescs(descs);
        }
        List<ProductModel> list = productDao.getProduct(page, query, companyId);
        return PageUtils.buildPageResult(page, list, productDao.getOutputValues(companyId, query));
    }

    @Override
    public Boolean checkPcode(QueryPcodeModel query, Integer companyId) throws OwnerException {
        if (productDao.checkPcode(query, companyId).size() > 0) {
            throw new OwnerException("已存在“"+query.getPcode()+"”,请重新输入！");
        }
        return true;
    }

    @Override
    public Boolean addProduct(ProductModel model, Integer companyId, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        if (model.getCreationDate().after(date)) {
            throw new OwnerException("创建时间错误！");
        }
        ProductEntity entity = new ProductEntity();
        entity.setCompanyId(companyId);
        entity.setPname(model.getPname());
        entity.setPcode(model.getPcode());
        entity.setModel(model.getModel());
        entity.setCreationDate(model.getCreationDate());
        entity.setUnit(model.getUnit());
        entity.setOutputValue(new BigDecimal(0));
        entity.setOutput(new BigDecimal(0));
        entity.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
        return productDao.insert(entity) > 0;
    }

    @Override
    public Boolean updateProduct(ProductModel model, UserInfo userInfo) {
        ProductEntity entity = new ProductEntity();
        entity.setId(model.getId());
        entity.setPname(model.getPname());
        entity.setPcode(model.getPcode());
        entity.setModel(model.getModel());
        entity.setCreationDate(model.getCreationDate());
        entity.setUnit(model.getUnit());
        entity.setCompanyId(model.getCompanyId());
        entity.setParameter(model.getParameter());
        entity.setFeatures(model.getFeatures());
        entity.setMainRaw(model.getMainRaw());
        entity.setComparison(model.getComparison());
        entity.update(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        return productDao.updateById(entity) > 0;
    }

    @Override
    public Boolean deleteProduct(Integer id, Integer companyId) throws OwnerException {
        if (!CollectionUtils.isEmpty(productYearDao.getYearList(id, companyId))) {
            throw new OwnerException("该产品有产值数据，不能删除！");
        }
        return productDao.deleteById(id) > 0;
    }

    @Override
    public List<ProductYearModel> getYearList(Integer productId, Integer companyId) {
        if (null != productId && productId != 0) {
            return productYearDao.getYearList(productId, companyId);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean updateProductYear(List<ProductYearModel> models, UserInfo userInfo, Integer productId) throws OwnerException {
        List<ProductYearEntity> list = new ArrayList<>();
        BigDecimal output = new BigDecimal(0);
        BigDecimal outputValue = new BigDecimal(0);
        ProductEntity productEntity = productDao.selectById(productId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(productEntity.getCreationDate());

        if (!CollectionUtils.isEmpty(models)) {
            for (ProductYearModel model : models) {
                if (model.getYear() < calendar.get(Calendar.YEAR) || model.getYear() > Calendar.getInstance().get(Calendar.YEAR)) {
                    throw new OwnerException("请规范添加年份！");
                }
                if (model.getOutput().compareTo(BigDecimal.ZERO) < 0 || model.getOutputValue().compareTo(BigDecimal.ZERO) < 0) {
                    throw new OwnerException("产量/产值不可为负！");
                }
                ProductYearEntity entity = new ProductYearEntity();
                entity.setProductId(model.getProductId());
                entity.setYear(model.getYear());
                entity.setOutput(model.getOutput());
                entity.setOutputValue(model.getOutputValue());
                entity.setCompanyId(userInfo.getCompanyId());
                entity.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
                output = output.add(model.getOutput());
                outputValue = outputValue.add(model.getOutputValue());
                list.add(entity);
            }
        }
        productEntity.setOutput(output);
        productEntity.setOutputValue(outputValue);
        productEntity.update(userInfo.getUserId(), userInfo.getMsUserId(), new Date());

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            Boolean delete;
            Integer insertOrUpdate = 0;
            if (CollectionUtils.isEmpty(models)) {
                delete = productYearDao.deleteYear(productId, userInfo.getCompanyId(), null);
            } else {
                delete = productYearDao.deleteYear(productEntity.getId(), userInfo.getCompanyId(), list);
                insertOrUpdate = productYearDao.insertOrUpdate(list, userInfo.getUserId(), userInfo.getMsUserId());
            }
            Integer updateById = productDao.updateById(productEntity);
            TransactionUtils.commit(transactionStatus);
            if (insertOrUpdate > 0 && updateById > 0 && delete) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("编辑失败！");
        }
        return false;
    }
}
