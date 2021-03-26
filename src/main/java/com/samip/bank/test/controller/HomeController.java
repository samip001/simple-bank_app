package com.samip.bank.test.controller;

import com.samip.bank.test.dao.EmployeeDAO;
import com.samip.bank.test.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private EmployeeDAO employeeDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public HomeController(EmployeeDAO employeeDAO,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.employeeDAO = employeeDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("titlename","Banking App");
        return "index";
    }

    @GetMapping("/add")
    public String addUser(Model model){
        model.addAttribute("employee",new Employee());
        model.addAttribute("titlename","Add New Employee");
        return "add_user";
    }

    @PostMapping("/add")
    public String registerUser(Employee employee,Model model){

        Employee saveEmployee = employeeDAO.checkEmailExit(employee.getEmail());
        if(saveEmployee != null ){
            model.addAttribute("message","Email Already Exits");
            model.addAttribute("employee",employee);
            model.addAttribute("titlename","Registration Error");
            return "add_user";
        }

        String password = employee.getPassword();
        log.info(password);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        log.warn(encodedPassword);

        employee.setPassword(encodedPassword);

        employeeDAO.insertEmployee(employee);

        model.addAttribute("message","Registration Success");
        model.addAttribute("employee",new Employee());
        model.addAttribute("titlename","Add New Employee");
        return "add_user";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<Employee> employees = employeeDAO.getAllEmployees();
        chekListEmptyOrNot(model, employees);
        return "user_list";
    }

    @GetMapping("/user/{userid}")
    public String userAction(@PathVariable("userid")Long id, @RequestParam("type")String type,Model model){
        if(type.equals("edit")){
            Optional<Employee> employee = employeeDAO.findEmployee(id);
            if(employee.isPresent()){
                Employee saveEmployee = employee.get();
                model.addAttribute("titlename","Update Employee: "+saveEmployee.getFirstName());
                model.addAttribute("employee",saveEmployee);
                return "update_user";
            }
        }else if(type.equals("delete")){
            if(employeeDAO.removeEmployee(id)) return "redirect:/users";
        }

        return "redirect:/";
    }

    @PostMapping("/updateuser")
    public String updateUser(Employee employee){
        Optional<Employee> optionalEmployee = employeeDAO.findEmployee(employee.getId());
        if(optionalEmployee.isPresent()){
            Employee storeEmployee = optionalEmployee.get();
            storeEmployee.setFirstName(employee.getFirstName());
            storeEmployee.setLastName(employee.getLastName());
            storeEmployee.setMiddleName(employee.getMiddleName());
            storeEmployee.setGender(employee.getGender());
            storeEmployee.setPhone(employee.getPhone());

            employeeDAO.insertEmployee(storeEmployee);

            return "redirect:/users";
        }

        return "redirect:/";
    }

    private void chekListEmptyOrNot(Model model, List<Employee> employees) {
        if(employees.size() < 1){
            model.addAttribute("titlename","User not Found");
            model.addAttribute("found",false);
        }else{
            model.addAttribute("titlename","Users List");
            model.addAttribute("employees",employees);
            model.addAttribute("found",true);
        }
    }
}
