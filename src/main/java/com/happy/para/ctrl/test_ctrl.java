package com.happy.para.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class test_ctrl {

	@RequestMapping(value="/test.do", method=RequestMethod.GET)
	public String test() {
		return "test";
	}
	
}
