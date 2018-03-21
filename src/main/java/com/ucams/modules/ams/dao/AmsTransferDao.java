/**
 * Copyright &copy; Ucams All rights reserved.
 */
package com.ucams.modules.ams.dao;

import java.util.List;

import com.ucams.common.persistence.CrudDao;
import com.ucams.common.persistence.annotation.MyBatisDao;
import com.ucams.modules.ams.entity.AmsTransfer;
import com.ucams.modules.ams.entity.AmsTransferArchives;

/**
 * 移交DAO接口
 * @author zkx
 * @version 2017-07-25
 */
@MyBatisDao
public interface AmsTransferDao extends CrudDao<AmsTransfer> {
	public int updateTransfer(AmsTransfer amsTransfer);
	public int updateDelFlag(AmsTransfer amsTransfer);
	public int insertAmsTransferArchivesList(AmsTransfer amsTransfer);
	public int deleteAmsTransferArchives(AmsTransfer amsTransfer);
	public List<AmsTransferArchives> getList(AmsTransfer amsTransfer);
	public List<AmsTransferArchives> getIdsList(AmsTransfer amsTransfer);
	public int insertAmsTransferArchives(AmsTransferArchives amsTransferArchives);
}