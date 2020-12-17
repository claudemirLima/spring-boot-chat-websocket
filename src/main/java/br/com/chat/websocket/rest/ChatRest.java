package br.com.chat.websocket.rest;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatRest {
	
	/**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
    	ModelAndView model = new ModelAndView("/chat");
    	model.addObject("username",username);
    	return model;
    }

}
