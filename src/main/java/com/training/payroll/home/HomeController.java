package com.training.payroll.home;

import com.training.payroll.employee.EmployeeRepository;
import com.training.payroll.order.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    HomeController(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository    = orderRepository;
    }

    @GetMapping({"/index", "/"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("list-employees");
        mav.addObject("employees", this.employeeRepository.findAll());
        mav.addObject("orders", this.orderRepository.findAll());
        return mav;
    }

    @GetMapping({"/error"})
    public ModelAndView error(){
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }
}
