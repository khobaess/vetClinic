package com.vetclinic.repository.specification;

import com.vetclinic.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PatientSpecifications {
    
    public static Specification<Patient> searchPatients(String petName, String ownerName, String species) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            List<Predicate> namePredicates = new ArrayList<>();
            
            if (petName != null && !petName.trim().isEmpty()) {
                namePredicates.add(cb.like(cb.lower(root.get("petName")), 
                    "%" + petName.toLowerCase() + "%"));
            }
            
            if (ownerName != null && !ownerName.trim().isEmpty()) {
                namePredicates.add(cb.like(cb.lower(root.get("ownerName")), 
                    "%" + ownerName.toLowerCase() + "%"));
            }

            if (!namePredicates.isEmpty()) {
                predicates.add(cb.or(namePredicates.toArray(new Predicate[0])));
            }

            if (species != null && !species.trim().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("species")), 
                    species.toLowerCase()));
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
