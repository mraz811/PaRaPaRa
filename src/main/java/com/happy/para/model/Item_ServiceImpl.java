package com.happy.para.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.para.dto.ItemDto;

@Service
public class Item_ServiceImpl implements Item_IService {
	
	@Autowired
	private Item_IDao item_IDao;
	
	private Logger logger = LoggerFactory.getLogger(Item_ServiceImpl.class);
	
	@Override
	public List<ItemDto> itemList() {
		logger.info("select ItemList");
		return item_IDao.itemList();
	}

	@Override
	public boolean itemInsert(ItemDto dto) {
		logger.info("insert Item : {}", dto);
		return item_IDao.itemInsert(dto);
	}

	@Override
	public boolean itemModify(ItemDto dto) {
		logger.info("modify Item : {}", dto);
		return item_IDao.itemModify(dto);
	}

	@Override
	public boolean itemDelete(String item_seq) {
		logger.info("delete Item : {}",item_seq);
		return item_IDao.itemDelete(item_seq);
	}

	@Override
	public ItemDto itemDetail(String item_seq) {
		logger.info("detail Item service : {}", item_seq);
		return item_IDao.itemDetail(item_seq);
	}

	@Override
	public int itemNameChk(String item_name) {
		logger.info("select Item Name service : {}", item_name);
		return item_IDao.itemNameChk(item_name);
	}

	@Override
	public List<ItemDto> itemSearchList(String item_name) {
		logger.info("search Item List Service : {}", item_name);
		return item_IDao.itemSearchList(item_name);
	}
	
	
	
}
