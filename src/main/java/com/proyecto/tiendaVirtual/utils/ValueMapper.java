package com.proyecto.tiendaVirtual.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ValueMapper {


    //Crea una instancia de ObjetMapper para devolver un json a cadena de texto.
    public static String jsonAsString(Object objeto){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());// para un buen manejo de fechas.
            return objectMapper.writeValueAsString(objeto);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }


}
