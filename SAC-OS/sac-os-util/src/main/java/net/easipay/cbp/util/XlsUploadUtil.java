package net.easipay.cbp.util;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.form.SacTransationSendForm;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class XlsUploadUtil {
	
	public static List<SacTransationSendForm> uploadXlsFile(MultipartFile[] myfiles,HttpServletRequest request) throws Exception{

		// 如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
		// 如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
		// 并且上传多个文件时，前台表单中的所有<input
		// type="file">的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
		List<SacTransationSendForm> list = null;
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				System.out.println("文件未上传");
			} else {
				System.out.println("文件长度: " + myfile.getSize());
				System.out.println("文件类型: " + myfile.getContentType());
				System.out.println("文件名称: " + myfile.getName());
				System.out.println("文件原名: " + myfile.getOriginalFilename());
				System.out.println("========================================");
				// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
				String realPath = request.getSession().getServletContext()
						.getRealPath("/files/upload");
				// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
				File file = new File(realPath, myfile.getOriginalFilename());
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
				if (myfile.getOriginalFilename().toLowerCase().endsWith("xls")) {
					list= readXls(myfile.getInputStream());
				} else {
					list = readXlsx(file + "");
				}
			}
		}
		return list;
	
	}
	
	@SuppressWarnings("resource")
	private static List<SacTransationSendForm> readXlsx(String fileName) throws Exception {
		// String fileName = "D:\\excel\\xlsx_test.xlsx";
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileName);
		List<SacTransationSendForm> list = new ArrayList<SacTransationSendForm>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				int count = 1;
				if (xssfRow != null) {
					SacTransationSendForm trx = new SacTransationSendForm();
					try {
						trx.setTrxSerialNo(getValue(xssfRow.getCell(0)));count++;
						trx.setPayCurrency(getValue(xssfRow.getCell(1)));count++;
						trx.setPayAmount(new BigDecimal(getValue(xssfRow.getCell(2))));count++;
						
						trx.setBussType(getValue(xssfRow.getCell(3)));count++;
						trx.setTrxType(getValue(xssfRow.getCell(4)));count++;
						trx.setTrxState(getValue(xssfRow.getCell(5)));count++;
						trx.setPayconType(getValue(xssfRow.getCell(6)));count++;
						trx.setPayWay(getValue(xssfRow.getCell(7)));count++;
						trx.setTrxTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(getValue(xssfRow.getCell(8))));count++;
						
						
						trx.setCraccCusCode(getValue(xssfRow.getCell(9)));count++;//收款方客户号
						trx.setCraccCusType(getValue(xssfRow.getCell(10)));count++;
						trx.setCraccCusName(getValue(xssfRow.getCell(11)));count++;
						trx.setCraccNo(getValue(xssfRow.getCell(12)));count++;
						trx.setCraccName(getValue(xssfRow.getCell(13)));count++;
						trx.setCraccNodeCode(getValue(xssfRow.getCell(14)));count++;
						trx.setCraccBankName(getValue(xssfRow.getCell(15)));count++;
						
						trx.setDraccCusCode(getValue(xssfRow.getCell(16)));count++;//收款方客户号
						trx.setDraccCusType(getValue(xssfRow.getCell(17)));count++;
						trx.setDraccCusName(getValue(xssfRow.getCell(18)));count++;
						trx.setDraccNo(getValue(xssfRow.getCell(19)));count++;
						trx.setDraccName(getValue(xssfRow.getCell(20)));count++;
						trx.setDraccNodeCode(getValue(xssfRow.getCell(21)));count++;
						trx.setDraccBankName(getValue(xssfRow.getCell(22)));count++;
					} catch (Exception e) {
						throw new SacException("100001", "第["+rowNum+"]行第["+count+"]列字段不能为空");
					}
					
					trx.setOtrxSerialNo(xssfRow.getCell(23)==null? "": getValue(xssfRow.getCell(23)));
					trx.setEtrxSerialNo(xssfRow.getCell(24)==null? "": getValue(xssfRow.getCell(24)));
					trx.setSacCurrency(xssfRow.getCell(25)==null? "": getValue(xssfRow.getCell(25)));
					trx.setSacAmount(xssfRow.getCell(26)==null? BigDecimal.ZERO: new BigDecimal(getValue(xssfRow.getCell(26))));
					
					trx.setTrxBatchNo(xssfRow.getCell(27)==null? "": getValue(xssfRow.getCell(27)));
					trx.setTrxCost(xssfRow.getCell(28)==null? BigDecimal.ZERO: new BigDecimal(getValue(xssfRow.getCell(28))));
					trx.setExRate(xssfRow.getCell(29)==null? BigDecimal.ZERO: new BigDecimal(getValue(xssfRow.getCell(29))));
					trx.setTrxErrDealType(xssfRow.getCell(27)==null? "": getValue(xssfRow.getCell(30)));
					trx.setTaxAmount(xssfRow.getCell(31)==null? BigDecimal.ZERO: new BigDecimal(getValue(xssfRow.getCell(31))));
					trx.setTransportExpenses(xssfRow.getCell(32)==null? BigDecimal.ZERO: new BigDecimal(getValue(xssfRow.getCell(32))));
					
					list.add(trx);
				}

			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

	@SuppressWarnings("resource")
	private static List<SacTransationSendForm> readXls(InputStream is) throws Exception {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<SacTransationSendForm> list = new ArrayList<SacTransationSendForm>();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				int count = 1 ;
				if (hssfRow != null) {
					SacTransationSendForm trx = new SacTransationSendForm();
					try {
						trx.setTrxSerialNo(getValue(hssfRow.getCell(0)));count++;
						trx.setPayCurrency(getValue(hssfRow.getCell(1)));count++;
						trx.setPayAmount(new BigDecimal(getValue(hssfRow.getCell(2))));count++;
						
						trx.setBussType(getValue(hssfRow.getCell(3)));count++;
						trx.setTrxType(getValue(hssfRow.getCell(4)));count++;
						trx.setTrxState(getValue(hssfRow.getCell(5)));count++;
						trx.setPayconType(getValue(hssfRow.getCell(6)));count++;
						trx.setPayWay(getValue(hssfRow.getCell(7)));count++;
						trx.setTrxTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(getValue(hssfRow.getCell(8))));count++;
						
						
						trx.setCraccCusCode(getValue(hssfRow.getCell(9)));count++;//收款方客户号
						trx.setCraccCusType(getValue(hssfRow.getCell(10)));count++;
						trx.setCraccCusName(getValue(hssfRow.getCell(11)));count++;
						trx.setCraccNo(getValue(hssfRow.getCell(12)));count++;
						trx.setCraccName(getValue(hssfRow.getCell(13)));count++;
						trx.setCraccNodeCode(getValue(hssfRow.getCell(14)));count++;
						trx.setCraccBankName(getValue(hssfRow.getCell(15)));count++;
						
						trx.setDraccCusCode(getValue(hssfRow.getCell(16)));count++;//收款方客户号
						trx.setDraccCusType(getValue(hssfRow.getCell(17)));count++;
						trx.setDraccCusName(getValue(hssfRow.getCell(18)));count++;
						trx.setDraccNo(getValue(hssfRow.getCell(19)));count++;
						trx.setDraccName(getValue(hssfRow.getCell(20)));count++;
						trx.setDraccNodeCode(getValue(hssfRow.getCell(21)));count++;
						trx.setDraccBankName(getValue(hssfRow.getCell(22)));count++;
					} catch (Exception e) {
						throw new SacException("100001", "第["+rowNum+"]行第["+count+"]列字段不能为空");
					}
					
					trx.setOtrxSerialNo(hssfRow.getCell(23)==null? "": getValue(hssfRow.getCell(23)));
					trx.setEtrxSerialNo(hssfRow.getCell(24)==null? "": getValue(hssfRow.getCell(24)));
					trx.setSacCurrency(hssfRow.getCell(25)==null? "": getValue(hssfRow.getCell(25)));
					trx.setSacAmount(hssfRow.getCell(26)==null? BigDecimal.ZERO: new BigDecimal(getValue(hssfRow.getCell(26))));
					
					trx.setTrxBatchNo(hssfRow.getCell(27)==null? "": getValue(hssfRow.getCell(27)));
					trx.setTrxCost(hssfRow.getCell(28)==null? BigDecimal.ZERO: new BigDecimal(getValue(hssfRow.getCell(28))));
					trx.setExRate(hssfRow.getCell(29)==null? BigDecimal.ZERO: new BigDecimal(getValue(hssfRow.getCell(29))));
					trx.setTrxErrDealType(hssfRow.getCell(27)==null? "": getValue(hssfRow.getCell(30)));
					trx.setTaxAmount(hssfRow.getCell(31)==null? BigDecimal.ZERO: new BigDecimal(getValue(hssfRow.getCell(31))));
					trx.setTransportExpenses(hssfRow.getCell(32)==null? BigDecimal.ZERO: new BigDecimal(getValue(hssfRow.getCell(32))));
					/*ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
					Validator validator = factory.getValidator(); 
					validator.validate(trx); */
					list.add(trx);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

}
