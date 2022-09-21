package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProductDao;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.rs.SysDictionaryDao;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.entity.ms.Project;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.product.ProductModel;
import com.yskc.ms.models.product.QueryProductModel;
import com.yskc.ms.service.ms.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public PageModel<List<ProductModel>> queryProduct(QueryProductModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("year");
            page.setDescs(desc);
        }
        return PageUtils.build(page, productDao.queryProduct(page,query,perm));
    }

    @Override
    public String addProduct(Integer id, ProductModel model) {
        Date dete = new Date();
        String productName = model.getProductName();
        String address = null;
        if(model.getAddressCodeArr()!=null){
            address = String.join(",", model.getAddressCodeArr());
            model.setAddress(address);
        }
        List<ProductModel> productModels = productDao.checkProduct(model.getId(), productName, model.getYear(), address);
        if(productModels!=null&&productModels.size()>0){
            if(model.getYear()!=null&&model.getAddressCodeArr()!=null){
                return "当前年份与所属地已存在该项目类型!请重新输入";
            }else if(model.getYear()!=null&&model.getAddressCodeArr()==null){
                return "当前年份已存在该项目类型!请重新输入";
            }else if(model.getYear()==null&&model.getAddressCodeArr()!=null){
                return "当前所属地已存在该项目类型!请重新输入";
            }else if(model.getYear()==null&&model.getAddressCodeArr()==null){
                return "已存在该项目类型!请重新输入";
            }
        }
        Product product = new Product();
        BeanUtils.copyProperties(model, product);
        product.setCreatorId(id);
        product.setLastUpdatorId(id);
        product.setCreateTime(dete);
        product.setLastUpdateTime(dete);
        productDao.insert(product);
        return "";
    }

    @Override
    public String editProduct(Integer id, ProductModel model) {
        String productName = model.getProductName();
        String address = null;
        if(model.getAddressCodeArr()!=null){
            address = String.join(",", model.getAddressCodeArr());
            model.setAddress(address);
        }
        List<ProductModel> productModels = productDao.checkProduct(model.getId(), productName, model.getYear(), address);
        if(productModels!=null&&productModels.size()>0){
            if(model.getYear()!=null&&model.getAddressCodeArr()!=null){
                return "当前年份与所属地已存在该项目类型!请重新输入";
            }else if(model.getYear()!=null&&model.getAddressCodeArr()==null){
                return "当前年份已存在该项目类型!请重新输入";
            }else if(model.getYear()==null&&model.getAddressCodeArr()!=null){
                return "当前所属地已存在该项目类型!请重新输入";
            }else if(model.getYear()==null&&model.getAddressCodeArr()==null){
                return "已存在该项目类型!请重新输入";
            }
        }
        Product product = new Product();
        BeanUtils.copyProperties(model, product);
        product.setLastUpdatorId(id);
        product.setLastUpdateTime(new Date());
        productDao.updateById(product);
        return "";
    }

    @Override
    public boolean deleteProduct(ProductModel model) throws OwnerException {
        List<Project> projects=projectDao.getListByProduct(model.getId());
        if(CollectionUtils.isEmpty(projects)) {
            return productDao.deleteById(model.getId()) > 0;
        }
        throw new OwnerException("已存在该类型的项目，不能删除");
    }

    @Override
    public List<ProductModel> productForSelect(Integer year, String addressCode, String productName) {

        return productDao.productForSelect(year,addressCode,productName);
    }

}
