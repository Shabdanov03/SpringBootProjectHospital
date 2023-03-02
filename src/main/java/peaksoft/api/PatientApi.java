package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Patient;
import peaksoft.service.PatientService;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/patients")
@RequiredArgsConstructor

public class PatientApi {
    private final PatientService patientService;


    @GetMapping("/{id}")
    public String getAllPatients(Model model, @PathVariable("id") Long id) {
        model.addAttribute("patients",patientService.getAllPatients(id));
        model.addAttribute("hospitalId", id);
        return "patient/mainPage";
    }


    @GetMapping("/{hospitalId}/new")
    public String create(Model model, @PathVariable("hospitalId")Long id) {
        model.addAttribute("newPatient", new Patient());
        model.addAttribute("hospitalId",id);
        return "patient/newPatient";
    }

    @PostMapping("/{hospitalId}/save")
    public String savePatient(@PathVariable("hospitalId")Long id, Model model, @ModelAttribute("newPatient") @Valid Patient patient,
                              BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "patient/newPatient";
        }
        try{
            patientService.savePatient(patient,id);
            return "redirect:/patients/"+id;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("hospitalId",id);
            model.addAttribute("Email","This email already exists.");
            return "patient/newPatient";
        }
    }

    @GetMapping("/{hospitalId}/{patientId}/delete")
    public String delete(@PathVariable("hospitalId")Long hospitalId,@PathVariable("patientId")Long id){
        patientService.deletePatient(id);
        return "redirect:/patients/"+hospitalId;
    }

    @GetMapping("/{hospitalId}/{patientId}/edit")
    public String edit(Model model,@PathVariable("patientId")Long id,@PathVariable("hospitalId")Long hospitalId){
        model.addAttribute("patient",patientService.findByPatientId(id));
        model.addAttribute("hospitalId",hospitalId);
        return "patient/edit";
    }

    @PostMapping("/{hospitalId}/{patientId}/update")
    public String update (@PathVariable("hospitalId")Long hospitalId,@PathVariable("patientId")Long id,Model model,
                          @ModelAttribute("patient")@Valid Patient patient,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "patient/edit";
        }
        try {
            patientService.updatePatient(id,patient);
            return "redirect:/patients/"+hospitalId;
        }catch (DataIntegrityViolationException e){
            model.addAttribute("hospitalId",hospitalId);
            model.addAttribute("Email","This email already exists.");
            return "patient/edit";
        }

    }

}
