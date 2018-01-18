package com.lazada.handler;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.lazada.model.json.firstlevel.ListItems;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public interface PlatformService {

	public void startCatching(ConnectImpl connectImpl,int exlRow,WritableWorkbook workbook) throws RowsExceededException, WriteException, IOException;
	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook,int exlRow);
	public int getFirstLevel(ConnectImpl ci,String website,String pagenum,String keyword,WritableSheet sheet,int exlRow) throws IOException ;
	public int getFisrtLevelLink(ConnectImpl ci, Document doc, WritableSheet sheet, String pagenum, int exlRow) throws RowsExceededException, WriteException;
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow,ListItems ietelement) throws RowsExceededException, WriteException ;
	public int getInfoByStoreLink(ConnectImpl connectImpl,String pagenum, String storelink,WritableSheet sheet, int exlRow) throws IOException;
	public int getInfoByProductLink(ConnectImpl ci,String links,WritableSheet sheet,int exlRow) throws IOException, RowsExceededException, WriteException ;
}
