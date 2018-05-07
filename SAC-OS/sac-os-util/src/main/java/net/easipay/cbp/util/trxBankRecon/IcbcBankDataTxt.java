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
 * 功能：工商银行对账txt格式转换
 * @author zfy 
 * create：2015-7-23
 */
public class IcbcBankDataTxt {
	/**
	 * 功能：Java读取txt文件
	 * 1：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 2：读取到输入流后，需要读取生成字节流 
	 * 3：一行一行的输出。readline()。 
	 * 备注：需要考虑的是异常情况
	 */
	private static Logger logger = LoggerFactory.getLogger(IcbcBankDataTxt.class);
	
	public static List<SacReceiveBankRecon> readTxtFile(InputStream inputStream) {
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		List<SacReceiveBankRecon> list1 = new ArrayList<SacReceiveBankRecon>();
		int count =0;
		try {
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(inputStream,encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			for (int i = 0; (lineTxt = bufferedReader.readLine()) != null; i++) {
				// 工商银行对账文件.txt 的body内容对账数据从第5行开始
				if (i >= 4) {
					count ++;
					SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
					// "\\s{2,}" 为1个空格以上进行分割
					String lineTxtStr[] = lineTxt.split("\\s{1,}");
					SacBankRecon.setBankSerialNo(lineTxtStr[1]);
					SacBankRecon.setPayconType("2");
					SacBankRecon.setCurrencyType("CNY");
					SacBankRecon.setPayAmount(lineTxtStr[3]);
					String trxTime = lineTxtStr[4] + lineTxtStr[5] + ":00";
					SacBankRecon.setTrxTime(format1(format(trxTime)));
					SacBankRecon.setRecOper("000001");
					SacBankRecon.setChnNo("ICBC000");
					// 判断是否正向和逆向交易
					if (lineTxtStr[6].equals("支付成功已清算")) {
						SacBankRecon.setBusiType("1");
					} else {
						SacBankRecon.setBusiType("2");
					}
					SacBankRecon.setRecStartDate(format1(format(trxTime).substring(0, 8)+"000000"));
					SacBankRecon.setRecEndDate(format1(format(trxTime).substring(0, 8)+"000000"));
					SacBankRecon.setRecCount(new Long(count));
					list.add(SacBankRecon);
				}
			}
			read.close();
		} catch (Exception e) {
			logger.error("读取工商银行对账文件内容出错");
			throw new RuntimeException("读取文件内容出错");
			/*e.printStackTrace();
			return null;*/
		}
		//设置交易总笔数
		for(int i=0;i<list.size();i++){
			SacReceiveBankRecon SacBankRecon = (SacReceiveBankRecon)list.get(i);
			SacBankRecon.setRecCount(new Long(count));
			list1.add(SacBankRecon);
		}
		return list1;
	}

	// 交易时间格式转换
	public static String format(String str) {
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
