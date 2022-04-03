package com.example.computerConfigurator.controller;

import com.example.computerConfigurator.blocks.*;
import com.example.computerConfigurator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    HddRepository hddRepository;
    @Autowired
    GpuRepository gpuRepository;
    @Autowired
    CaseBlockRepository caseBlockRepository;

    @GetMapping
    public String getListElements(@RequestParam(defaultValue = "") String socket,
                                  @RequestParam(defaultValue = "") String ramType,
                                  @RequestParam(defaultValue = "") String hddType,
                                  @RequestParam(defaultValue = "") String caseFormFactor,
                                  @RequestParam(defaultValue = "0") int cpuId,
                                  @RequestParam(defaultValue = "0") int ramId,
                                  @RequestParam(defaultValue = "0") int mbId,
                                  @RequestParam(defaultValue = "0") int hddId,
                                  @RequestParam(defaultValue = "0") int gpuId,
                                  @RequestParam(defaultValue = "0") int caseBlockId,
                                  Model model) {
        List<Integer> priceList = new ArrayList<>();
        cpuRepository.findById(cpuId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        ramRepository.findById(ramId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        mbRepository.findById(mbId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        hddRepository.findById(hddId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        gpuRepository.findById(gpuId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        caseBlockRepository.findById(caseBlockId).ifPresent(c -> priceList.add(c.getManufacturer().getPrice()));
        List<Cpu> cpuList = StreamSupport
                .stream(cpuRepository.findAll().spliterator(), false)
                .filter(cpu -> cpu.getCpuSocket().name().contains(socket))
                .filter(cpu -> cpuId == 0 || cpu.getId() == cpuId)
                .collect(Collectors.toList());
        List<MotherBoard> mbList = StreamSupport
                .stream(mbRepository.findAll().spliterator(), false)
                .filter(mb -> mb.getCpuSocket().name().contains(socket))
                .filter(mb -> mb.getRamType().name().contains(ramType))
                .filter(mb -> hddType.startsWith(mb.getHddType().name()) || mb.getHddType().name().contains(hddType))
                .filter(mb -> mb.getCaseFormFactor().name().contains(caseFormFactor))
                .filter(mb -> mbId == 0 || mb.getId() == mbId)
                .collect(Collectors.toList());
        List<Ram> ramList = StreamSupport
                .stream(ramRepository.findAll().spliterator(), false)
                .filter(ram -> ram.getRamType().name().contains(ramType))
                .filter(r -> ramId == 0|| r.getId() == ramId)
                .collect(Collectors.toList());
        List<Hdd> hddList = StreamSupport
                .stream(hddRepository.findAll().spliterator(), false)
                .filter(hdd -> hdd.getHddType().name().contains(hddType))
                .filter(hdd -> hddId == 0 || hdd.getId() == hddId)
                .collect(Collectors.toList());
        List<VideoCard> gpuList = StreamSupport
                .stream(gpuRepository.findAll().spliterator(), false)
                .filter(videoCard -> gpuId == 0 || videoCard.getId() == gpuId)
                .collect(Collectors.toList());
        List<CaseBlock> caseList = StreamSupport
                .stream(caseBlockRepository.findAll().spliterator(), false)
                .filter(caseBlock -> caseBlockId == 0 || caseBlock.getId() == caseBlockId)
                .filter(caseBlock -> caseBlock.getCaseFormFactor().name().contains(caseFormFactor))
                .collect(Collectors.toList());
        model.addAttribute("cpuList", cpuList);
        model.addAttribute("mbList", mbList);
        model.addAttribute("ramList", ramList);
        model.addAttribute("hddList", hddList);
        model.addAttribute("gpuList", gpuList);
        model.addAttribute("caseList", caseList);
        model.addAttribute("socket", socket);
        model.addAttribute("ramType", ramType);
        model.addAttribute("hddType", hddType);
        model.addAttribute("cpuId", cpuId);
        model.addAttribute("ramId", ramId);
        model.addAttribute("mbId", mbId);
        model.addAttribute("hddId", hddId);
        model.addAttribute("gpuId", gpuId);
        model.addAttribute("caseBlockId", caseBlockId);
        model.addAttribute("sumOrder", priceList.stream().mapToInt(Integer::valueOf).sum());
        return "/filter";
    }
}
