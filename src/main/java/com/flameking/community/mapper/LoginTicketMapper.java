package com.flameking.community.mapper;

import com.flameking.community.entity.LoginTicket;
import com.flameking.community.entity.LoginTicketExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LoginTicketMapper {
  int countByExample(LoginTicketExample example);

  int deleteByExample(LoginTicketExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(LoginTicket record);

  int insertSelective(LoginTicket record);

  List<LoginTicket> selectByExample(LoginTicketExample example);

  LoginTicket selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

  int updateByExample(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

  int updateByPrimaryKeySelective(LoginTicket record);

  int updateByPrimaryKey(LoginTicket record);

  void updateStatusByTicket(@Param("status") int status, @Param("ticket") String ticket);
}