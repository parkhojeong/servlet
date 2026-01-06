package hello.servlet.web.frontcontrollerimpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet("/my-front-controller/v1/*")
public class FrontController extends HttpServlet {
    ViewResolver viewResolver = new ViewResolver();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Model model = new Model();
        String viewFileName = new ControllerManager()
                .process(req.getRequestURI(), model, req.getParameterMap());

        viewResolver.resolve(req, resp, model, viewFileName);
    }

    static class ControllerManager {

        private final Map<String, Object> servletMap;

        public ControllerManager() {
            servletMap = getServletMap();
        }

        private Map<String, Object> getServletMap() {
            return Map.of(
                    "/front-controller/v1/members/new-form", new MemberFormController(),
                    "/front-controller/v1/members/save", new MemberSaveController(),
                    "/front-controller/v1/members", new MemberListController());
        }

        public String process(String requestURI, Model model, Map<String, String[]> parameterMap) {
            Object servlet = servletMap.getOrDefault( requestURI, null);
            System.out.println("servlet = " + servlet);
            if(servlet == null){
                return "";
            }

            try {
                Method method = servlet.getClass().getDeclaredMethod("service");
                Object invoke = method.invoke(servlet);
                if(invoke instanceof String) {
                    return (String) invoke;
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                System.out.println("e = " + e);
            }

            try {
                Method method = servlet.getClass().getDeclaredMethod("service", Model.class);
                Object invoke = method.invoke(servlet, model);
                if(invoke instanceof String) {
                    return (String) invoke;
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                System.out.println("e = " + e);
            }


            try {
                Method method = servlet.getClass().getDeclaredMethod("service", Model.class, Map.class);
                Object invoke = method.invoke(servlet, model, parameterMap);
                if(invoke instanceof String) {
                    return (String) invoke;
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                System.out.println("e = " + e);
            }

            return null;
        }

    }

    static class ViewResolver {
        private void resolve(HttpServletRequest req, HttpServletResponse resp, Model model, String viewFileName) throws ServletException, IOException {
            setModel(req, model);
            forward(req, resp, viewFileName);
        }

        private void setModel(HttpServletRequest req, Model model) {
            model.getAttributes().forEach(req::setAttribute);
        }

        private void forward(HttpServletRequest req, HttpServletResponse resp, String viewFileName) throws ServletException, IOException {
            RequestDispatcher dispatcher = req.getRequestDispatcher(getViewPath(viewFileName));
            dispatcher.forward(req, resp);
        }

        private static String getViewPath(String viewFileName) {
            return "/WEB-INF/views/" + viewFileName + ".jsp";
        }
    }

}
