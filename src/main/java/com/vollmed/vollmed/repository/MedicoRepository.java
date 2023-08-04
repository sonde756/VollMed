package com.vollmed.vollmed.repository;

import com.vollmed.vollmed.enumeration.Especialidade;
import com.vollmed.vollmed.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);



    @Query("""
            SELECT m FROM medico m
            WHERE m.ativo = true
            AND m.especialidade = :especialidade
            AND m.id NOT IN (SELECT c.medico.id FROM consulta c WHERE c.data = :data)
            ORDER BY FUNCTION('RANDOM')
            """)
    Medico escolherMedicoAleatoriaLivreNaData(Especialidade especialidade, LocalDateTime data);


    @Query("""
            SELECT m.ativo
            FROM medico m
            WHERE m.id = :id
                        
                        """)
    Boolean findByAtivoTrue(Long id);
}
