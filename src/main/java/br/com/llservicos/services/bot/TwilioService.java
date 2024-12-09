package br.com.llservicos.services.bot;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.pedido.PedidoModel;
import br.com.llservicos.domain.pedido.dtos.PedidoResponseDTO;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.repositories.EnderecoRepository;
import br.com.llservicos.repositories.PedidoRepository;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.repositories.ServicoRepository;
import br.com.llservicos.services.endereco.EnderecoService;
import br.com.llservicos.services.pessoa.PessoaFisicaService;
import br.com.llservicos.services.pessoa.PessoaJuridicaService;
import br.com.llservicos.services.pessoa.PessoaService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TwilioService {


    @ConfigProperty(name = "twilio.account.sid")
    String accountSid;

    @ConfigProperty(name = "twilio.auth.token")
    String authToken;

    @ConfigProperty(name = "twilio.whatsapp.from")
    String fromWhatsApp;

    @Inject
    ServicoRepository servicoRepository;

    @Inject
    EnderecoService enderecoService;

    @Inject
    PessoaService pessoaService;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    EnderecoRepository enderecoRepository;
    @Inject
    PessoaFisicaService pessoaFisicaService;

    @Inject
    PedidoRepository pedidoRepository;
    @Inject
    PessoaJuridicaService pessoaJuridicaService;

    @PostConstruct
    void init() {
        Twilio.init(accountSid, authToken);
    }

    public String sendWhatsAppMessage(String toWhatsApp, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:" + toWhatsApp),
                        new PhoneNumber(fromWhatsApp),
                        messageBody)
                .create();

        return message.getSid();
    }

    private Map<String, String> sessionMap = new HashMap<>();
    private Map<String, PessoaFisicaDTO> tempPessoaFisica = new HashMap<>();
    private Map<String, PessoaJuridicaDTO> tempPessoaJuridica = new HashMap<>();
    String guardaEndereco = null;
    String guardaNomeServiço = null;
    boolean statusPF = false;
    public String processMessage(String from, String body) {
        String replyMessage = null;

        // Obtém o estado atual ou define como "initial" se não estiver presente
        String currentState = sessionMap.getOrDefault(from, "initial");

        // Tratamento do telefone
        String telefone = from.replaceAll("(?i)^whatsapp:\\+?55", "").trim();



        if ("initial".equals(currentState)) {
            // Enviar mensagem de boas-vindas e opções iniciais
            replyMessage = "Olá! Sou o assistente virtual da LL Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
            sessionMap.put(from, "awaiting_option"); // Aguardando uma resposta válida
        } else {
            List<String> nomeServico = servicoRepository.findAllNomesOrderedById();
            List<String> nomeDescricao = servicoRepository.findAllDescricoesOrderedById();
            //Buscando o usuário
            PessoaResponseDTO usuario = pessoaService.buscarPessoaPorTelefone(telefone);

            switch (currentState) {
                case "awaiting_option": // Aguardar uma opção do menu inicial
                    switch (body.trim()) {

                        case "1":
                            sessionMap.put(from, "solicitacao_servico");


                            // Construir a mensagem com a lista de serviços
                            StringBuilder mensagemServicos1 = new StringBuilder("Ótimo! Vou precisar de algumas informações. Vamos começar!\n");
                            mensagemServicos1.append("Qual o tipo de serviço de jardinagem você precisa?\n");

                            // Adiciona os serviços na mensagem numerados
                            for (int i = 0; i < nomeServico.size(); i++) {
                                mensagemServicos1.append("\uD83C\uDF31 ").append(nomeServico.get(i)).append("\n");
                            }

                            replyMessage = mensagemServicos1.toString();
                            break;

                        case "2":
                            sessionMap.put(from, "solicitacao_servico");
                            StringBuilder mensagemServicos = new StringBuilder("Nós oferecemos uma variedade de serviços de jardinagem para atender às suas necessidades. Aqui estão os principais serviços que realizamos:\n").append("\n");


                            // Adiciona os serviços na mensagem numerados
                            for (int i = 0; i < nomeDescricao.size(); i++) {
                                mensagemServicos.append("\uD83C\uDF31 ").append(nomeServico.get(i)).append(": ").append(nomeDescricao.get(i)).append("\n");
                            }
                            mensagemServicos.append("\u2B05 ").append("Voltar").append("\n");
                            mensagemServicos.append("\nDigite a opção do serviço que deseja solicitar:");
                            replyMessage = mensagemServicos.toString();

                            break;

                        case "3":
                            sessionMap.put(from, "pesquisar_pedido");
                            replyMessage = "Por favor, informe o número do pedido para acompanhar o status do serviço:";
                            break;

                        case "4":
                            sessionMap.put(from, "contato_colaborador");
                            replyMessage = "Informe o que você precisa e um de nossos atendentes entrará em contato em breve.";
                            break;

                        default:
                            replyMessage = "Opção inválida. Por favor, escolha:\n1. Solicitar um serviço.\n2. Conhecer os serviços de jardinagem.\n3. Acompanhar serviço.\n4. Falar com um atendente.";
                            break;
                    }
                    break;

                case "pesquisar_pedido":
                    body = body.trim();  // Remove espaços antes e depois da string
                    Long numPedido = Long.parseLong(body);
                    PedidoModel solicitacao = pedidoRepository.findById(numPedido);
                    if (solicitacao==null){
                        sessionMap.put(from, "initial");
                        replyMessage = "Não existe solicitação com esse codigo!";
                        sessionMap.remove(from);
                    }else {
                        StringBuilder mensagemw = new StringBuilder("\uD83C\uDF31  Sua solicitação: ");
                        mensagemw.append(solicitacao.getServico().getNome());
                        mensagemw.append(" esta com status de ").append(solicitacao.getStatus()).append("\n");

                        // Agora, envie a resposta para o cliente com as opções
                        replyMessage = mensagemw.toString();
                    }
                    break;

                case "solicitacao_servico":
                    body = body.trim();  // Remove espaços extras

                    if (body.equalsIgnoreCase("Outro")) {
                        replyMessage = "Por favor, descreva o serviço que você está buscando.";
                        sessionMap.put(from, "contato_colaborador");
                    } else if (body.equalsIgnoreCase("voltar")) {
                        sessionMap.put(from, "initial");
                        replyMessage = "Olá! Sou o assistente virtual da L&L Prestadora de Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
                        break;
                    } else {
                        boolean servicoValido = false;
                        for (String servico : nomeServico) {
                            if (servico.equalsIgnoreCase(body)) {
                                servicoValido = true;
                                break;
                            }
                        }
                            guardaNomeServiço = servicoRepository.findNomeByNome(body);
                        if (servicoValido) {
                            guardaNomeServiço = servicoRepository.findNomeByNome(body);
                            replyMessage = "Você escolheu o serviço: " + body + ". \nÉ isso mesmo? \n1. Sim \n2. Não";
                            sessionMap.put(from, "confirmacao_pedido");
                        } else {
                            sessionMap.put(from, "solicitacao_servico");
                            StringBuilder mensagemServicos = new StringBuilder("Nós oferecemos uma variedade de serviços de jardinagem para atender às suas necessidades. Aqui estão os principais serviços que realizamos:\n");

                            // Adiciona os serviços na mensagem numerados
                            for (int i = 0; i < nomeDescricao.size(); i++) {
                                mensagemServicos.append("\uD83C\uDF31 ").append(nomeServico.get(i)).append(": ").append(nomeDescricao.get(i)).append("\n");
                            }
                            mensagemServicos.append("\nDigite a opção do serviço que deseja solicitar:");
                            replyMessage = mensagemServicos.toString();
                        }
                    }
                    break;


                case "confirmacao_pedido":
                    body = body.trim();  // Remove espaços antes e depois da string

                    if (body.equalsIgnoreCase("Sim") || body.equalsIgnoreCase("1")) {

                        // Verificar o cadastro
                        if (usuario != null) {
                            sessionMap.put(from, "usuarioComCadastro");
                            StringBuilder mensagemConfirmacao = new StringBuilder("Ótimo! " + usuario.nome() + ", encontrei seu cadastro! Vamos seguir com as próximas etapas! 😆\n");
                            mensagemConfirmacao.append("Agora, por favor, escolha uma das opções abaixo:\n");
                            mensagemConfirmacao.append("1- Fazer orçamento\n");
                            mensagemConfirmacao.append("2- Acompanhar serviço já solicitado\n");
                            mensagemConfirmacao.append("3- Voltar ao menu principal\n");

                            // Agora, envie a resposta para o cliente com as opções
                            replyMessage = mensagemConfirmacao.toString();

                        } else {
                            sessionMap.put(from, "fazer_cadastro");
                            StringBuilder mensagemCadastro = new StringBuilder("Você ainda não possui cadastro, sem problemas! Vou ajudar você a criar agora mesmo 😊");
                            mensagemCadastro.append("\nVocê é uma pessoa juridica ou pessoa fisica?");
                            mensagemCadastro.append("\n🙋 1. Pessoa Física");
                            mensagemCadastro.append("\n👨‍💼 2. Pessoa Jurídica");
                            replyMessage = mensagemCadastro.toString();
                        }
                    } else if (body.equalsIgnoreCase("Não") || body.equalsIgnoreCase("2")) {
                        sessionMap.put(from, "solicitacao_servico");
                        StringBuilder mensagemServicos = new StringBuilder("Ótimo! Vou precisar de algumas informações. Vamos começar!\n");
                        mensagemServicos.append("Qual o tipo de serviço de jardinagem você precisa?\n");

                        // Adiciona os serviços na mensagem numerados
                        for (int i = 0; i < nomeServico.size(); i++) {
                            mensagemServicos.append("\uD83C\uDF31 -").append(nomeServico.get(i)).append("\n");
                        }

                        replyMessage = mensagemServicos.toString();
                    } else {
                        sessionMap.put(from, "solicitacao_servico");
                        StringBuilder mensagemServicos = new StringBuilder("Ótimo! Vou precisar de algumas informações. Vamos começar!\n");
                        mensagemServicos.append("Qual o tipo de serviço de jardinagem você precisa?\n");

                        // Adiciona os serviços na mensagem numerados
                        for (int i = 0; i < nomeServico.size(); i++) {
                            mensagemServicos.append("\uD83C\uDF31 ").append(nomeServico.get(i)).append("\n");
                        }

                        replyMessage = mensagemServicos.toString();
                    }
                    break;

                //Fazendo cadastro do usuário
                case "fazer_cadastro":
                    // Pergunta se é Pessoa Física ou Jurídica
                    if (body.equalsIgnoreCase("1") || body.equalsIgnoreCase("Pessoa Fisica")) {
                        sessionMap.put(from, "cadastrar_pfisica");
                        System.out.println("fazer_cadastro entrou como pessoa fisica");
                        statusPF=true;
                        tempPessoaFisica.put(from, new PessoaFisicaDTO());
                        replyMessage = "Qual o seu nome completo?";
                    } else if (body.equalsIgnoreCase("2") || body.equalsIgnoreCase("Pessoa Jurídica")) {
                        replyMessage = "Qual o nome da sua empresa?";
                        System.out.println("fazer_cadastro entrou como pessoa jurifica");
                        tempPessoaJuridica.put(from, new PessoaJuridicaDTO());
                        sessionMap.put(from, "cadastrar_pjuridica");
                    } else {
                        replyMessage = "Opção inválida. Escolha:\n1. Pessoa Física\n2. Pessoa Jurídica";
                        break;
                    }
                    break;

                case "cadastrar_pfisica":
                    // Espera o nome completo
                    if (!body.isEmpty()) {
                        PessoaFisicaDTO pessoaFisica = tempPessoaFisica.get(from);
                        pessoaFisica.setNome(body.trim());
                        sessionMap.put(from, "aguardando_cpf");
                        replyMessage = "Qual o seu CPF?";
                    } else {
                        replyMessage = "Por favor, informe seu CPJ.";
                    }
                    break;
                case "aguardando_cpf":
                    // Espera o endereço
                    if (!body.isEmpty()) {
                        PessoaFisicaDTO pessoaFisica = tempPessoaFisica.get(from);
                        pessoaFisica.setCpf(body.trim());
                        sessionMap.put(from, "cadastrar_endereco");
                        replyMessage = "Qual o seu endereço?";
                    } else {
                        replyMessage = "Por favor, informe seu endereço:";
                    }
                    break;


                case "cadastrar_pjuridica":
                    // Espera o nome completo
                    if (!body.isEmpty()) {
                        PessoaJuridicaDTO pessoaJuridica = tempPessoaJuridica.get(from);
                        pessoaJuridica.setNome(body.trim());
                        sessionMap.put(from, "aguardando_cnpj");
                        replyMessage = "Qual o seu CNPJ?";
                    } else {
                        replyMessage = "Por favor, informe seu nome CNPJ.";
                    }
                    break;

                case "aguardando_cnpj":
                    if (!body.isEmpty()) {
                        PessoaJuridicaDTO pessoaJuridica = tempPessoaJuridica.get(from);
                        pessoaJuridica.setCnpj(body.trim());
                        sessionMap.put(from, "cadastrar_endereco");
                        replyMessage = "Qual o endereço completo da sua empresa?";
                    } else {
                        replyMessage = "Por favor, informe seu endereço:";
                    }
                    break;

                case "cadastrar_endereco":
                    if (!body.isEmpty()) {
                        if (tempPessoaFisica != null) {
                            PessoaFisicaDTO pessoaFisica = tempPessoaFisica.get(from);
                            System.out.println("Pessoa Fifica cadastro entrou*");
                            List<EnderecoDTO> enderecos = new ArrayList<>();
                            enderecos.add(new EnderecoDTO(body, null, null, null, null, null, null));
                            guardaEndereco = body;

                            pessoaFisica.setEnderecos(enderecos);
                            pessoaFisica.setTelefone(telefone);
                            //cadastrar
                            criarPessoaFisica(pessoaFisica);
                            usuario = pessoaService.buscarPessoaPorTelefone(telefone);

                            sessionMap.put(from, "usuarioComCadastro");
                            StringBuilder mensagemConfirmacao = new StringBuilder("Ótimo! " + usuario.nome() + ", cadastro concluido! 😆\n");
                            mensagemConfirmacao.append("Agora, por favor, escolha uma das opções abaixo:\n");
                            mensagemConfirmacao.append("1- Fazer orçamento\n");
                            mensagemConfirmacao.append("2- Acompanhar serviço já solicitado\n");
                            mensagemConfirmacao.append("3- Voltar ao menu principal\n");
                            replyMessage = mensagemConfirmacao.toString();

                        } else if (tempPessoaJuridica != null) {
                            PessoaJuridicaDTO pessoaJuridica = tempPessoaJuridica.get(from);
                            System.out.println("Pessoa Juridica cadastro entrou*");
                            List<EnderecoDTO> enderecos = new ArrayList<>();
                            enderecos.add(new EnderecoDTO(body, null, null, null, null, null, null));
                            guardaEndereco = body;

                            pessoaJuridica.setEnderecos(enderecos);
                            pessoaJuridica.setTelefone(telefone);
                            //cadastrar
                            criarPessoaJuridica(pessoaJuridica);
                            usuario = pessoaService.buscarPessoaPorTelefone(telefone);

                            sessionMap.put(from, "usuarioComCadastro");
                            StringBuilder mensagemConfirmacao = new StringBuilder("Ótimo! " + usuario.nome() + ", cadastro concluido! 😆\n");
                            mensagemConfirmacao.append("Agora, por favor, escolha uma das opções abaixo:\n");
                            mensagemConfirmacao.append("1- Fazer orçamento\n");
                            mensagemConfirmacao.append("2- Acompanhar serviço já solicitado\n");
                            mensagemConfirmacao.append("3- Voltar ao menu principal\n");
                            replyMessage = mensagemConfirmacao.toString();
                        }
                    } else {
                        replyMessage = "Por favor, informe seu endereço:";
                    }
                    break;

                case "usuarioComCadastro":
                    if (body.equalsIgnoreCase("1")) {
                        guardaEndereco =  enderecoRepository.ultimoEndereco(usuario.id());
                        StringBuilder mensagem = new StringBuilder(usuario.nome() + ", de acordo com seu cadastro seu endereço é ");
                        mensagem.append(guardaEndereco);
                        mensagem.append("\nDigite: \n1. Para confirmar \n2. Para Alterar");
                        replyMessage = mensagem.toString();
                        sessionMap.put(from, "escolha_endereco");

                    } else if (body.equalsIgnoreCase("2")) {

                        // Verifica se body é nulo e remove espaços
                        if (body != null) {
                            body = body.trim();
                        }
                        List<PedidoModel> pedidos = pedidoRepository.findByPessoaId(usuario.id());

                        // Verifica se pedidos não é nulo ou vazio
                        if (pedidos != null && !pedidos.isEmpty()) {
                            StringBuilder mensagemw = new StringBuilder().append("Esses são suas solicitações anteriores:\n");

                            for (PedidoModel pedido : pedidos) {
                                // Adiciona o nome do serviço na lista
                                mensagemw.append("\uD83C\uDF31 Serviço:").append(pedido.getServico().getNome());
                                mensagemw.append("| Status:").append(pedido.getStatus());
                                mensagemw.append("| Valor:").append(pedido.getValorTotal()).append("\n");
                            }
                            mensagemw.append("1. Volta para menu anterior\n");
                            mensagemw.append("2. Volta para menu principal\n");
                            mensagemw.append("3. Sair\n");

                            // Formata a resposta final
                            replyMessage = mensagemw.toString();
                            sessionMap.put(from, "menu_acompanhar");
                        } else {
                            // Caso não haja pedidos, fornece uma mensagem padrão
                            StringBuilder mensagemw = new StringBuilder().append("Esses são suas solicitações anteriores:");
                            mensagemw.append("1. Volta para menu anterior\n");
                            mensagemw.append("2. Volta para menu principal\n");
                            mensagemw.append("3. Sair\n");
                            replyMessage = mensagemw.toString();
                            sessionMap.put(from, "menu_acompanhar");
                        }

                    } else if (body.equalsIgnoreCase("3")) {
                        replyMessage = "Olá! Sou o assistente virtual da L&L Prestadora de Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
                        sessionMap.put(from, "awaiting_option");
                    } else {
                        sessionMap.put(from, "usuarioComCadastro");
                        StringBuilder mensagem = new StringBuilder(usuario.nome() + ", por favor, escolha uma das opções abaixo: 😆\n");
                        mensagem.append("1- Fazer orçamento\n");
                        mensagem.append("2- Acompanhar serviço já solicitado\n");
                        mensagem.append("3- Voltar ao menu principal\n");

                        replyMessage = mensagem.toString();
                    }
                    break;

                case "escolha_endereco":
                    body = body.trim();
                    if (body.equalsIgnoreCase("1")) {
                        guardaEndereco =  enderecoRepository.ultimoEndereco(usuario.id());
                        //Usuário confimou o endereço, equipe vai entrar em contato
                        sessionMap.put(from, "contato_colaborador");
                        StringBuilder mensagem = new StringBuilder(usuario.nome() + ", esta sendo analisada sua solicitação de orçamento para ");
                        mensagem.append(guardaNomeServiço);
                        mensagem.append(" no endereço: ");
                        mensagem.append(guardaEndereco +".");
                        mensagem.append("\nNossa equipe entrará em contato em breve.");
                        replyMessage = mensagem.toString();
                        sessionMap.remove(from);

                    } else if (body.equalsIgnoreCase("2")) {
                        replyMessage = "Digite o novo endereço:";
                        sessionMap.put(from, "novo_endereco");
                    }
                    else{
                        replyMessage = "Não entendi, por favor repita sua reposta.";
                        sessionMap.put(from, "usuarioComCadastro");
                    }
                    break;

                case "novo_endereco":
                    body = body.trim();  // Remove espaços antes e depois da string

                    enderecoService.adicionaEndBot(usuario.id(), new EnderecoDTO(body, null, null , null, null, null,null));
                    guardaEndereco =  enderecoRepository.ultimoEndereco(usuario.id());

                    StringBuilder mensagem = new StringBuilder(usuario.nome() + ", seu novo endereço é ");
                    mensagem.append(guardaEndereco);
                    mensagem.append("\nDigite: \n1. Para confirmar \n2. Para Alterar");

                    replyMessage = mensagem.toString();

                    sessionMap.put(from, "escolha_endereco");
                    break;

                case "acompanhar_servico":
                    // Verifica se body é nulo e remove espaços
                    if (body != null) {
                        body = body.trim();
                    }

                    // Recupera os pedidos associados ao ID do usuário
                    List<PedidoModel> pedidos = pedidoRepository.findByPessoaId(usuario.id());
                    System.out.println("Pedidos retornados pelo repositório: " + pedidos);

                    // Verifica se a lista de pedidos não é nula e não está vazia
                    if (pedidos != null && !pedidos.isEmpty()) {
                        System.out.println("Lista de pedidos não está vazia, tamanho: " + pedidos.size());

                        // Construção da mensagem com os pedidos do cliente
                        StringBuilder mensagemw = new StringBuilder().append("Esses são suas solicitações anteriores:\n");
                        for (PedidoModel pedido : pedidos) {
                            mensagemw.append("\uD83C\uDF31 Serviço: ").append(pedido.getServico().getNome())
                                    .append(" | Status: ").append(pedido.getStatus())
                                    .append(" | Valor: ").append(pedido.getValorTotal()).append("\n");
                        }
                        mensagemw.append("\n1. Volta para menu anterior\n");
                        mensagemw.append("2. Volta para menu principal\n");
                        mensagemw.append("3. Sair\n");

                        replyMessage = mensagemw.toString();
                        sessionMap.put(from, "menu_acompanhar");
                    } else {
                        // Caso a lista seja nula ou vazia
                        System.out.println("Lista de pedidos é nula ou vazia.");

                        StringBuilder mensagemw = new StringBuilder().append("Você ainda não fez nenhuma solicitação:\n");
                        mensagemw.append("\n1. Volta para menu anterior\n");
                        mensagemw.append("2. Volta para menu principal\n");
                        mensagemw.append("3. Sair\n");

                        replyMessage = mensagemw.toString();
                        sessionMap.put(from, "menu_acompanhar");
                    }

                    // Remove a sessão associada ao usuário
                    sessionMap.remove(from);
                    break;

                case "menu_acompanhar":
                    body = body.trim();
                    if (body.equalsIgnoreCase("1")) {
                        sessionMap.put(from, "usuarioComCadastro");

                        StringBuilder mensagemConfirmacao = new StringBuilder(usuario.nome() + " escolha uma das opções abaixo:\n");
                        mensagemConfirmacao.append("1- Fazer orçamento\n");
                        mensagemConfirmacao.append("2- Acompanhar serviço já solicitado\n");
                        mensagemConfirmacao.append("3- Voltar ao menu principal\n");
                        replyMessage = mensagemConfirmacao.toString();
                        break;
                    } else if (body.equalsIgnoreCase("2")) {
                        sessionMap.put(from, "initial");
                        replyMessage = "Olá! Sou o assistente virtual da L&L Prestadora de Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
                        break;

                    }
                    else if (body.equalsIgnoreCase("3")) {
                        replyMessage = "Se precisar de algo no futuro, estarei aqui para ajudar! Tenha um ótimo dia! \uD83C\uDF1F\";";
                        sessionMap.remove(from);
                    }
                    else{
                        StringBuilder mensagemw = new StringBuilder().append("Não entendi, qual opção vc deseja:\n");
                        mensagemw.append("1. Volta para menu anterior\n");
                        mensagemw.append("2. Volta para menu principal\n");
                        mensagemw.append("3. Sair\n");
                        replyMessage = mensagemw.toString();
                        sessionMap.put(from, "menu_acompanhar");
                    }
                    break;

                case "contato_colaborador":
                    body = body.trim();  // Remove espaços antes e depois da string

                    replyMessage = "Obrigado! Nossa equipe entrará em contato em breve.";
                    sessionMap.remove(from); // Encerra a interação após registrar o pedido
                    break;

                default: // Estado desconhecido ou reset
                    sessionMap.put(from, "initial");
                    replyMessage = "Olá! Sou o assistente virtual da L&L Prestadora de Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
                    break;
            }
        }

        return replyMessage;
    }

    public PessoaFisicaResponseDTO criarPessoaFisica(PessoaFisicaDTO pf) {
        try {
            return pessoaFisicaService.insert(pf);
        } catch (Exception e) {
            // Tratamento ou log da exceção
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar pessoa fisíca ", e);
        }
    }

    public PessoaJuridicaResponseDTO criarPessoaJuridica(PessoaJuridicaDTO pj) {
        try {

            return pessoaJuridicaService.insert(pj);
        } catch (Exception e) {
            // Tratamento ou log da exceção
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar pessoa fisíca ", e);
        }
    }

}