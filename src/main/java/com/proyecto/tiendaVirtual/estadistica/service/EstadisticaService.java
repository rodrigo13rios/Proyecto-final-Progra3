package com.proyecto.tiendaVirtual.estadistica.service;

import com.proyecto.tiendaVirtual.compra.repository.CompraItemRepository;
import com.proyecto.tiendaVirtual.desarrolladora.model.Desarrolladora;
import com.proyecto.tiendaVirtual.estadistica.dto.CategoriaCountDTO;
import com.proyecto.tiendaVirtual.estadistica.dto.DesarrolladoraStatsDTO;
import com.proyecto.tiendaVirtual.estadistica.dto.SeriePointDTO;
import com.proyecto.tiendaVirtual.juego.repository.JuegoRepository;
import com.proyecto.tiendaVirtual.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstadisticaService {

    @Autowired
    private CompraItemRepository compraItemRepo;
    @Autowired
    private JuegoRepository juegoRepo;
    @Autowired
    private SecurityUtils securityUtils;

    @Transactional(readOnly = true)
    public DesarrolladoraStatsDTO getDashboard(int days) {
        Desarrolladora dev = securityUtils.getLoggedUser().getDesarrolladora();
        if (dev == null) throw new RuntimeException("El usuario no es desarrolladora");
        Long devId = dev.getId();

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusDays(days);

        long ventasTotales = compraItemRepo.ventasTotalesDev(devId);
        double ingresosTotales = round2(compraItemRepo.ingresosTotalesDev(devId));

        long ventasPeriodo = compraItemRepo.ventasPeriodoDev(devId, from, to);
        double ingresosPeriodo = round2(compraItemRepo.ingresosPeriodoDev(devId, from, to));

        LocalDate fromDay = from.toLocalDate();
        LocalDate toDay = to.toLocalDate();
        List<SeriePointDTO> ventasPorDia = fillDays(fromDay, toDay, compraItemRepo.ventasPorDia(devId, from, to), false);
        List<SeriePointDTO> ingresosPorDia = fillDays(fromDay, toDay, compraItemRepo.ingresosPorDia(devId, from, to), true);

        double precioPromedio = round2(juegoRepo.precioPromedioDev(devId));
        double precioMediana = round2(mediana(juegoRepo.preciosOrdenadosDev(devId)));

        List<CategoriaCountDTO> distribucion = juegoRepo.distribucionCategoriasDev(devId).stream()
                .map(row -> new CategoriaCountDTO(String.valueOf(row[0]), ((Number) row[1]).longValue()))
                .toList();

        long compradoresUnicos = compraItemRepo.compradoresUnicosDev(devId);
        long compradoresRecurrentes = compraItemRepo.compradoresRecurrentesDev(devId);
        double tasaRetencion = compradoresUnicos == 0 ? 0.0 : (compradoresRecurrentes * 1.0 / compradoresUnicos);

        return new DesarrolladoraStatsDTO(
                ventasTotales, ingresosTotales,
                ventasPeriodo, ingresosPeriodo,
                ventasPorDia, ingresosPorDia,
                precioPromedio, precioMediana,
                distribucion,
                compradoresUnicos, compradoresRecurrentes, round4(tasaRetencion)
        );
    }

    private static double mediana(List<Double> xs) {
        if (xs == null || xs.isEmpty()) return 0.0;
        int n = xs.size();
        if (n % 2 == 1) return xs.get(n / 2);
        return (xs.get(n/2 - 1) + xs.get(n/2)) / 2.0;
    }

    private static double round2(double v) { return Math.round(v * 100.0) / 100.0; }
    private static double round4(double v) { return Math.round(v * 10000.0) / 10000.0; }

    private static List<SeriePointDTO> fillDays(
            LocalDate fromDay,
            LocalDate toDayInclusive,
            List<Object[]> rows,
            boolean roundMoney
    ) {
        Map<LocalDate, Double> map = new HashMap<>();
        for (Object[] r : rows) {
            LocalDate dia = ((java.sql.Date) r[0]).toLocalDate();
            double val = ((Number) r[1]).doubleValue();
            if (roundMoney) val = Math.round(val * 100.0) / 100.0;
            map.put(dia, val);
        }

        List<SeriePointDTO> out = new ArrayList<>();
        for (LocalDate d = fromDay; !d.isAfter(toDayInclusive); d = d.plusDays(1)) {
            out.add(new SeriePointDTO(d, map.getOrDefault(d, 0.0)));
        }
        return out;
    }
}