package com.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bean.Rxdrug;

@Repository
public interface RxdrugMapper {
		void AddRxdrug(Rxdrug rxd);
		void DelRxdrug(String time);
		List<Rxdrug> GetIt(String time);
		Integer CountNullRx();

		List<String> GetAllOverTime();

		List<String> GetSomeDrugTime(String drug);
}
