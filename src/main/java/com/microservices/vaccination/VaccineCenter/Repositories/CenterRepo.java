package com.microservices.vaccination.VaccineCenter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.vaccination.VaccineCenter.Entities.VaccineCenter;

public interface CenterRepo extends JpaRepository<VaccineCenter, Integer> {

}
