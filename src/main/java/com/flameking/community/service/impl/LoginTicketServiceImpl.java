package com.flameking.community.service.impl;

import com.flameking.community.entity.LoginTicket;
import com.flameking.community.entity.LoginTicketExample;
import com.flameking.community.mapper.LoginTicketMapper;
import com.flameking.community.service.LoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginTicketServiceImpl implements LoginTicketService {
  @Autowired
  private LoginTicketMapper loginTicketMapper;
  @Override
  public void logout(String ticket) {
    loginTicketMapper.updateStatusByTicket(1, ticket);
  }

  @Override
  public List<LoginTicket> findLoginTicketByTicket(String ticket) {
    LoginTicketExample loginTicketExample = new LoginTicketExample();
    loginTicketExample.createCriteria().andTicketEqualTo(ticket);
    return loginTicketMapper.selectByExample(loginTicketExample);
  }
}
