/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucams.common.service.TreeService;
import com.ucams.common.utils.StringUtils;
import com.ucams.modules.ams.entity.AmsGenre;
import com.ucams.modules.ams.dao.AmsGenreDao;

/**
 * 归档一览表类别管理Service
 * @author sunjx
 * @version 2017-06-12
 */
@Service
@Transactional(readOnly = true)
public class AmsGenreService extends TreeService<AmsGenreDao, AmsGenre> {
	
	@Autowired
	private AmsGenreDao amsGenreDao;
	
	public AmsGenre get(String id) {
		return super.get(id);
	}
	
	public List<AmsGenre> findList(AmsGenre amsGenre) {
		if (StringUtils.isNotBlank(amsGenre.getParentIds())){
			amsGenre.setParentIds(amsGenre.getParentIds().replace(" ", ""));
		}
		return super.findList(amsGenre);
	}
	
	@Transactional(readOnly = false)
	public void save(AmsGenre amsGenre) {
		super.save(amsGenre);
	}
	
	@Transactional(readOnly = false)
	public void delete(AmsGenre amsGenre) {
		super.delete(amsGenre);
	}
	
	@Transactional(readOnly =true)
	public List<AmsGenre> findByCode(String code){
		return amsGenreDao.findByCode(code);
	}
}