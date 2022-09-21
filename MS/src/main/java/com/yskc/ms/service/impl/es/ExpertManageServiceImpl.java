package com.yskc.ms.service.impl.es;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.es.ExpertUserDao;
import com.yskc.ms.dao.es.UserExpertLogDao;
import com.yskc.ms.dao.es.UserFilesDao;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.entity.es.UserEntity;
import com.yskc.ms.entity.es.UserExpertLogEntity;
import com.yskc.ms.enums.UserStatusEnum;
import com.yskc.ms.models.CountModel;
import com.yskc.ms.models.newexpert.expert.*;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.ResetPasswordModel;
import com.yskc.ms.service.es.ExpertManageService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

import static com.yskc.common.enums.DataSourceEnum.ES;


/**
 * @DateTime: 2021/9/23 15:35
 * @Description:专家库用户管理逻辑处理层
 * @author: hsx
 */
@Service
public class ExpertManageServiceImpl implements ExpertManageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpertUserDao expertUserDao;
    @Autowired
    private UserFilesDao userFilesDao;
    @Autowired
    private UserExpertLogDao userExpertLogDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private UserDao userDao;

    @Override
    public PageModel<List<ExpertModel>> getExpertList(QueryExpertModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList( "id","lastUpdateTime"));
        }
        List<ExpertModel> expertList = expertUserDao.getExpertList(page, query);
        expertList.forEach(item->{
            Date birthday = item.getBirthday();
            if (null != birthday) {
                item.setAge(ExpertAuditModel.getAgeByBirth(birthday));
            }
        });
        return PageUtils.build(page,expertList);
    }

    @Override
    public Boolean ChangeUserStatus(Integer id, Integer status) throws OwnerException{
        UserEntity user = expertUserDao.selectById(id);
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }
        return expertUserDao.ChangeUserStatus(id, status == 0 ? UserStatusEnum.DISABLED.getType() : UserStatusEnum.ENABLED.getType(),new Date())>0;
    }

    @Override
    public Boolean resetPassword(ResetPasswordModel rpm)  throws OwnerException{
        UserEntity user = expertUserDao.selectById(rpm.getId());
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }
        String pwd = Md5Util.encrypt(MessageFormat.format("{0}{1}", rpm.getUserName(), rpm.getPassword()));
        return expertUserDao.resetPassword(rpm.getId(), pwd,new Date()) > 0;
    }

    @Override
    public Boolean updateUser(ExpertModel model) throws OwnerException{
        UserEntity user = expertUserDao.selectById(model.getId());
        if (user == null) {
            throw new OwnerException(ErrorEnum.NOT_USER);
        }
        String idNo = expertUserDao.verifyUniqueIdNo(model.getIdNo(), model.getIdType(), model.getId());
        if (null != idNo) {
            throw new OwnerException("证件号码重复，请检查后重新输入");
        }
        BeanUtils.copyProperties(model,user);
        user.setLastUpdateTime(new Date());
        return expertUserDao.updateUser(user)>0;
    }

    @Override
    public PageModel<List<ExpertAuditModel>> getExpertAuditList(QueryExpertModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList( "ue.status"));
            page.setDescs(CollUtil.newArrayList("ue.lastUpdateTime"));
        }
        List<ExpertAuditModel> expertAuditList = expertUserDao.getExpertAuditList(page, query);
        if (!CollectionUtils.isEmpty(expertAuditList)) {
            expertAuditList.forEach(item->{
                if (null != item.getBirthday()) {
                    item.setAge(ExpertAuditModel.getAgeByBirth(item.getBirthday()));
                }
                if (StringUtils.hasLength(item.getTags())){
                    List<String> tagsArr = new ArrayList<>(Arrays.asList(item.getTags().split(",")));
                    tagsArr.remove(0);
                    item.setTagsArr(tagsArr);
                }else {
                    item.setTagsArr(new ArrayList<>());
                }
            });
        }
        return PageUtils.build(page, expertAuditList);
    }

    @Override
    public Boolean updateExpertAuditList(ExpertTagsModel model) {
        if (!CollectionUtils.isEmpty(model.getList())){
            return expertUserDao.updateExpertTags(model.getList());
        }
        return false;
    }

    @Override
    public Boolean expertAudit(UserExpertLogModel model, Integer id) throws OwnerException{
        model.setCreateTime(new Date());
        model.setMsCreatorId(id);
        List<Integer> userStatus = expertUserDao.getUserStatus(model.getUserExpertIds());
        if (!userStatus.isEmpty() && (userStatus.contains(FlowInstanceStatusEnum.DONE.getStatus()) || userStatus.contains(FlowInstanceStatusEnum.REJECT.getStatus()))) {
            throw new OwnerException("用户已审核完毕，请勿重复操作！");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(ES);
        try {
            userExpertLogDao.addAuditLog(model);
            expertUserDao.editExpertStatus(model);
            TransactionUtils.commit(ES,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(ES,transactionStatus);
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    @Override
    public List<UserExpertLogModel> getUserAuditLog(Integer id) {
        List<UserExpertLogEntity> userAuditLog = userExpertLogDao.getUserAuditLog(id);
        if (!userAuditLog.isEmpty()) {
            Set<Integer> list = new HashSet<>();
            List<UserExpertLogModel> models = new ArrayList<>();
            userAuditLog.forEach(item -> {
                list.add(item.getMsCreatorId());
            });
            List<MiniUserModel> users = userDao.getUsers(list);
            Map<Integer, String> map = new HashMap<>();
            if (!users.isEmpty()) {
                users.forEach(item -> {
                    map.put(item.getId(), item.getRealName());
                });
            }
            userAuditLog.forEach(item -> {
                UserExpertLogModel model = new UserExpertLogModel();
                BeanUtils.copyProperties(item, model);
                model.setMsCreatorName(map.get(model.getMsCreatorId()));
                models.add(model);
            });
            return models;
        } else {
            return null;
        }
    }

    @Override
    public Map<String,Integer> getStatusTotal() {
        List<CountModel> toAuditTotal = expertUserDao.getStatusTotal();
        Integer sum = 0;
        Map<String,Integer> map = new HashMap();
        for (CountModel countModel : toAuditTotal) {
            Integer count = countModel.getCount();
            sum += count;
            map.put(countModel.getValue(), count);
        }
        //统计全部
        map.put("-1", sum);
        return map;
    }

    @Override
    public UserEnterModel getUserEnterData(Integer id) {
        UserEnterModel model = expertUserDao.getUserEnterData(id);
        if (StringUtils.hasLength(model.getTags())){
            ArrayList<String> list = new ArrayList<>(Arrays.asList(model.getTags().split(",")));
            list.remove(0);
            model.setTagsArr(list);
        }
        model.setResearchResult((String) CommonUtils.strSpecialTransfer(model.getResearchResult(),false));
        return model;
    }
}
