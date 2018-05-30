package com.zjht.youoil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.stereotype.Service;


/**
 * 功能描述：
 * 
 * @author dengbh
 * @since 2014-1-9
 */
@Service
public class ExportUtil {

	/**
	 * 导出Excel
	 * 
	 * @param fileName
	 *            要导出的Excel的文件名
	 * @param request
	 * @param response
	 * @param data
	 *            数据
	 * @param dataFormat
	 *            每列的数据格式
	 * @throws IOException
	 */
	public void exportExcel(String fileName, HttpServletRequest request, HttpServletResponse response, String[] head, List data, ExcelDataFormat[] dataFormat)
			throws IOException {
		OutputStream out = response.getOutputStream();
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			String filenamedisplay = URLEncoder.encode(fileName, "UTF-8");
			// if
			// (request.getHeader("User-Agent").toLowerCase().indexOf("firefox")
			// > 0)
			// filenamedisplay = new String(fileName.getBytes("UTF-8"),
			// "ISO8859-1");// firefox浏览器
			// else if
			// (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") >
			// 0)
			// filenamedisplay = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
			response.setHeader("Content-Dispositon", "attachment;filename=" + filenamedisplay);
			Workbook book = new HSSFWorkbook();
			String safeName = WorkbookUtil.createSafeSheetName("数据");
			Sheet sheet = book.createSheet(safeName);
			CellStyle[] cellStyles = getCellStyles(book, dataFormat);
			// 处理表头
			drawHead(book, sheet, head);
			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Object[] rowData = (Object[]) data.get(i);
				for (int j = 0; j < rowData.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(cellStyles[j]);
					if (dataFormat[j] == ExcelDataFormat.DATE) {
						cell.setCellValue((Date) rowData[j]);
					} else if (dataFormat[j] == ExcelDataFormat.DOUBLE) {
						cell.setCellValue(((Double) rowData[j]).doubleValue());
					} else {
						cell.setCellValue((String) rowData[j]);
					}

				}
			}
			book.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 处理表头
	 * 
	 * @param book
	 * @param head
	 */
	public static void drawHead(Workbook book, Sheet sheet, String[] head) {
		Font columnHeadFont = book.createFont();   
	    columnHeadFont.setFontName("宋体");   
	    columnHeadFont.setFontHeightInPoints((short) 14);   
	    columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	    CellStyle columnHeadStyle = book.createCellStyle();   
	    columnHeadStyle.setFont(columnHeadFont);   
	    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
	    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
	    columnHeadStyle.setLocked(true);   
	    columnHeadStyle.setWrapText(true);   
	    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小   
	    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
	    columnHeadStyle.setBorderRight((short) 1);// 边框的大小   
	    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
	    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
	    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）   
	    columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index); 
		Row row = sheet.createRow(0);
		for (int i = 0; i < head.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(columnHeadStyle);
			cell.setCellValue(head[i]);
			sheet.setColumnWidth(i, 5200);
		}
	}

	/**
	 * 根据每列的数据类型，设置单元格数据类型
	 * 
	 * @param dataFormat
	 * @return
	 */
	private static CellStyle[] getCellStyles(Workbook book, ExcelDataFormat[] dataFormat) {
		CellStyle[] result = new CellStyle[dataFormat.length];
		CreationHelper helper = book.getCreationHelper();
	    Font columnHeadFont = book.createFont();   
	    columnHeadFont.setFontName("宋体");   
	    CellStyle columnHeadStyle = book.createCellStyle();   
	    columnHeadStyle.setFont(columnHeadFont);   
	    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
	    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
	    columnHeadStyle.setLocked(true);   
	    columnHeadStyle.setWrapText(true);   
	    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小   
	    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
	    columnHeadStyle.setBorderRight((short) 1);// 边框的大小   
	    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
	    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
	    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）   
	    columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index); 
		for (int i = 0; i < dataFormat.length; i++) {
			if (dataFormat[i] == ExcelDataFormat.DATE) {
				columnHeadStyle.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd"));
			}
			result[i] = columnHeadStyle;
		}
		return result;
	}

	/**
	 * 使用条件：需要导出的数据均为String类型时使用<br/>
	 * 导出Excel
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	public static void exportExcel(String fileName, HttpServletRequest request, HttpServletResponse response, String[] head, List data) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("pragma", "no-cache");
		response.setContentType("application/octet-stream;charset=UTF-8");
		//System.out.println("filename is :" + java.net.URLEncoder.encode(fileName, "utf-8"));
		response.setHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		try {
			Workbook book = new HSSFWorkbook();
			String safeName = WorkbookUtil.createSafeSheetName("数据");
			Sheet sheet = book.createSheet(safeName);
			Font font = book.createFont();   
		    font.setFontName("宋体");   
			// 普通单元格样式   
		    CellStyle style = book.createCellStyle();   
		    style.setFont(font);   
		    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中   
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中   
		    style.setWrapText(true);   
		    style.setLeftBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderLeft((short) 1);   
		    style.setRightBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderRight((short) 1);   
		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
		    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．   
		    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
			// 处理表头
			drawHead(book, sheet, head);
			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Object[] rowData = (Object[]) data.get(i);
				for (int j = 0; j < rowData.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(new BorderStyle().setBorderThinStyle(book.createCellStyle()));
					String cvalue=(String)rowData[j];					
					cell.setCellValue(cvalue);
					cell.setCellStyle(style);
				}
			}
			// book.set
			book.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
	/**
	 * 新的文件导出方法，行数据不使用数组，改成list，方便列数据调换位置
	 * @param fileName
	 * @param request
	 * @param response
	 * @param head
	 * @param data
	 * @throws IOException
	 */
	public static void exportExcelNew(String fileName, HttpServletRequest request, HttpServletResponse response, String[] head, List<List<String>> data) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("pragma", "no-cache");
		response.setContentType("application/octet-stream;charset=UTF-8");
		//System.out.println("filename is :" + java.net.URLEncoder.encode(fileName, "utf-8"));
		response.setHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		try {
			Workbook book = new HSSFWorkbook();
			String safeName = WorkbookUtil.createSafeSheetName("数据");
			Sheet sheet = book.createSheet(safeName);
			Font font = book.createFont();   
		    font.setFontName("宋体");   
			// 普通单元格样式   
		    CellStyle style = book.createCellStyle();   
		    style.setFont(font);   
		    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中   
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中   
		    style.setWrapText(true);   
		    style.setLeftBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderLeft((short) 1);   
		    style.setRightBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderRight((short) 1);   
		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
		    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．   
		    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
			// 处理表头
			drawHead(book, sheet, head);
			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				List<String> rowData = data.get(i);
				ListIterator<String> iter = rowData.listIterator();
				while(iter.hasNext()){
					Cell cell = row.createCell(iter.nextIndex());
					cell.setCellStyle(new BorderStyle().setBorderThinStyle(book.createCellStyle()));
					String cvalue=(String)iter.next();
					cell.setCellValue(cvalue);
					cell.setCellStyle(style);
					
				}
			}
			// book.set
			book.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
	/**
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @param head
	 * @param data
	 * @param filePath 文件保存路径
	 * @return
	 * @throws IOException
	 */
	public static File exportExcelAndCreateFile(String fileName, HttpServletRequest request, HttpServletResponse response, String[] head, List<List<String>> data,String filePath) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("pragma", "no-cache");
		response.setContentType("application/octet-stream;charset=UTF-8");
		//System.out.println("filename is :" + java.net.URLEncoder.encode(fileName, "utf-8"));
		response.setHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		File file = null;
		try {
			Workbook book = new HSSFWorkbook();
			String safeName = WorkbookUtil.createSafeSheetName("数据");
			Sheet sheet = book.createSheet(safeName);
			Font font = book.createFont();   
		    font.setFontName("宋体");   
			// 普通单元格样式   
		    CellStyle style = book.createCellStyle();   
		    style.setFont(font);   
		    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中   
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中   
		    style.setWrapText(true);   
		    style.setLeftBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderLeft((short) 1);   
		    style.setRightBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderRight((short) 1);   
		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
		    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．   
		    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
			// 处理表头
			drawHead(book, sheet, head);
			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				List<String> rowData = data.get(i);
				ListIterator<String> iter = rowData.listIterator();
				while(iter.hasNext()){
					Cell cell = row.createCell(iter.nextIndex());
					cell.setCellStyle(new BorderStyle().setBorderThinStyle(book.createCellStyle()));
					String cvalue=(String)iter.next();
					cell.setCellValue(cvalue);
					cell.setCellStyle(style);
					
				}
			}
			//生成文件
			file = new File(filePath);
			FileOutputStream fileOut = new FileOutputStream(file);
			book.write(fileOut);
			book.write(out);
			fileOut.flush();
			return file;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
		return null;
	}
	/**
	 * 使用条件：需要导出的数据均为String类型时使用<br/>
	 * 导出Excel
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcelWithFoot(String fileName, HttpServletRequest request, HttpServletResponse response, String[] head, List data,String footer,int frows) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("pragma", "no-cache");
		response.setContentType("application/octet-stream;charset=UTF-8");
		//System.out.println("filename is :" + java.net.URLEncoder.encode(fileName, "utf-8"));
		response.setHeader("Content-Disposition", "filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		try {
			Workbook book = new HSSFWorkbook();
			String safeName = WorkbookUtil.createSafeSheetName("数据");
			Sheet sheet = book.createSheet(safeName);
			Font font = book.createFont();   
		    font.setFontName("宋体");   
			// 普通单元格样式   
		    CellStyle style = book.createCellStyle();   
		    style.setFont(font);   
		    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中   
		    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中   
		    style.setWrapText(true);   
		    style.setLeftBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderLeft((short) 1);   
		    style.setRightBorderColor(HSSFColor.BLACK.index);   
		    style.setBorderRight((short) 1);   
		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
		    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．   
		    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
			// 处理表头
			drawHead(book, sheet, head);
			for (int i = 0; i < data.size(); i++) {
				Row row = sheet.createRow(i + 1);
				Object[] rowData = (Object[]) data.get(i);
				for (int j = 0; j < rowData.length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(new BorderStyle().setBorderThinStyle(book.createCellStyle()));
					String cvalue=(String)rowData[j];
					cell.setCellValue(cvalue);
					cell.setCellStyle(style);
				}
			}
			//统计行
			Row footRow = sheet.createRow(data.size()+1);
			Font columnFooter = book.createFont();   
			columnFooter.setFontName("宋体");   
			columnFooter.setFontHeightInPoints((short) 14);   
			columnFooter.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
			Cell cellFoot=footRow.createCell(0);
			CellStyle columnFooterStyle = book.createCellStyle();   
			columnFooterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
			columnFooterStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
			columnFooterStyle.setLocked(true);   
			columnFooterStyle.setFont(columnFooter);
			columnFooterStyle.setWrapText(true);   
			columnFooterStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色   
			columnFooterStyle.setBorderLeft((short) 1);// 边框的大小   
			columnFooterStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色   
			columnFooterStyle.setBorderRight((short) 1);// 边框的大小   
			columnFooterStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体   
			columnFooterStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色   
		    // 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）   
			columnFooterStyle.setFillForegroundColor(HSSFColor.WHITE.index); 
			
			sheet.addMergedRegion(new CellRangeAddress(data.size()+1, data.size()+frows,  0,  head.length-1));//起始行、起始列、结束行、结束列);
			cellFoot.setCellStyle(new BorderStyle().setBorderThinStyle(book.createCellStyle()));
			cellFoot.setCellValue(footer);
			cellFoot.setCellStyle(columnFooterStyle);
			// book.set
			book.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}
	/**
	 * 使用条件：将Excel按行解析成List<String><br/>
	 * 
	 * @param excel
	 *            Excel文件
	 * @param startSheet
	 *            需要解析的开始页
	 * @param startLine
	 *            需要解析的开始行
	 * @param startColumn
	 *            需要解析的开始列
	 * @throws IOException
	 */
	public static List<List<String>> resolveExcel(InputStream excelInputStream, int startSheet, int startLine, int startColumn) throws IOException {
		List<List<String>> data = new ArrayList<List<String>>();
		Workbook workBook = null;
		workBook = new HSSFWorkbook(excelInputStream);
		// 循环页
		for (int numSheet = startSheet - 1; numSheet < workBook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workBook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = startLine - 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}

				// 循环列Cell
				List<String> arrCell = new ArrayList<String>();
				for (int cellNum = startColumn - 1; cellNum <= row.getLastCellNum(); cellNum++) {
					Cell cell = row.getCell(cellNum);
					
					if (cell == null) {
						arrCell.add(null);
						continue;
					}
					
					arrCell.add(parseExcel(cell));
					
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					String cellValue = String.valueOf(cell.getStringCellValue());
//					arrCell.add(cellValue);
				}
				data.add(arrCell);
			}
		}
		return data;
	}
	
	
	private static String parseExcel(Cell cell) {
		String result = new String();
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
						.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil
						.getJavaDate(value);
				result = sdf.format(date);
			} else {
				
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				result = String.valueOf(cell.getStringCellValue());
//				double value = cell.getNumericCellValue();
//				CellStyle style = cell.getCellStyle();
//				DecimalFormat format = new DecimalFormat();
//				String temp = style.getDataFormatString();
//				// 单元格设置成常规
//				if (temp.equals("General")) {
//					format.applyPattern("#");
//				}
//				result = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String类型
			result = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
		default:
			result = "";
			break;
		}
		return result;
	}


	public static void main(String[] args) throws IOException {
		File file = new File("E:\\123.xls");
		List<List<String>> data = ExportUtil.resolveExcel(new FileInputStream(file), 1, 3, 1);
		for (List<String> row : data) {
			for (String cell : row) {
				System.out.print(cell + "|");
			}
			System.out.println();
		}
	}
}
