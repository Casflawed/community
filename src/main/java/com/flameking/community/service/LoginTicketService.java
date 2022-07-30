package com.flameking.community.service;

import com.flameking.community.entity.LoginTicket;

import java.util.List;

public interface LoginTicketService {
  void logout(String ticket);

  List<LoginTicket> findLoginTicketByTicket(String ticket);
}
