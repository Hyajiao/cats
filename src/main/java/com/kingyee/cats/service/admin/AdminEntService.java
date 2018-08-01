package com.kingyee.cats.service.admin;

import com.kingyee.cats.common.security.AdminUserUtil;
import com.kingyee.cats.dao.admin.AdminEntDao;
import com.kingyee.cats.db.CatsProject;
import com.kingyee.cats.db.CatsSurveyProject;
import com.kingyee.cats.db.CmAdminUser;
import com.kingyee.common.model.POJOPageInfo;
import com.kingyee.common.model.SearchBean;
import com.kingyee.common.util.encrypt.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 药企管理
 *
 * @author nmy
 * 2018年05月16日
 */
@Service
public class AdminEntService {
	
    @Autowired
    private AdminEntDao dao;

	/**
	 * 获取用户对应的项目列表
	 * @return
	 */
	public POJOPageInfo getUserProjectList(SearchBean searchBean, POJOPageInfo pageInfo) {
		return dao.getUserProjectList(searchBean, pageInfo);
	}

	/**
	 * 项目明细
	 * @param cpId 项目表主键
	 * @return
	 */
	public CatsProject getProjectInfoById(Long cpId) {
		return dao.get(CatsProject.class,cpId);
	}


	/**
	 * 调研报告列表
	 * @return
	 */
	public List<CatsSurveyProject> getSurveyFinishProjectList(Long projectId) {
		return dao.getSurveyFinishProjectList(projectId);
	}

	/**
	 * 修改密码
	 * @return
	 */
	public boolean changePwd(String oldPwd,String newPwd) throws UnsupportedEncodingException {
		CmAdminUser db = dao.get(CmAdminUser.class,AdminUserUtil.getLoginUser().getId());
		if(EncryptUtil.getSHA256Value(oldPwd).equals(db.getAuPassword())){
			db.setAuPassword(EncryptUtil.getSHA256Value(newPwd));
			dao.update(db);
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 根据id获取调研项目信息
	 * @return
	 */
	public CatsSurveyProject getSurvyProjectBy(Long id) {
		return dao.get(CatsSurveyProject.class,id);
	}
}
