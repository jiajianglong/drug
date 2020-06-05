package com.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bean.Rxperson;

@Repository
public interface RxpersonMapper {
		void AddRxperson(Rxperson Rxp);
		List<Rxperson> GetByTime(String time);

		List<String> GetequalsTime();
		List<String> GetDateTime(String time);
		void UpdateRxp(Rxperson rx);
		void DelRxp(String time);
}
