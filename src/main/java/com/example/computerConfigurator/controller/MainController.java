package com.example.computerConfigurator.controller;

import com.example.computerConfigurator.blocks.*;
import com.example.computerConfigurator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addcomponents")
public class MainController {
    @Autowired
    private MbRepository mbRepository;
    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private RamRepository ramRepository;
    @Autowired
    private GpuRepository gpuRepository;
    @Autowired
    private HddRepository hddRepository;
    @Autowired
    private CaseBlockRepository caseBlockRepository;

    @GetMapping
    public String getAllComponent(Model model) {
        model.addAttribute("cpuList", cpuRepository.findAll());
        model.addAttribute("motherboards", mbRepository.findAll());
        model.addAttribute("ramList", ramRepository.findAll());
        model.addAttribute("gpuList", gpuRepository.findAll());
        model.addAttribute("hddList", hddRepository.findAll());
        model.addAttribute("gpuList", gpuRepository.findAll());
        model.addAttribute("caseList", caseBlockRepository.findAll());
        model.addAttribute("ramTypes", RamType.values());
        model.addAttribute("cpuSockets", CpuSocket.values());
        model.addAttribute("caseFormsFactor", CaseFormFactor.values());
        model.addAttribute("hddTypes", HddType.values());
        //model.addAttribute("caseTypes", CaseFormFactor.values());
        model.addAttribute("cpu",
                cpuRepository.findFirstByOrderByIdDesc().orElse(new Cpu()));
        model.addAttribute("mboard",
                mbRepository.findFirstByOrderByIdDesc().orElse(new MotherBoard()));
        model.addAttribute("ram", ramRepository.findFirstByOrderByIdDesc().orElse(new Ram()));
        model.addAttribute("hdd", hddRepository.findFirstByOrderByIdDesc().orElse(new Hdd()));
        model.addAttribute("caseBlock", caseBlockRepository.findFirstByOrderByIdDesc().orElse(new CaseBlock()));
        return "addcomponents";
    }

    @PostMapping("addCase")
    public String addCase(@ModelAttribute CaseBlock caseBlock) {
        caseBlockRepository.save(caseBlock);
        return "redirect:/addcomponents#case";
    }

    @GetMapping("delCase")
    public String dellCase(@RequestParam int id) {
        caseBlockRepository.delete(caseBlockRepository.findById(id).get());
        return "redirect:/addcomponents#case";
    }

    @PostMapping("addGpu")
    public String addGpu(@ModelAttribute VideoCard videoCard) {
        gpuRepository.save(videoCard);
        return "redirect:/addcomponents#gpu";
    }

    @GetMapping("delGpu")
    public String dellGpu(@RequestParam int id) {
        gpuRepository.delete(gpuRepository.findById(id).get());
        return "redirect:/addcomponents#gpu";
    }

    @PostMapping("addHdd")
    public String addHdd(@ModelAttribute Hdd hdd) {
        hddRepository.save(hdd);
        return "redirect:/addcomponents#hdd";
    }

    @GetMapping("delHdd")
    public String dellHdd(@RequestParam int id) {
        hddRepository.delete(hddRepository.findById(id).get());
        return "redirect:/addcomponents#hdd";
    }

    @PostMapping("addMb")
    public String addMb(@ModelAttribute MotherBoard mb) {
        mbRepository.save(mb);
        return "redirect:/addcomponents#motherboard";
    }

    @GetMapping("delMotherboard")
    public String delMotherboard(@RequestParam int id) {
        mbRepository.delete(mbRepository.findById(id).get());
        return "redirect:/addcomponents#motherboard";
    }

    @PostMapping("addCpu")
    public String addCpu(@ModelAttribute Cpu cpu) {
        cpuRepository.save(cpu);
        return "redirect:/addcomponents#cpu";
    }

    @PostMapping("addRam")
    public  String addRam(@ModelAttribute Ram ram) {
        ramRepository.save(ram);
        return "redirect:/addcomponents#ram";
    }

    @GetMapping("delRam")
    public String delRam(@RequestParam int id) {
        ramRepository.delete(ramRepository.findById(id).get());
        return "redirect:/addcomponents#ram";
    }
}
