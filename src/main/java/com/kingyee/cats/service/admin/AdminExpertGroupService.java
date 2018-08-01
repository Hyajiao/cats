package com.kingyee.cats.service.admin;

import com.google.gson.JsonElement;
import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminExpertGroupDao;
import com.kingyee.cats.db.CatsExpertGroup;
import com.kingyee.cats.db.CatsExpertGroupDetail;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.common.IBaseService;
import com.kingyee.common.gson.JsonWrapper;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专家组管理
 * 
 * @author ph
 */
@Service
public class AdminExpertGroupService implements IBaseService {

    private final static Logger logger = LoggerFactory
            .getLogger(AdminExpertGroupService.class);
	
    @Autowired
    private AdminExpertGroupDao dao;

    /**
     * 专家组列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsExpertGroup> list(SearchBean searchBean, POJOPageInfo<CatsExpertGroup> pageInfo){
        return dao.list(searchBean, pageInfo);
    }

    /**
     * 取得所有有效的专家组列表
     * @return
     */
    public List<CatsExpertGroup> getAllExpertGroupList(){
        return dao.getAllExpertGroupList();
    }

    /**
     * 保存专家组
     * @param expertGroup
     * @return
     */
    public Long save(CatsExpertGroup expertGroup) {
        expertGroup.setCegCreateUserId(AdminUserUtil.getUserId());
        expertGroup.setCegCreateUserName(AdminUserUtil.getShowName());
        expertGroup.setCegCreateTime(System.currentTimeMillis());
        expertGroup.setCegIsValid(1);
        return dao.save(expertGroup);
    }

    /**
     * 根据id获取专家组信息
     * @param id
     * @return
     */
    public CatsExpertGroup getById(Long id){
        return dao.get(CatsExpertGroup.class, id);
    }

    /**
     * 更新专家组信息
     * @param expertGroup
     */
    public void edit(CatsExpertGroup expertGroup) {
        CatsExpertGroup db = getById(expertGroup.getCegId());
        db.setCegExpertGroupName(expertGroup.getCegExpertGroupName());
        db.setCegExpertNum(expertGroup.getCegExpertNum());
        db.setCegMemo(expertGroup.getCegMemo());
        db.setCegUpdateUserId(AdminUserUtil.getUserId());
        db.setCegUpdateUserName(AdminUserUtil.getShowName());
        db.setCegUpdateTime(System.currentTimeMillis());
        dao.update(db);
    }


    /**
     * 更新信息
     * @param table
     */
    public void update(Object table) {
        dao.update(table);
    }

    /**
     * 专家组列表
     * @param searchBean
     * @param pageInfo
     * @return
     */
    public POJOPageInfo<CatsExpertGroupDetail> expertList(SearchBean searchBean, POJOPageInfo<CatsExpertGroupDetail> pageInfo){
        return dao.expertList(searchBean, pageInfo);
    }

    /**
     * 保存专家
     * @param expertGroupDetail
     * @return
     */
    public Long save(CatsExpertGroupDetail expertGroupDetail) {
        expertGroupDetail.setCegdCreateUserId(AdminUserUtil.getUserId());
        expertGroupDetail.setCegdCreateUserName(AdminUserUtil.getShowName());
        expertGroupDetail.setCegdCreateTime(System.currentTimeMillis());
        return dao.save(expertGroupDetail);
    }

    /**
     * 根据id获取专家组信息
     * @param id
     * @return
     */
    public CatsExpertGroupDetail getExpertById(Long id){
        return dao.get(CatsExpertGroupDetail.class, id);
    }

    /**
     * 更新专家组信息
     * @param expertGroupDetail
     */
    public void edit(CatsExpertGroupDetail expertGroupDetail) {
        CatsExpertGroupDetail db = getExpertById(expertGroupDetail.getCegdId());
        BeanUtils.copyProperties(expertGroupDetail, db, new String[] {"cegdId", "cegdCreateTime", "cegdCreateUserId", "cegdCreateUserName"});
        db.setCegdUpdateUserId(AdminUserUtil.getUserId());
        db.setCegdUpdateUserName(AdminUserUtil.getShowName());
        db.setCegdUpdateTime(System.currentTimeMillis());
        dao.update(db);
    }


    /**
     * 删除专家
     * @param
     */
    public Long del(Long id) {
        CatsExpertGroupDetail expertGroupdetail = this.getExpertById(id);
        dao.del(expertGroupdetail);
        return expertGroupdetail.getCegdCegId();
    }

    /**
     * 批量保存专家
     * @return
     */
    public JsonElement saveExpertBatch(List<CatsExpertGroupDetail> expertList){
        int duplicateNum = 0;
        String medliveIds = "";
        for (CatsExpertGroupDetail expert: expertList) {
            if(expert.getCegdMedliveId() != null){
                // 跳过已经存在的医脉通id专家信息
                CatsExpertGroupDetail dbExpert = dao.getExpertByMedliveId(expert.getCegdMedliveId(), expert.getCegdCegId());
                if(dbExpert != null){
                    duplicateNum++;
                    medliveIds = medliveIds + expert.getCegdMedliveId() + ",";
                    continue;
                }
            }
            dao.save(expert);

        }

        if(duplicateNum == 0){
            StringBuffer msgSb = new StringBuffer();
            msgSb.append("成功导入");
            msgSb.append(expertList.size());
            msgSb.append("条记录");
            return JsonWrapper.newSuccessInstance(msgSb.toString());
        }else{
            StringBuffer msgSb = new StringBuffer();
            msgSb.append("成功导入");
            msgSb.append(expertList.size() - duplicateNum);
            msgSb.append("条，未导入（重复）数据");
            msgSb.append(duplicateNum);
            msgSb.append("条。重复的医脉通id为：");
            msgSb.append(medliveIds.substring(0, medliveIds.length() - 1));
            return JsonWrapper.newSuccessInstance(msgSb.toString());
        }


    }
}
