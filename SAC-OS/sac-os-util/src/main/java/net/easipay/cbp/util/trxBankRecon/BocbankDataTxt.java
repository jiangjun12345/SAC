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
 * 功能：中国银行对账txt格式转换
 * @author zfy 
 * create：2015-7-23
 */
public class BocbankDataTxt {
	/**
	 * 功能：Java读取txt文件的内容 步骤： 
	 * 1：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 2：读取到输入流后，需要读取生成字节流
	 *  3：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * @param filePath
	 */
	private static Logger logger = LoggerFactory.getLogger(BocbankDataTxt.class);

	public static List<SacReceiveBankRecon> readTxtFile(InputStream inputStream) {
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		List<SacReceiveBankRecon> list1 = new ArrayList<SacReceiveBankRecon>();
		try {
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(inputStream,encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
		    int count =0;//交易总笔数
			for (int i = 0; (lineTxt = bufferedReader.readLine()) != null; i++) {
				// 中国银行对账文件.txt 的body内容从第2行开始
				if (i >= 1) {
					count ++;
					SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
					String lineTxtStr[] = lineTxt.split("\\|");
					SacBankRecon.setBankSerialNo(lineTxtStr[2]);
					SacBankRecon.setPayconType("2");
					SacBankRecon.setCurrencyType("CNY");
					SacBankRecon.setPayAmount(lineTxtStr[6]);
					SacBankRecon.setTrxTime(format(lineTxtStr[4]));
					SacBankRecon.setRecOper("000001");
					SacBankRecon.setChnNo("BOC0000");
					// 判断是否正向和逆向交易
					if (lineTxtStr[8].equals("PCEP")) {
						SacBankRecon.setBusiType("1");
					} else {
						SacBankRecon.setBusiType("2");
					}
					SacBankRecon.setRecStartDate(format(lineTxtStr[4].substring(0, 8)+"000000"));
					SacBankRecon.setRecEndDate(format(lineTxtStr[4].substring(0, 8)+"000000"));
					SacBankRecon.setRecCount(new Long(count));
					list.add(SacBankRecon);
				}
			}
			read.close();
			//设置交易总笔数
			for(int i=0;i<list.size();i++){
				SacReceiveBankRecon SacBankRecon = (SacReceiveBankRecon)list.get(i);
				SacBankRecon.setRecCount(new Long(count));
				list1.add(SacBankRecon);
			}
			
		} catch (Exception e) {
			logger.error("读取文件内容出错");
			throw new RuntimeException("读取文件内容出错");
			/*e.printStackTrace();
			return null;*/
		}
		return list1;
	}
	
	//日期格式转换yyyyMMddHHmmss
	public static Date format(String str) {
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
