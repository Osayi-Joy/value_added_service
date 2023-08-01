package com.digicore.billent.backoffice.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
 * @author Oluwatobi Ogunwuyi
 * @createdOn Aug-01(Tue)-2023
 */
@Slf4j
@Component
public class Dummy {

 public void doNoting(){
  log.trace("doing nothing");
 }
}
