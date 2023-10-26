package com.training.payroll.home;

import com.training.payroll.employee.Employee;
import com.training.payroll.employee.EmployeeRepository;
import com.training.payroll.order.OrderRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    HomeController(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping({"/index", "/"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("list-employees");
        mav.addObject("employees", this.employeeRepository.findAll());
        mav.addObject("orders", this.orderRepository.findAll());
        return mav;
    }

    @GetMapping("/add-employee")
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("add-employee");
        mav.addObject("employee", new Employee());
        return mav;
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView add(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("edit-employee");
        Optional<Employee> employee = employeeRepository.findById(id);
        mav.addObject("employee", employee);
        return mav;
    }

    @PostMapping("/add-employee")
    public ModelAndView addEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return index();
    }

    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute Employee employee) {
        employeeRepository.findById(employee.getId()).map(eachEmployee -> {
            employee.setName(employee.getName());
            employee.setRole(employee.getRole());
            return employeeRepository.save(employee);
        });

        return index();
    }

    @DeleteMapping("/delete-employee/{id}")
    public ModelAndView deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return index();
    }

    @GetMapping({"/error"})
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }
}
