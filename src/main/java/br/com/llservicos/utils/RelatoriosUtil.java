package br.com.llservicos.utils;


import br.com.llservicos.domain.servico.ServicoModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class RelatoriosUtil implements Serializable {
    // retorna o pdf em byte array
    public byte[] gerarRelatorio(List<ServicoModel> listaServicos, String relatorio, ServletContext context)
            throws Exception{
        //cria a lista de dados para o relatório com a lista de obj
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaServicos);

        //Carrega o caminho do arquivo Jasper
        String caminhoJasper = context.getRealPath("pasta")+ File.separator + relatorio + ".jasper";

        //Carrega o arquivo jasper passando os dados
        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper,null, source);

        //Exporta para PDF
        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }
    public byte[] gerarRelatorioPorPeriodo(List<ServicoModel> listaServicos, Date dataInicio, Date dataFim, String relatorio, ServletContext context) throws Exception {
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaServicos);
        String caminhoJasper = context.getRealPath("pasta") + File.separator + relatorio + ".jasper";
        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, null, source);
        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }

    public byte[] gerarRelatorioPorTipo(List<ServicoModel> listaServicos, String tipoServico, String relatorio, ServletContext context) throws Exception {
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaServicos);
        String caminhoJasper = context.getRealPath("pasta") + File.separator + relatorio + ".jasper";
        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, null, source);
        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }
}
