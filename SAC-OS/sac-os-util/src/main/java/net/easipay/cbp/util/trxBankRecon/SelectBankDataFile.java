package net.easipay.cbp.util.trxBankRecon;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.easipay.cbp.model.SacReceiveBankRecon;
import net.easipay.cbp.util.trxBankRecon.AbcBankDataTxt;
import net.easipay.cbp.util.trxBankRecon.BcBankDataExcel;
import net.easipay.cbp.util.trxBankRecon.BocbankDataTxt;
import net.easipay.cbp.util.trxBankRecon.BosBankDataTxt;
import net.easipay.cbp.util.trxBankRecon.CiticBankDataTxt;
import net.easipay.cbp.util.trxBankRecon.CmbBankDataExcel;
import net.easipay.cbp.util.trxBankRecon.IcbcBankDataTxt;
import net.easipay.cbp.util.trxBankRecon.SpdbBankDataTxt;

/**
 * 功能描述：各家银行对账文件数据转换的入口
 * @author zfy
 * crate：2015-07-24
 */

public class SelectBankDataFile {
	
	public static List<SacReceiveBankRecon> selectBankData(String bankNodeCode,
			InputStream inputStream, String busiType, String fileName) {
		// 解析file,组装出发送给账务系统的List数据
		List<SacReceiveBankRecon> list = null;

		// 中国银行对账文件txt解析
		if ("BOC0000".equals(bankNodeCode)) {
			list = BocbankDataTxt.readTxtFile(inputStream);
		}

		// 农业银行对账文件txt解析
		if ("ABC0000".equals(bankNodeCode)) {
			list = AbcBankDataTxt.readTxtFile(inputStream);
		}

		// 工商银行对账文件txt解析
		if ("ICBC000".equals(bankNodeCode)) {
			list = IcbcBankDataTxt.readTxtFile(inputStream);
		}

		// 建设银行对账文件txt解析
		if ("CCBEB00".equals(bankNodeCode)) {
			list = CcbebBankDataTxt.readTxtFile(inputStream);
		}

		// 交通银行对账文件Excel解析
		if ("BC00000".equals(bankNodeCode)) {
			try {
				list = BcBankDataExcel.readExcel(inputStream, fileName);
			} catch (IOException e) {
				throw new RuntimeException("读取文件内容出错");
				//e.printStackTrace();
			}
		}

		// 浦发银行对账文件txt解析
		if ("SPDB000".equals(bankNodeCode)) {
			list = SpdbBankDataTxt.readTxtFile(inputStream);
		}

		// 招商银行对账文件Ecel解析
		if ("CMB0000".equals(bankNodeCode)) {
			try {
				list = CmbBankDataExcel.readExcel(inputStream, fileName);
			} catch (IOException e) {
				//e.printStackTrace();
				throw new RuntimeException("读取文件内容出错");
			}
		}

		// 上海银行网银和快捷对账文件txt解析
		if ("BOS0000".equals(bankNodeCode)) {
			list = BosBankDataTxt.readTxtFile(inputStream);
		}

		// 中信银行对账文件txt解析
		if ("CITIC00".equals(bankNodeCode)) {
			list = CiticBankDataTxt.readTxtFile(inputStream, busiType);
		}

		// 邮政储蓄银行对账文件Excel解析
		if ("PSBC000".equals(bankNodeCode)) {
			try {
				list = PsbcBankDataExcel.readExcel(inputStream, fileName);
			} catch (IOException e) {
				//e.printStackTrace();
				throw new RuntimeException("读取文件内容出错");
			}
		}

		// 银联对账文件Txt解析
		if ("UPOP000".equals(bankNodeCode)) {
			list = UnionBankDataTxt.readTxtFile(inputStream);
		}
		
		// 明生银行对账文件解析
		if("CMBC000".equals(bankNodeCode)){
			try {
				list = CmbcBankDataExcel.readExcel(inputStream, fileName);
			} catch (IOException e) {
				//e.printStackTrace();
				throw new RuntimeException("读取文件内容出错");
			}
		}

		return list;
	}
}
