package com.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Drug;
import com.bean.Gonghuoshang;
import com.bean.Jinhuo;
import com.bean.Json;
import com.bean.Msg;
import com.bean.Store;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.DrugMapper;
import com.mapper.GonghuoshangMapper;
import com.mapper.JinhuoMapper;
import com.mapper.StoreMapper;
import com.utils.Boolean_tiaoxingma;
import com.utils.StringPro;


@Service
public class JinhuoService {
		@Autowired
		GonghuoshangMapper ghs;
		
		@Autowired
		JinhuoMapper jin;
		
		@Autowired
		StoreMapper sm; 
		
		@Autowired
		DrugMapper drug;

		public Msg AddGhsService(String it){
			if(ghs.QueryGHS(it).isEmpty()){
				ghs.AddGHS(it);
				Msg re = Msg.success().add("success","success");
				return re;
			}else{
				Msg re2 = Msg.fail().add("fail","���ݿ����Ѵ��ڴι�������Ϣ");
				return re2;
			}
		}

		public List<Gonghuoshang> GetAllGhsService(){
			return ghs.GeyAllGHS();
		}

		public void DelGhsAndRecordsService(String it){
			ghs.DelGHS(it);
			jin.DelJinhuoRecords(it);
			}
		/**
		 * del check
		 * @param it
		 */
		public Msg DelGhsCheckService(String it){
			int checkcount = jin.CountGhsRecords(it);
			if(checkcount==0){
				ghs.DelGHS(it);
				Msg re = Msg.success().add("success", "delete success!!!");
				return re;
			}else{
				Json j = new Json(it,null,null,null,null,checkcount);
				Msg re2 = Msg.fail().add("grcount",j);
				return re2;
			}
		}
		
		public List<Jinhuo> GetAllJinhuoService(){
			return jin.GetAllJinhuo();
		}
		public void AddJinhuoService(Jinhuo j){
			jin.AddJinhuo(j);
		}
		public void DeleteJinhuoService(String drugname, String changshang,
				String pihao, String amount) {
			jin.DelJinhuo(drugname, changshang, pihao, amount);
		}

		public boolean CpshService() {
			List<Jinhuo> CheckJh = jin.GetAllJinhuo();
			if(CheckJh.isEmpty()){
				return false;
			}else{
				List<Store> checkstore = null;
				for (Jinhuo j : CheckJh) {
					jin.AddJinhuorecords(j);
					checkstore = sm.QueryByNCP(j.getDrugname(),j.getChangshang(),j.getPihao());
					if(checkstore.size()<=0){
						try{
						sm.AddStore(new Store(j.getDrugname(),j.getChangshang(),j.getBeginprice(),j.getPrice(),new SimpleDateFormat("yyyy-MM-dd").parse(j.getBegindate()),new SimpleDateFormat("yyyy-MM-dd").parse(j.getDate()), j.getPihao(),j.getBeizhu(), j.getLocation(), j.getAmount(), j.getUnit(), j.getGuige(), j.getTiaoxingma()));
						}catch(Exception e){
							
						}
					}else{
						String newcount = StringPro.add(checkstore.get(0).getCount(), j.getAmount());
						sm.UpdateStoreCount(newcount, j.getDrugname(), j.getChangshang(), j.getPihao());
					}
				}
				jin.DelTableJinhuo();
				return true;
			}
		}
		public List<Drug> ForSelectService(String qd) {
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
		public PageInfo<Jinhuo> QueryJFYService(Integer pn, String qd) {
			if(qd==""||qd==null){
				PageHelper.startPage(pn, 8);
				List<Jinhuo> re = jin.GAJRecords();
				PageInfo<Jinhuo> page_1 = new PageInfo<Jinhuo>(re,5);
				return page_1;
			}else{
					PageHelper.startPage(pn, 8);
					List<Jinhuo> re3 = jin.GJRGHS(qd);
					PageInfo<Jinhuo> page_3 = new PageInfo<Jinhuo>(re3,5);
					return page_3;
			}
		}
}
