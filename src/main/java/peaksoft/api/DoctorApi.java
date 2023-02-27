package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor

public class DoctorApi {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;


    @GetMapping("/{id}")
    public String getAllDoctors(Model model,@PathVariable("id")Long id) {
        model.addAttribute("doctors", doctorService.getAllDoctors(id));
        model.addAttribute("hospitalId",id);
        return "doctor/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable("hospitalId") Long id, Model model) {
        model.addAttribute("hospitalId", id);
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentService.getAllDepartments(id));
        return "doctor/newDoctor";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable("hospitalId")Long id, @ModelAttribute("doctor")@Valid Doctor doctor,
                       BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.getAllDepartments(id));
            return "doctor/newDoctor";
        }
        try{
            doctorService.saveDoctor(doctor,id);
            return "redirect:/doctors/"+id;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("hospitalId", id);
            model.addAttribute("departments", departmentService.getAllDepartments(id));
            model.addAttribute("Email","This email already exists.");
            return "doctor/newDoctor";
        }

    }

    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String delete(@PathVariable("doctorId")Long id,@PathVariable("hospitalId")Long hospitalId){
        doctorService.deleteDoctor(id);
        return "redirect:/doctors/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{doctorId}/edit")
    public String edit(Model model,@PathVariable("hospitalId")Long hospitalId,@PathVariable("doctorId")Long doctorId){
        model.addAttribute(doctorService.findByDoctorId(doctorId));
        model.addAttribute("hospitalId",hospitalId);
        return "doctor/edit";
    }
    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@PathVariable("hospitalId")Long hospitalId,@PathVariable("doctorId")Long id,Model model,@ModelAttribute("doctor") @Valid Doctor doctor,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "doctor/edit";
        }
        try {
            doctorService.updateDoctor(id, doctor);
            return "redirect:/doctors/" + hospitalId;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("hospitalId",hospitalId);
            model.addAttribute("Email","This email already exists.");
            return "doctor/edit";
        }
    }



}
