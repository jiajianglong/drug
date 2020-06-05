package com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bean.Drug;


@Repository
public interface DrugMapper {
		List<Drug> QueryByNameCS(@Param("drugname")String drugname,@Param("changshang")String changshang);
		void AddDrug(Drug drug);
		void DeleteDrug(@Param("drugname")String drugname,@Param("changshang")String changshang);
		List<Drug> GetAllDrug();
		List<Drug> QueryByTiao(String tiaoxingma);
		List<Drug> QueryByName(String name);
		void Update(@Param("olddn")String olddn,@Param("oldcs")String oldcs,@Param("dd")Drug drug);
}
