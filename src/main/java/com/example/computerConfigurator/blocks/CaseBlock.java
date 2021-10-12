package com.example.computerConfigurator.blocks;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@SuperBuilder(toBuilder = true)
@Entity
@NoArgsConstructor
public class CaseBlock extends Manufacturer {
    private CaseFormFactor formFactor;
}