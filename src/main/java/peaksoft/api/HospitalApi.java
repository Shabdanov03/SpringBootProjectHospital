package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Hospital;
import peaksoft.service.HospitalService;

/**
 * Shabdanov Ilim
 **/
@Controller
@RequestMapping("/hospitals")
@RequiredArgsConstructor

public class HospitalApi {

    private final HospitalService hospitalService;

    @GetMapping
    public String getAllHospitals(Model model,@RequestParam(name = "keyWord",required = false) String keyWord) {
        model.addAttribute("keyWord",keyWord);
        model.addAttribute("hospitals",hospitalService.getAllHospitals(keyWord));
        return "hospital/mainPage";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("newHospital", new Hospital());
        return "hospital/newHospital";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("newHospital") @Valid Hospital hospital,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/newHospital";
        }
        hospitalService.saveHospital(hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("{hospitalId}/delete")
    public String deleteHospital(@PathVariable("hospitalId") Long id) {
        hospitalService.deleteHospital(id);
        return "redirect:/hospitals";
    }

    @GetMapping("/{hospitalId}/edit")
    public String edit(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("hospital", hospitalService.findByHospitalId(hospitalId));
        return "hospital/edit";
    }

    @PostMapping("/{hospitalId}/update")
    public String update(@PathVariable("hospitalId") Long id, @ModelAttribute("hospital") @Valid Hospital hospital,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/edit";
        }
        hospitalService.updateHospital(id, hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("/{hospitalId}")
    public String findById(Model model, @PathVariable("hospitalId") Long id) {
        model.addAttribute("hospital", hospitalService.findByHospitalId(id));
        model.addAttribute("doctors", hospitalService.findByHospitalId(id).getDoctors().size());
        model.addAttribute("patients", hospitalService.findByHospitalId(id).getPatients().size());
        return "hospital/profile";
    }


}
