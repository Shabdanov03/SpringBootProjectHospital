package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Department;
import peaksoft.service.DepartmentService;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentApi {

    private final  DepartmentService departmentService;

    @GetMapping("/{id}")
    public String getAllDepartments(Model model,@PathVariable("id")Long id) {
        model.addAttribute("departments", departmentService.getAllDepartments(id));
        model.addAttribute("hospitalId",id);
        return "department/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(Model model, @PathVariable("hospitalId")Long id) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("hospitalId",id);
        return "department/newDepartment";
    }

    @PostMapping("/{hospitalId}/save")
    public String saveDepartment(@ModelAttribute("newDepartment")Department department,@PathVariable("hospitalId")Long id){
    departmentService.saveDepartment(department,id);
    return "redirect:/departments/"+id;
    }

    @GetMapping("/{hospitalId}/{departmentId}/delete")
    public String delete(@PathVariable("hospitalId")Long hospitalId,@PathVariable("departmentId")Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return "redirect:/departments/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{departmentId}/edit")
    public String edit(Model model,@PathVariable("departmentId")Long id,@PathVariable("hospitalId")Long hospitalId){
        model.addAttribute(departmentService.findByDepartmentId(id));
        model.addAttribute("hospitalId",hospitalId);
        return "department/edit";
    }

    @PostMapping("/{hospitalId}/{departmentId}/update")
        public String update (@PathVariable("hospitalId")Long hospitalId,@PathVariable("departmentId")Long id,@ModelAttribute("department")Department department){
        departmentService.updateDepartment(id,department);
        return "redirect:/departments/"+hospitalId;
    }

}
