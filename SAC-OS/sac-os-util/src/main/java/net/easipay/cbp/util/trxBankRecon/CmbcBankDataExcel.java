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

/**
 * 功能：民生银行对账文件的数据转换解析 
 * 
 * @return List<SacReceiveBankRecon>
 * @throws IOException
 * autor：zfy create: 2015-07-24
 */
public class CmbcBankDataExcel {

	private static Logger logger = LoggerFactory
			.getLogger(CmbBankDataExcel.class);

	public static List<SacReceiveBankRecon> readExcel(InputStream inputStream,
			String fileName) throws IOException {
		if (fileName == null || "".equals(fileName)) {
			return null;
		}
		// 获取Excel文件的版本类型，以.xls后缀的为2003-2007版本，以.xlsx结尾的，则为2010版本
		String officeExcelType = fileName.substring(
				fileName.lastIndexOf(".") + 1, fileName.length());
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
	 * 
	 * @param path
	 * the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public static List<SacReceiveBankRecon> readXlsx(InputStream inputStream,
			String fileName) throws IOException {
		System.out.println("Processing..." + fileName);
		InputStream is = inputStream;
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 民生银行对账数据解析
			// Read the Row 读取body的第15行数据，对账数据是从第15行开始的
			for (int rowNum = 14; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					if (xssfRow.getCell(1) != null) {
						SacReceiveBankRecon sacReceiveBankRecon = new SacReceiveBankRecon();
						XSSFCell trxTime = xssfRow.getCell(0);
						XSSFCell bankSerialNo = xssfRow.getCell(1);
						//借方发生额 
						XSSFCell payAmountPay = xssfRow.getCell(2);
						//贷方发生额
						XSSFCell payAmountReturn = xssfRow.getCell(3);
						sacReceiveBankRecon.setBankSerialNo(getValue(bankSerialNo));
						sacReceiveBankRecon.setPayconType("2");
						sacReceiveBankRecon.setCurrencyType("CNY");
						// 判断正向逆向交易及交易金额
						if (!getValue(payAmountPay).toString().equals("0")) {
							sacReceiveBankRecon.setPayAmount(getValue(payAmountPay));
							sacReceiveBankRecon.setBusiType("1");
						} else if(!getValue(payAmountReturn).toString().equals("0")) {
							sacReceiveBankRecon.setPayAmount(getValue(payAmountReturn));
							sacReceiveBankRecon.setBusiType("2");
						}
						sacReceiveBankRecon.setTrxTime(format1(format(getValue(trxTime)+ "000000")));
						sacReceiveBankRecon.setRecOper("000001");
						sacReceiveBankRecon.setChnNo("CMBC000");
						list.add(sacReceiveBankRecon);
					}
				}
			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public static List<SacReceiveBankRecon> readXls(InputStream inputStream,
			String fileName) throws IOException {
		System.out.println("Processing..." + fileName);
		InputStream is = inputStream;
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		SacReceiveBankRecon sacReceiveBankRecon = null;
		List<SacReceiveBankRecon> list = new ArrayList<SacReceiveBankRecon>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			// 解析交通银行账数据
			for (int rowNum = 14; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					sacReceiveBankRecon = new SacReceiveBankRecon();
					HSSFCell trxTime = hssfRow.getCell(0);
					HSSFCell bankSerialNo = hssfRow.getCell(1);
					//借方发生额 
					HSSFCell payAmountPay = hssfRow.getCell(6);
					//贷方发生额
					HSSFCell payAmountRturn = hssfRow.getCell(6);
					sacReceiveBankRecon.setBankSerialNo(getValue(bankSerialNo));
					sacReceiveBankRecon.setPayconType("2");
					sacReceiveBankRecon.setCurrencyType("CNY");
					sacReceiveBankRecon.setRecEndDate(format1(format(getValue(trxTime)+ "000000")));
					// 判断正向逆向交易及交易金额
					if(!getValue(payAmountPay).toString().equals("0")){
						sacReceiveBankRecon.setPayAmount(getValue(payAmountPay));
						sacReceiveBankRecon.setBusiType("1");
					}else if(!getValue(payAmountRturn).toString().equals("0")){
						sacReceiveBankRecon.setPayAmount(getValue(payAmountRturn));
						sacReceiveBankRecon.setBusiType("2");
					}
					sacReceiveBankRecon.setTrxTime(format1(format(getValue(trxTime)+ "000000")));
					sacReceiveBankRecon.setRecOper("000001");
					sacReceiveBankRecon.setChnNo("CMBC000");
					list.add(sacReceiveBankRecon);
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
			return cellValue;
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
			return cellValue;
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	// 日期格式转换
	public static String format(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str1 = str.replace("-", "");
		return str1;
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
