package net.easipay.cbp.util.trxBankRecon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.easipay.cbp.model.SacReceiveBankRecon;

/**
 * 功能：中信银行对账txt格式转换
 * @author zfy
 * create: 2015-07-24
 */
public class CiticBankDataTxt {
	/**
	 * 功能：Java读取txt文件的内容 步骤：
	 * 1：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 2：读取到输入流后，需要读取生成字节流 
	 * 3：一行一行的输出。readline()。 
	 * 备注：需要考虑的是异常情况
	 * @param filePath
	 */
	private static Logger logger = LoggerFactory.getLogger(CiticBankDataTxt.class);
	
	public static List<SacReceiveBankRecon> readTxtFile(InputStream inputStream, String busiType) {
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		List<SacReceiveBankRecon> list1 = new ArrayList<SacReceiveBankRecon>();
		int count = 0; //交易总笔数
		String encoding = "GBK";
		// 正向交易文件解析
		if ("1".equals(busiType)) {
			try {
				InputStreamReader read = new InputStreamReader(inputStream,encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				for (int i = 0; (lineTxt = bufferedReader.readLine()) != null; i++) {
					// 中信银行对账文件.txt 的body内容从第7行开始
					if (i >= 6) {
						count ++;
						SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
						String lineTxtStr[] = lineTxt.split("\\|");
						SacBankRecon.setBankSerialNo(lineTxtStr[0]);
						SacBankRecon.setPayconType("2");
						SacBankRecon.setCurrencyType("CNY");
						SacBankRecon.setPayAmount(lineTxtStr[5]);
						SacBankRecon.setTrxTime(format1(format(lineTxtStr[2])));
						SacBankRecon.setRecOper("000001");
						SacBankRecon.setChnNo("CITIC00");
						// 正向交易值为1
						SacBankRecon.setBusiType("1");
						SacBankRecon.setRecStartDate(format1(format(lineTxtStr[2]).substring(0, 8)+"000000"));
						SacBankRecon.setRecEndDate(format1(format(lineTxtStr[2]).substring(0, 8)+"000000"));
						SacBankRecon.setRecCount(new Long(count));
						SacBankRecon.setTrxCode("3302"); //新增交易码
						list.add(SacBankRecon);
					}else{
						//当天无对账数据，插入一条流水号、交易金额、记录数为0的默认值
						SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
						SacBankRecon.setBankSerialNo("0");
						SacBankRecon.setPayconType("2");
						SacBankRecon.setCurrencyType("CNY");
						SacBankRecon.setPayAmount("0");
	                    Date date = new Date(new Date().getTime()-24*60*60*1000);
						SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
						String strDate = f.format(date);
	                    SacBankRecon.setTrxTime(f.parse(strDate));
						SacBankRecon.setRecOper("000001");
						SacBankRecon.setChnNo("CITIC00");
					    SacBankRecon.setBusiType("1");
						SacBankRecon.setRecStartDate(format1(strDate+"000000"));
						SacBankRecon.setRecEndDate(format1(strDate+"000000"));
						SacBankRecon.setRecCount(new Long(0));
					}
				}
				read.close();
			} catch (Exception e) {
				throw new RuntimeException("读取文件内容出错");
				/*logger.error("读取中信银行对账文件内容出错");
				e.printStackTrace();
				return null;*/
			}
		}
		// 逆向文件解析
		else if ("2".equals(busiType)) {
			try {
				InputStreamReader read = new InputStreamReader(inputStream,encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				for (int i = 0; (lineTxt = bufferedReader.readLine()) != null; i++) {
					// 中信银行对账文件.txt 的body内容从第7行开始
					if (i >= 6) {
						count ++;
						SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
						String lineTxtStr[] = lineTxt.split("\\|");
						SacBankRecon.setBankSerialNo(lineTxtStr[0]);
						SacBankRecon.setPayconType("2");
						SacBankRecon.setCurrencyType("CNY");
						SacBankRecon.setPayAmount(lineTxtStr[3]);
						SacBankRecon.setTrxTime(format1(format(lineTxtStr[2])));
						SacBankRecon.setRecOper("000001");
						SacBankRecon.setChnNo("CITIC00");
						// 逆向交易值为2
						SacBankRecon.setBusiType("2");
						SacBankRecon.setRecStartDate(format1(format(lineTxtStr[2]).substring(0, 8)+"000000"));
						SacBankRecon.setRecEndDate(format1(format(lineTxtStr[2]).substring(0, 8)+"000000"));
						SacBankRecon.setRecCount(new Long(count));
						SacBankRecon.setTrxCode("3303"); //新增交易码
						list.add(SacBankRecon);
					}else{
						////当天无对账数据，插入一条流水号、交易金额、记录数为0的默认值
						SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
						SacBankRecon.setBankSerialNo("0");
						SacBankRecon.setPayconType("2");
						SacBankRecon.setCurrencyType("CNY");
						SacBankRecon.setPayAmount("0");
	                    Date date = new Date(new Date().getTime()-24*60*60*1000);
						SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
						String strDate = f.format(date);
	                    SacBankRecon.setTrxTime(f.parse(strDate));
						SacBankRecon.setRecOper("000001");
						SacBankRecon.setChnNo("CITIC00");
					    SacBankRecon.setBusiType("1");
						SacBankRecon.setRecStartDate(format1(strDate+"000000"));
						SacBankRecon.setRecEndDate(format1(strDate+"000000"));
						SacBankRecon.setRecCount(new Long(0));
					}
				}
				read.close();
			} catch (Exception e) {
				logger.error("读取中信银行对账文件内容出错");
				e.printStackTrace();
				return null;
			}
		}
		//设置交易总笔数
		for(int i= 0;i<list.size();i++){
			SacReceiveBankRecon SacBankRecon = list.get(i);
			SacBankRecon.setRecCount(new Long(count));
			list1.add(SacBankRecon);
		}
		return list1;
	}

	// 日期格式转换
	public static String format(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str1 = str.replace("-", "");
		String str2 = str1.replace(":", "");
		return str2;
	}
	
	//日期格式转换yyyyMMddHHmmss
	public static Date format1(String str) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMddHHmmss" );
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
