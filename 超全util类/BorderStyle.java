
package com.zjht.youoil.util;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * 功能描述：Excel表格边框
 * @author fengsg
 * @since 2012-9-27
 */
public class BorderStyle {

	/**
	 * 设置Excel表格黑细边框
	 * @param wb
	 * @return
	 */
	public CellStyle  setBorderThinStyle(CellStyle style) {
	    style.setBorderBottom(CellStyle.BORDER_THIN);
	    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderLeft(CellStyle.BORDER_THIN);
	    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderRight(CellStyle.BORDER_THIN);
	    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    style.setBorderTop(CellStyle.BORDER_THIN);
	    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    return style;
	}
	/**
	 * 设置Excel表格黑粗边框
	 * @param wb
	 * @return
	 */
	public CellStyle  setBorderThickStyle(CellStyle style) {
		style.setBorderBottom(CellStyle.BORDER_THICK);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THICK);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THICK);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THICK);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
}
