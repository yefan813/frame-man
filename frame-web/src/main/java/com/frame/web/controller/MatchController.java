package com.frame.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/match")
public class MatchController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);
	
}
