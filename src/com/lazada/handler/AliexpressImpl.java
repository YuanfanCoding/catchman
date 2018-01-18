package com.lazada.handler;

import java.io.IOException;

import org.jsoup.nodes.Document;

import com.lazada.model.json.firstlevel.ListItems;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class AliexpressImpl extends CheckboxModel implements PlatformService{

	public AliexpressImpl(int type, String typetext) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.typetext=typetext;
		this.startpage=startpage;
		this.endpage=endpage;
	}

	@Override
	public void startCatching(ConnectImpl connectImpl, int exlRow, WritableWorkbook workbook)
			throws RowsExceededException, WriteException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pageCatching(ConnectImpl connectImpl, WritableWorkbook workbook, int exlRow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFirstLevel(ConnectImpl ci, String website, String pagenum, String keyword, WritableSheet sheet,
			int exlRow) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDetailInfo(ConnectImpl ci, String urlstring, WritableSheet sheet, int exlRow, ListItems ietelement)
			throws RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInfoByStoreLink(ConnectImpl connectImpl, String pagenum, String storelink, WritableSheet sheet,
			int exlRow) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInfoByProductLink(ConnectImpl ci, String links, WritableSheet sheet, int exlRow)
			throws IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFisrtLevelLink(ConnectImpl ci, Document doc, WritableSheet sheet, String pagenum, int exlRow)
			throws RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
