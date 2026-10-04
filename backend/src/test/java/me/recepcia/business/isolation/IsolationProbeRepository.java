package me.recepcia.business.isolation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface IsolationProbeRepository extends JpaRepository<IsolationProbe, UUID> {

}
