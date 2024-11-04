package br.com.llservicos.resources;


import br.com.llservicos.utils.RelatoriosUtil;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/relatorio")
@Produces(value = MediaType.APPLICATION_OCTET_STREAM)
public class RelatorioResource {
    @Inject
    RelatoriosUtil relatoriosUtil;

    @POST
    @Path("/por-periodo")
    public Response gerarRelatorioPorPeriodo(@QueryParam("dataInicio") String dataInicioStr,
                                             @QueryParam("dataFim") String dataFimStr,
                                             @Context ServletContext context) {
        //try {
            // Converter Strings para Date
        //    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //Date dataInicio = dateFormat.parse(dataInicioStr);
           // Date dataFim = dateFormat.parse(dataFimStr);

           // List<ServicoModel> listaServicos = /* Lógica para buscar os serviços conforme o período */;

           // byte[] pdfBytes = relatoriosUtil.gerarRelatorioPorPeriodo(listaServicos, dataInicio, dataFim, "nome_do_relatorio_por_periodo", context);
          //  return Response.ok(pdfBytes)
          //          .header("Content-Disposition", "attachment; filename=\"relatorio_por_periodo.pdf\"")
           //         .build();
       // } catch (Exception e) {
       //     e.printStackTrace();
            return Response.serverError().entity("Erro ao gerar o relatório por período").build();
        //}
    }
    @POST
    @Path("/por-tipo")
    public Response gerarRelatorioPorTipo(@QueryParam("tipoServico") String tipoServico,
                                          @Context ServletContext context) {
      //  try {
           // List<ServicoModel> listaServicos = /* Lógica para buscar os serviços conforme o tipo de serviço */;

            //byte[] pdfBytes = relatoriosUtil.gerarRelatorioPorTipo(listaServicos, tipoServico, "nome_do_relatorio_por_tipo", context);
          //  return Response.ok(pdfBytes)
           //         .header("Content-Disposition", "attachment; filename=\"relatorio_por_tipo.pdf\"")
           //         .build();
      //  } catch (Exception e) {
        //    e.printStackTrace();
            return Response.serverError().entity("Erro ao gerar o relatório por tipo").build();
       // }
    }

}