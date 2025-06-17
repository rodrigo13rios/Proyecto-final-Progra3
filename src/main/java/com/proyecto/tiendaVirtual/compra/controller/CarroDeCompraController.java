package com.proyecto.tiendaVirtual.compra.controller;

import com.proyecto.tiendaVirtual.compra.model.CarroDeCompras;
import com.proyecto.tiendaVirtual.compra.service.carritodecompra.CarroDeCompraService;
import com.proyecto.tiendaVirtual.configuration.MyUserDetails;
import com.proyecto.tiendaVirtual.utils.ApiResponse;
import com.proyecto.tiendaVirtual.utils.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/shopingList")
public class CarroDeCompraController {

    @Autowired
    CarroDeCompraService service;


    @GetMapping("/get")
    public ResponseEntity<ApiResponse> getListByCliente(){
        MyUserDetails userDetails=(MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nickName= userDetails.getNickname();
        List<CarroDeCompras> list=service.getCarroByCliente_nickName(nickName);
        if (list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ApiResponse<List<CarroDeCompras>>carroApiResponse=new ApiResponse<>(list);
        log.info("CarroDeCompras::getListByCliente response{}", ValueMapper.jsonAsString(carroApiResponse));
        return new ResponseEntity<>(carroApiResponse, HttpStatus.OK);
    }

    @GetMapping("/count/{id_cliente}")
    public ResponseEntity<ApiResponse> countItemsCliente(@PathVariable Long id_cliente){
        Long cantidad= service.getCountByCliente_Id(id_cliente);
       ApiResponse<Long>cantidadApiResponse=new ApiResponse<>(cantidad);
       log.info("CarroDeCompraController::countItemsCliente respose{}",ValueMapper.jsonAsString(cantidadApiResponse));
       return new ResponseEntity<>(cantidadApiResponse,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addJuegoAlCarro(@RequestBody @Valid CarroDeCompras carroDeCompras, BindingResult bindingResult){
        log.info("CarroDeComprasController::addProductToCart petición: {}", ValueMapper.jsonAsString(carroDeCompras));

        if (bindingResult.hasErrors()){

            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            log.warn("CarroDeComprasController::addProductToCart errores de validación: {}", errorMessage);
            ApiResponse<String>error=new ApiResponse<>(errorMessage);
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        service.addJuego(carroDeCompras);
        ApiResponse<CarroDeCompras> apiResponse=new ApiResponse<>(carroDeCompras);
        return new ResponseEntity(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/clean/{id_juego}")
    ResponseEntity<ApiResponse> removeItem(@PathVariable Long id_item){
        service.removeJuego_Id(id_item);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
