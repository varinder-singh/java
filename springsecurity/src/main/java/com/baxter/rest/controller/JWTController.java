package com.baxter.rest.controller;

import com.baxter.model.JWTRequest;
import com.baxter.model.JWTResponse;
import com.baxter.service.BaxterUserDetailService;
import com.baxter.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JWTController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private BaxterUserDetailService baxterUserDetailService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloWorld(){
        return "This application is running";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity getToken(@RequestBody JWTRequest jwtReq) throws Exception{
        String username = jwtReq.getUsername();
        String password = jwtReq.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch(DisabledException e){
            throw new Exception("USER_DISABLED", e);
        } catch(BadCredentialsException e){
            throw new Exception("BAD_CREDENTIALS", e);
        }
        UserDetails userDetails = baxterUserDetailService.loadUserByUsername(username);
        String token = jwtUtil.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }
}
