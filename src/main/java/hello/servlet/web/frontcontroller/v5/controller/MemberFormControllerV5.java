package hello.servlet.web.frontcontroller.v5.controller;

import hello.servlet.web.frontcontroller.v5.ControllerV5;

import java.util.Map;

public class MemberFormControllerV5 implements ControllerV5 {
    @Override
    public String  process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
