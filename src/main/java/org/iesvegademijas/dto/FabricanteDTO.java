package org.iesvegademijas.dto;

import java.util.Optional;

import org.iesvegademijas.model.Fabricante;

public class FabricanteDTO {

	public Fabricante fab;
	public Optional<Integer> numProductos;
	
	public FabricanteDTO(Fabricante fab) {
		this.fab = fab;
	}

	public Optional<Integer> getNumProductos() {
		return numProductos;
	}

	public void setNumProductos(Optional<Integer> numProductos) {
		this.numProductos = numProductos;
	}
	
}
