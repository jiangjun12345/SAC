package net.easipay.cbp.util.trxBankRecon;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.easipay.cbp.model.SacReceiveBankRecon;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PsbcBankDataExcel {

	/**
	 * 功能：邮政储蓄银行对账文件的数据转换解析
	 * the path of the Excel file
	 * @return List<SacReceiveBankRecon>
	 * @throws IOException
	 * autor：zfy 2015-07-27
	 */
	private static Logger logger = LoggerFactory.getLogger(PsbcBankDataExcel.class);

	public static List<SacReceiveBankRecon> readExcel(InputStream inputStream,String fileName) throws IOException {
		if (fileName == null || "".equals(fileName)) {
			return null;
		}
		// 获取Excel文件的版本类型，以.xls后缀的为2003-2007版本，以.xlsx结尾的，则为2010版本
		String officeExcelType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if (!"".equals(officeExcelType)) {
			// 2003-2007版本的解析
			if ("xls".equals(officeExcelType)) {
				return readXls(inputStream, fileName);
			} // 2010版本的解析
			else if ("xlsx".equals(officeExcelType)) {
				return readXlsx(inputStream, fileName);
			}
		} else {
			logger.info(": Not the Excel file!");
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 * @param path
	 * the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public static List<SacReceiveBankRecon> readXlsx(InputStream inputStream,String fileName) throws IOException {
		System.out.println("Processing..." + fileName);
		InputStream is = inputStream;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		int count =0;
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 解析邮政储蓄银行账数据
			// Read the Row 读取body每一行的数据赋值给渠道对账文件对象
			for (int rowNum = 4; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					count ++;
					if (xssfRow.getCell(0) != null) {
						SacReceiveBankRecon sacReceiveBankRecon = new SacReceiveBankRecon();
						XSSFCell bankSerialNo = xssfRow.getCell(0);
						XSSFCell trxDate = xssfRow.getCell(1);// 交易日期 2015-07-17
						XSSFCell trxTime = xssfRow.getCell(2);// 交易时间 03:51:24
						XSSFCell payAmountPay = xssfRow.getCell(10); // 借方金额
						XSSFCell payAmountReturn = xssfRow.getCell(11);// 贷方金额
						// 判断是否是正向和逆向交易，如果借方金额不为空，则该笔交易为支付交易，如果贷方金额不为空，则该笔交易为退款交易
						if (!getValue(payAmountPay).isEmpty()) {
							// 交易金额
							sacReceiveBankRecon.setPayAmount(getValue(payAmountPay));
							// 正向（支付）交易
							sacReceiveBankRecon.setBusiType("1");
						} else if (!getValue(payAmountReturn).isEmpty()) {
							// 交易金额
							sacReceiveBankRecon.setPayAmount(getValue(payAmountReturn));
							// 逆向（退款）交易
							sacReceiveBankRecon.setBusiType("2");
						}
						sacReceiveBankRecon.setBankSerialNo(getValue(bankSerialNo));
						sacReceiveBankRecon.setPayconType("2");
						sacReceiveBankRecon.setCurrencyType("CNY");
						sacReceiveBankRecon.setTrxTime(format1(format(getValue(trxDate)+ getValue(trxTime))));
						sacReceiveBankRecon.setRecOper("000001");
						sacReceiveBankRecon.setChnNo("BC00000");
						sacReceiveBankRecon.setRecStartDate(format1((format(getValue(trxDate)+ getValue(trxTime))).substring(0, 8)+"000000"));
						sacReceiveBankRecon.setRecEndDate(format1((format(getValue(trxDate)+ getValue(trxTime))).substring(0, 8)+"000000"));
						sacReceiveBankRecon.setRecCount(new Long(count));
						list.add(sacReceiveBankRecon);
					}
				}
			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * @param path
	 * the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public static List<SacReceiveBankRecon> readXls(InputStream inputStream,
			String fileName) throws IOException {
		System.out.println("Processing..." + fileName);
		InputStream is = inputStream;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		int count =0;
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			// 解析邮政储蓄银行账数据
			for (int rowNum = 4; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					count ++;
					SacReceiveBankRecon sacReceiveBankRecon = new SacReceiveBankRecon();
					if (hssfRow.getCell(0) != null) {
						HSSFCell bankSerialNo = hssfRow.getCell(0); // 外部流水号
						HSSFCell trxDate = hssfRow.getCell(1); // 交易日期  2015-07-17
						HSSFCell trxTime = hssfRow.getCell(2); // 交易时间 03:51:24
						HSSFCell payAmountPay = hssfRow.getCell(10); // 借方金额
						HSSFCell payAmountReturn = hssfRow.getCell(11); // 贷方金额
						// 判断是否是正向和逆向交易，如果借方金额不为空，则该笔交易为支付交易，如果贷方金额不为空，则该笔交易为退款交易
						if (!getValue(payAmountPay).isEmpty()) {
							// 交易金额
							sacReceiveBankRecon.setPayAmount(getValue(payAmountPay));
							// 正向（支付）交易
							sacReceiveBankRecon.setBusiType("1");
						} else if (!getValue(payAmountReturn).isEmpty()) {
							// 交易金额
							sacReceiveBankRecon.setPayAmount(getValue(payAmountReturn));
							// 逆向（退款）交易
							sacReceiveBankRecon.setBusiType("2");
						}
						sacReceiveBankRecon.setBankSerialNo(getValue(bankSerialNo));
						sacReceiveBankRecon.setPayconType("2");
						sacReceiveBankRecon.setCurrencyType("CNY");
						// 把交易日期 2015-07-17 和 交易时间 03:51:24 合并成这种格式：yyyyMMddHHmmss
						sacReceiveBankRecon.setTrxTime(format1(format(getValue(trxDate)+ getValue(trxTime))));
						sacReceiveBankRecon.setRecOper("000001");
						sacReceiveBankRecon.setChnNo("PSBC000");
						sacReceiveBankRecon.setRecStartDate(format1(format(getValue(trxDate)+ getValue(trxTime)).substring(0, 8)+"000000"));
						sacReceiveBankRecon.setRecEndDate(format1(format(getValue(trxDate)+ getValue(trxTime)).substring(0, 8)+"000000"));
						sacReceiveBankRecon.setRecCount(new Long(count));
						list.add(sacReceiveBankRecon);
					}
				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			xssfRow.setCellType(XSSFCell.CELL_TYPE_STRING);
			String cellValue = xssfRow.toString();
			return cellValue;// String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {

		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			hssfCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			String cellValue = hssfCell.toString();
			return cellValue;// String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	// 日期格式转换
	public static String format(String str) {
		String str1 =str.replace("-", "");
		String str2 =str1.replace(":", "");
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
