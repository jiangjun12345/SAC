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
 * 功能：银联对账txt格式转换
 * @author zfy 
 * create：2015-7-23
 */
public class UnionBankDataTxt {
	/**
	 * 功能：Java读取txt文件的内容
	 * 1：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 2：读取到输入流后，需要读取生成字节流 
	 * 3：一行一行的输出。readline()。
	 *  备注：需要考虑的是异常情况
	 */
	private static Logger logger = LoggerFactory.getLogger(UnionBankDataTxt.class);
	
	public static List<SacReceiveBankRecon> readTxtFile(InputStream inputStream) {
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		List<SacReceiveBankRecon> list1 = new ArrayList<SacReceiveBankRecon>();
		int count = 0;
		try {
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(inputStream,encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			for (int i = 0; (lineTxt = bufferedReader.readLine()) != null; i++) {
				// 银联对账文件.txt 的body内容从第1行开始
				if (i >= 0) {
					count ++;
					SacReceiveBankRecon SacBankRecon = new SacReceiveBankRecon();
					String lineTxtStr[] = lineTxt.split("\\s{1,}");
					SacBankRecon.setBankSerialNo(lineTxtStr[11]);
					SacBankRecon.setPayconType("2");
					SacBankRecon.setCurrencyType("CNY");
					//交易金额格式为：000000019700，后面的两位为分，需要把0去掉，就是元为单位的数据了
					String PayAmount =lineTxtStr[6].replace("0", "")+".00";
					SacBankRecon.setPayAmount(PayAmount);
					// 交易日期
					String trxtime = lineTxtStr[9].substring(0, 14);
					SacBankRecon.setTrxTime(format(trxtime));
					SacBankRecon.setRecOper("000001");
					SacBankRecon.setChnNo("UPOP000");
					// 判断是否正向和逆向交易,是根据外部流水号来判断的，带T开头的是正向交易，否则为逆向交易
					if (lineTxtStr[11].substring(0, 1).equals("T")) {
						SacBankRecon.setBusiType("1");
					} else {
						SacBankRecon.setBusiType("2");
					}
					SacBankRecon.setRecStartDate(format(trxtime.substring(0, 8)+"000000"));
					SacBankRecon.setRecEndDate(format(trxtime.substring(0, 8)+"000000"));
					SacBankRecon.setRecCount(new Long(count));
					list.add(SacBankRecon);
				}
			}
			read.close();
			for(int i=0;i<list.size();i++){
				SacReceiveBankRecon SacBankRecon = (SacReceiveBankRecon)list.get(i);
				SacBankRecon.setRecCount(new Long(count));
				list1.add(SacBankRecon);
			}
		} catch (Exception e) {
			throw new RuntimeException("读取银联对账文件内容出错!");
			/*logger.error("读取银联对账文件内容出错");
			e.printStackTrace();
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
