package com.sopovs.moradanen.fan.service;

import java.util.List;

import com.sopovs.moradanen.fan.domain.Club;

public interface IDaoService {
	Club findClubByName(String name);

	List<Club> listAllClubs();
}
