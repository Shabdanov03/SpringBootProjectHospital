package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Doctor;
import peaksoft.repository.DoctorRepository;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/doctors")
@RequiredArgsConstructor

public class DoctorApi {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final DoctorRepository doctorRepository;


    @GetMapping("/{id}")
    public String getAllDoctors(Model model, @PathVariable("id") Long id) {
        model.addAttribute("doctors", doctorService.getAllDoctors(id));
        model.addAttribute("hospitalId", id);
        return "doctor/mainPage";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable("hospitalId") Long id, Model model) {
        model.addAttribute("hospitalId", id);
        model.addAttribute("doctor", new Doctor());
        return "doctor/newDoctor";
    }

    @PostMapping("/{hospitalId}/save")
    public String save(@PathVariable("hospitalId") Long id, @ModelAttribute("doctor") @Valid Doctor doctor,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "doctor/newDoctor";
        }
        try {
            doctorService.saveDoctor(doctor, id);
            return "redirect:/doctors/" + id;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("hospitalId", id);
            model.addAttribute("Email", "This email already exists.");
            return "doctor/newDoctor";
        }

    }

    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String delete(@PathVariable("doctorId") Long id, @PathVariable("hospitalId") Long hospitalId) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors/" + hospitalId;
    }

    @GetMapping("/{hospitalId}/{doctorId}/edit")
    public String edit(Model model, @PathVariable("hospitalId") Long hospitalId, @PathVariable("doctorId") Long doctorId) {
        model.addAttribute(doctorService.findByDoctorId(doctorId));
        model.addAttribute("hospitalId", hospitalId);
        return "doctor/edit";
    }

    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@PathVariable("hospitalId") Long hospitalId, @PathVariable("doctorId") Long id, Model model, @ModelAttribute("doctor") @Valid Doctor doctor,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "doctor/edit";
        }
        try {
            doctorService.updateDoctor(id, doctor);
            return "redirect:/doctors/" + hospitalId;
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("hospitalId", hospitalId);
            model.addAttribute("Email", "This email already exists.");
            return "doctor/edit";
        }
    }

    @GetMapping("/{hospitalId}/{doctorId}/assignPage")
    public String assignPage(@PathVariable("hospitalId") Long hospitalId,
                             @PathVariable Long doctorId, Model model) {
        model.addAttribute("hospitalId", hospitalId);
        model.addAttribute("doctor", doctorService.findByDoctorId(doctorId));
        model.addAttribute("departments", departmentService.getAllDepartmentsByHospitalIdAndDoctorId(doctorId, hospitalId));
        return "doctor/assignPage";
    }

    @PostMapping("/{hospitalId}/{doctorId}/assign")
    public String assign(@PathVariable Long hospitalId, @PathVariable Long doctorId, @RequestParam List<Long> departmentsId) {
        doctorService.assign(doctorId, departmentsId);
        return "redirect:/doctors/" + hospitalId;
    }

}
