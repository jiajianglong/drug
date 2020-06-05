package com.service;



import java.util.List;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Drug;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.DrugMapper;
import com.utils.Boolean_tiaoxingma;


@Service
public class DruginfoService {
	@Autowired
	DrugMapper drug;
	public PageInfo<Drug> QueryDrugService(Integer pn,String qd){
		if(qd==""||qd==null){
			PageHelper.startPage(pn, 8);
			List<Drug> re = drug.GetAllDrug();
			PageInfo<Drug> page_1 = new PageInfo<Drug>(re,5);
			return page_1;
		}else{
			if(Boolean_tiaoxingma.CheckParam(qd)){
				PageHelper.startPage(pn, 8);
				List<Drug> re2 = drug.QueryByTiao(qd);
				PageInfo<Drug> page_2 = new PageInfo<Drug>(re2,5);
				return page_2;
			}else{
				PageHelper.startPage(pn, 8);
				List<Drug> re3 = drug.QueryByName(qd);
				PageInfo<Drug> page_3 = new PageInfo<Drug>(re3,5);
				return page_3;
			}
		}
		
	}
	public boolean UpdateDrugService(String olddn, String oldcs, Drug updrug) {
		/*drug.DeleteDrug(olddn, oldcs);
		drug.AddDrug(updrug);*/
		if(olddn.equals(updrug.getDrugname())&&oldcs.equals(updrug.getChangshang())){
			drug.Update(olddn, oldcs, updrug);
			return true;
		}else{
			List<Drug> check = drug.QueryByNameCS(updrug.getDrugname(), updrug.getChangshang());
			if(check.size()>0){
				return false;
			}else{
				drug.Update(olddn, oldcs, updrug);
				return true;
			}
		}
		
	}
	//add
	public boolean AddService(Drug adddrug) {
		List<Drug> check = drug.QueryByNameCS(adddrug.getDrugname(), adddrug.getChangshang());
		if(check.size()>0){
			return false;
		}else{
			drug.AddDrug(adddrug);
			return true;
		}
	}
	public void DeleteService(String drugname, String changshang) {
		drug.DeleteDrug(drugname, changshang);
	}

	public List<Drug> QueryDrugService(String qd) {
		if(qd==""||qd==null){
			List<Drug> re = drug.GetAllDrug();
			return re;
		}else{
			if(Boolean_tiaoxingma.CheckParam(qd)){
				List<Drug> re2 = drug.QueryByTiao(qd);
				return re2;
			}else{
				List<Drug> re3 = drug.QueryByName(qd);
				return re3;
			}
		}
	}
}
