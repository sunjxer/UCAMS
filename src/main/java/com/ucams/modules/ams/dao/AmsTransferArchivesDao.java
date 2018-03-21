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
 * 移交案卷DAO接口
 * @author zkx
 * @version 2017-07-25
 */
@MyBatisDao
public interface AmsTransferArchivesDao extends CrudDao<AmsTransferArchives> {
   public List<AmsTransferArchives> findTranList(AmsTransfer amsTransfer);
}