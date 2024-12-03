package br.com.llservicos.resources;

import br.com.llservicos.services.bot.TwilioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/bot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BotResource {

    @Inject
    TwilioService twilioService;

    @POST
    @Path("/enviarMensagem")
    public Response sendWhatsApp(SmsRequest smsRequest) {
        String messageSid = twilioService.sendWhatsAppMessage(smsRequest.getToPhone(), smsRequest.getMessageBody());
        return Response.ok("WhatsApp mensagem enviada com SID: " + messageSid + " Para: " + smsRequest.toPhone).build();
    }

    @POST
    @Path("/msgrecebida")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN) // Responde com texto simples
    public Response receiveMessage(@FormParam("Body") String body, @FormParam("From") String from) {
        // Processa a mensagem recebida e gera uma resposta
        String replyMessage = twilioService.processMessage(from, body);

        // Retorna apenas a mensagem como texto
        return Response.ok(replyMessage).build();
    }



    private void saveOrder(String customerNumber, String orderDetails) {
        // Salva o pedido no banco de dados
        System.out.println("Pedido confirmado do cliente " + customerNumber + ": " + orderDetails);
    }

    public static class SmsRequest {
        private String toPhone;
        private String messageBody;

        // Getters e setters
        public String getToPhone() {
            return toPhone;
        }

        public void setToPhone(String toPhone) {
            this.toPhone = toPhone;
        }

        public String getMessageBody() {
            return messageBody;
        }

        public void setMessageBody(String messageBody) {
            this.messageBody = messageBody;
        }
    }
}