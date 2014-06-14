package org.javaee.bolao.partida;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.javaee.bolao.config.IResources;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.exception.BolaoRuntimeException;

@ManagedBean
public class RelatorioApostas {

	@Resource(lookup = IResources.DATASOURCE)
	private DataSource dataSource;
	
	public byte[] gerarRelatorioPDFApostasPorPartida(Partida partida) {
		
		try{
			
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("IDPARTIDA", partida.getIdPartida());
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getClassLoader().getResourceAsStream("relatorios/RelatorioApostas.jasper"),parametros, dataSource.getConnection());
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
		}catch(Exception e){
			throw new BolaoRuntimeException(e, "Erro ao gerar o relatório. Motivo: {0}", e.getMessage());
		}
	}
	
}
