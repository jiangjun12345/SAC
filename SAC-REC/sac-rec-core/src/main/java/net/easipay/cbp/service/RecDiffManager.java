/**
 * 
 */
package net.easipay.cbp.service;

import java.util.List;
import java.util.Map;
import net.easipay.cbp.form.AccountNoticeForm;
import net.easipay.cbp.model.SacRecDifferences;
import net.easipay.cbp.service.base.GenericManager;

/**
 * @Description: TODO(用一句话描述该文件做什么)
 * @author sun (作者英文名称)
 * @date 2015-4-2 下午12:29:24
 * @version V1.0
 * @jdk v1.6
 * @tomcat v7.0
 */

public interface RecDiffManager extends GenericManager<SacRecDifferences, Long> {

  /**
   * 根据条件查询对账差错,只存在一条记录的情况
   * 
   * @param param
   * @return
   */
  public SacRecDifferences getRecDiff(Map<String, Object> param);

  /**
   * 根据条件查询对账差错,可能存在多条记录的情况
   * 
   * @param param
   * @return
   */
  public List<SacRecDifferences> getRecDiffList(Map<String, Object> param);

  /**
   * 根据条件查询对账差错长款需要增补交易,可能存在多条记录的情况
   * 
   * @param param
   * @return
   */
  public List<SacRecDifferences> selectDifferencesForSupplement(Map<String, Object> param);

  /**
   * 插入对账差错数据
   * 
   * @param param
   * @return
   */
  public void insertRecDiff(SacRecDifferences sacRecDifferences);

  /**
   * 处理内部对账差错数据
   * 
   * @param param
   * @return
   */
  public void processInnerRecDiff(List<SacRecDifferences> recDiffList) throws Exception;

  /**
   * 处理需要补单的对账差错数据
   * 
   * @param param
   * @return
   */
  public void processReplenishmentsRecDiff(List<SacRecDifferences> recDiffList) throws Exception;

  /**
   * 处理内部对账完成自动增补遗漏差错交易任务
   * 
   * @param param
   * @return
   */
  public void processAutoSupplementTrxRecDiff(SacRecDifferences sacRecDifferences) throws Exception;

  /**
   * 更新内部对账完成自动增补遗漏差错交易状态任务
   * 
   * @param param
   * @return
   */
  public void processUpdateStateAutoSupplementTrxRecDiff(SacRecDifferences sacRecDifferences) throws Exception;

  /**
   * 处理渠道对账差错数据
   * 
   * @param param
   * @return
   */
  public void processChannelRecDiff(AccountNoticeForm noticeform) throws Exception;

}
