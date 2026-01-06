package hello.servlet.web.frontcontroller.v5.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.ControllerV5;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private Map<String, Object> handlerMap = new HashMap<>();
    private List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        handlerMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        handlerMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = handlerMap.get(req.getRequestURI());
        if(handler == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter handlerAdapter = null;
        for (MyHandlerAdapter ha : handlerAdapters) {
            if(ha.supports(handler)){
                handlerAdapter = ha;
                break;
            }
        }
        if(handlerAdapter == null) {
            throw new IllegalArgumentException("handler adpater not found. handler = " + handler + "");
        }

        ModelView modelView = handlerAdapter.handle(req, resp, handler);

        MyView myView = viewResolver(modelView.getViewName());
        myView.render(modelView.getModel() ,req, resp);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static @NonNull Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames()
                .asIterator()
                .forEachRemaining(name -> paramMap.put(name, req.getParameter(name)));
        return paramMap;
    }
}
