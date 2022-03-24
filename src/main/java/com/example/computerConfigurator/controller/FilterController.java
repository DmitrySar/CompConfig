package com.example.computerConfigurator.controller;

import com.example.computerConfigurator.blocks.Cpu;
import com.example.computerConfigurator.blocks.MotherBoard;
import com.example.computerConfigurator.blocks.Ram;
import com.example.computerConfigurator.repository.CpuRepository;
import com.example.computerConfigurator.repository.MbRepository;
import com.example.computerConfigurator.repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("filter")
public class FilterController {
    @Autowired
    CpuRepository cpuRepository;
    @Autowired
    MbRepository mbRepository;
    @Autowired
    RamRepository ramRepository;

    @GetMapping
    public String getListElements(@RequestParam(defaultValue = "") String socket,
                                  @RequestParam(defaultValue = "") String ramType,
                                  @RequestParam(defaultValue = "0") int cpuId,
                                  Model model) {
        ArrayList<Integer> sum = new ArrayList<>();
        List<Cpu> cpuList = StreamSupport
                .stream(cpuRepository.findAll().spliterator(), false)
                .filter(cpu -> cpu.getCpuSocket().name().contains(socket))
                .filter(cpu -> cpuId == 0 || cpu.getId() == cpuId)
                .peek(p -> sum.add(p.getManufacturer().getPrice()))
                .collect(Collectors.toList());
        List<MotherBoard> mbList = StreamSupport
                .stream(mbRepository.findAll().spliterator(), false)
                .filter(mb -> mb.getCpuSocket().name().contains(socket))
                .filter(mb -> mb.getRamType().name().contains(ramType))
                .peek(p -> sum.add(p.getManufacturer().getPrice()))
                .collect(Collectors.toList());
        List<Ram> ramList = StreamSupport
                .stream(ramRepository.findAll().spliterator(), false)
                .filter(ram -> ram.getRamType().name().contains(ramType))
                .peek(p -> sum.add(p.getManufacturer().getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("mbList", mbList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("socket", socket);
        model.addAttribute("ramType", ramType);
        model.addAttribute("cpuId", cpuId);
        model.addAttribute("sumOrder", sum.stream().mapToInt(Integer::valueOf).sum());
        return "/filter";
    }
}
