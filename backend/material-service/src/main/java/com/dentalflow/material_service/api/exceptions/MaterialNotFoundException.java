package com.dentalflow.material_service.api.exceptions;

public class MaterialNotFoundException extends RuntimeException {

    public MaterialNotFoundException(Integer id) {
        super("Material no encontrado, id: " + id);
    }
}
