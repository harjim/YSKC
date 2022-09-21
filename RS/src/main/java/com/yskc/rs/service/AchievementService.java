package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.achievement.AchievementFileModel;
import com.yskc.rs.models.achievement.AchievementModel;
import com.yskc.rs.models.achievement.QueryAchievementModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:36
 * @Description: 成果业务接口层
 */
public interface AchievementService {
    /**
     * 获取成果管理
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<AchievementModel>> getList(QueryAchievementModel query, Integer companyId);

    /**
     * 添加成果
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean add(AchievementModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 编辑研发成果
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean edit(AchievementModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 删除研发成果
     *
     * @param info
     * @param ids
     * @return
     * @throws OwnerException
     */
    boolean del(List<Integer> ids, UserInfo info) throws OwnerException;

    /**
     * 添加成果文件
     *
     * @param info
     * @param achievementFile
     * @param file
     * @return
     * @throws OwnerException
     */
    AchievementFileModel addFile(UserInfo info, AchievementFileModel achievementFile, MultipartFile file) throws OwnerException;

    /**
     * 获取成果文件
     *
     * @param achievementId
     * @return
     */
    List<AchievementFileModel> getFiles(Integer achievementId);

    /**
     * 删除成果文件
     *
     * @param achievementId
     * @param id
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean deleteFile(Integer achievementId, Integer id, UserInfo info) throws OwnerException;

    boolean updateSeq(Integer id1,Integer id2);

    boolean updateFile(UserInfo info,AchievementFileModel achievementFile,MultipartFile file) throws OwnerException;
}
