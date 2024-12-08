package br.com.llservicos.services.bot;

import br.com.llservicos.domain.endereco.dtos.EnderecoDTO;
import br.com.llservicos.domain.endereco.dtos.EnderecoResponseDTO;
import br.com.llservicos.domain.pessoa.PessoaModel;
import br.com.llservicos.domain.pessoa.dtos.PessoaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.PessoaFisicaModel;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaDTO;
import br.com.llservicos.domain.pessoa.pessoafisica.dtos.PessoaFisicaResponseDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.PessoaJuridicaModel;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaDTO;
import br.com.llservicos.domain.pessoa.pessoajuridica.dtos.PessoaJuridicaResponseDTO;
import br.com.llservicos.domain.servico.dto.ServicoResponseDTO;
import br.com.llservicos.repositories.EnderecoRepository;
import br.com.llservicos.repositories.PessoaRepository;
import br.com.llservicos.repositories.ServicoRepository;
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
    PessoaRepository pessoaRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PessoaService pessoaService;

    @Inject
    PessoaFisicaService pessoaFisicaService;

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

    boolean statusPF = false;
    public String processMessage(String from, String body) {
        String replyMessage = null;

        // Obtém o estado atual ou define como "initial" se não estiver presente
        String currentState = sessionMap.getOrDefault(from, "initial");

        // Tratamento do telefone
        String telefone = from.replaceAll("(?i)^whatsapp:\\+?55", "").trim();

        //Buscando o usuário
        PessoaResponseDTO usuario = pessoaService.buscarPessoaPorTelefone(telefone);


        //Verifica se vai querer apenas um endereço
        boolean orcamento = false;

        //Verifica se vai querer apenas um endereço
        boolean orcamento = false;

        if ("initial".equals(currentState)) {
            // Enviar mensagem de boas-vindas e opções iniciais
            replyMessage = "Olá! Sou o assistente virtual da LL Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
            sessionMap.put(from, "awaiting_option"); // Aguardando uma resposta válida
        } else {
            List<String> nomeServico = servicoRepository.findAllNomesOrderedById();
            List<String> nomeDescricao = servicoRepository.findAllDescricoesOrderedById();
            List<EnderecoResponseDTO> enderecoEscolhidoParaServico= null;
            ServicoResponseDTO servicoEscolhido = null;
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
                            StringBuilder mensagemServicos = new StringBuilder("Nós oferecemos uma variedade de serviços de jardinagem para atender às suas necessidades. Aqui estão os principais serviços que realizamos:\n");


                            // Adiciona os serviços na mensagem numerados
                            for (int i = 0; i < nomeDescricao.size(); i++) {
                                mensagemServicos.append("\uD83C\uDF31 ").append(nomeDescricao.get(i)).append("\n");
                            }
                            mensagemServicos.append("\nDigite a opção do serviço que deseja solicitar:");
                            replyMessage = mensagemServicos.toString();

                            break;

                        case "3":
                            sessionMap.put(from, "track_service");
                            replyMessage = "Por favor, informe o número do pedido para acompanhar o status do serviço.";
                            break;

                        case "4":
                            sessionMap.put(from, "contato_colaborador");
                            replyMessage = "Informe o que você precisa, e um de nossos atendentes entrará em contato em breve.";
                            break;

                        default:
                            replyMessage = "Opção inválida. Por favor, escolha:\n1. Solicitar um serviço.\n2. Conhecer os serviços de jardinagem.\n3. Acompanhar serviço.\n4. Falar com um atendente.";
                            break;
                    }
                    break;

                case "solicitacao_servico":
                    body = body.trim();  // Remove espaços extras

                    if (body.equalsIgnoreCase("Outro")) {
                        replyMessage = "Por favor, descreva o serviço que você está buscando.";
                        sessionMap.put(from, "contato_colaborador");
                    } else {
                        // Verifica se o serviço fornecido pelo usuário está na lista (ignorando maiúsculas e minúsculas)
                        boolean encontrado = false;
                        Long numeService = Long.parseLong(body);
                        servicoEscolhido = servicoRepository.buscarPorId(numeService);
                        for (String servico : nomeServico) {
                            if (servico.equalsIgnoreCase(body)) {
                                encontrado = true;
                                break;
                            }
                        }

                        if (encontrado) {
                            replyMessage = "Você escolheu o serviço: " + body + ". \nÉ isso mesmo? \n1. Sim \n2. Não";
                            sessionMap.put(from, "confirmacao_pedido");
                        } else {
                            replyMessage = "Não entendi sua resposta!";
                            sessionMap.remove(from); // Encerra a interação após registrar o pedido
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
                            mensagemConfirmacao.append("3- Alterar cadastro\n");
                            mensagemConfirmacao.append("4- Voltar ao menu principal\n");

                            // Agora, envie a resposta para o cliente com as opções
                            replyMessage = mensagemConfirmacao.toString();

                        } else {
                            sessionMap.put(from, "fazer_cadastro");
                            StringBuilder mensagemCadastro = new StringBuilder("Você ainda não possui cadastro, sem problemas! Vou ajudar você a criar agora mesmo 😊");
                            mensagemCadastro.append("\nVocê é uma pessoa juridica ou pessoa fisica?" + telefone);
                            mensagemCadastro.append("\n🙋 1. Pessoa Física");
                            mensagemCadastro.append("\n👨‍💼 2. Pessoa Jurídica");
                            replyMessage = mensagemCadastro.toString();
                        }
                    } else if (body.equalsIgnoreCase("Não") || body.equalsIgnoreCase("2")) {
                        sessionMap.put(from, "solicitacao_servico");
                        StringBuilder mensagemServicos = new StringBuilder("Ótimo! Vou precisar de algumas informações. Vamos começar!\n");
                        mensagemServicos.append("Qual o tipo de serviço de jardinagem você precisa?\n" + body);

                        // Adiciona os serviços na mensagem numerados
                        for (int i = 0; i < nomeServico.size(); i++) {
                            mensagemServicos.append("\uD83C\uDF31 ").append(nomeServico.get(i)).append("\n");
                        }

                        replyMessage = mensagemServicos.toString();
                    } else {
                        sessionMap.put(from, "solicitacao_servico");
                        StringBuilder mensagemServicos = new StringBuilder("Ótimo! Vou precisar de algumas informações. Vamos começar!\n");
                        mensagemServicos.append("Qual o tipo de serviço de jardinagem você precisa?\n" + body);

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
                        statusPF=true;
                        tempPessoaFisica.put(from, new PessoaFisicaDTO());
                        replyMessage = "Qual o seu nome completo?";
                    } else if (body.equalsIgnoreCase("2") || body.equalsIgnoreCase("Pessoa Jurídica")) {
                        replyMessage = "Qual o nome da sua empresa?";
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
                        replyMessage = "Qual o seu Endereço?";
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
                        replyMessage = "Agora vamos cadastrar o endereço completo que você deseja que seja feito o serviço escolhido.";
                    } else {
                        replyMessage = "Por favor, informe seu endereço completo:";
                    }
                    break;
                //Cadastrar Endereco
                case "cadastrar_endereco":
                    if (!body.isEmpty()) {
                        if (statusPF=true){
                            sessionMap.put(from, "usuarioComCadastro");
                            replyMessage = "digita 1 para fisica";
                        }
                        if (tempPessoaFisica != null) {
                            PessoaFisicaDTO pessoaFisica = tempPessoaFisica.get(from);

                            List<EnderecoDTO> enderecos = new ArrayList<>();
                            enderecos.add(new EnderecoDTO(body, null, null, null, null, null, null));

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
                            mensagemConfirmacao.append("3- Alterar cadastro\n");
                            mensagemConfirmacao.append("4- Voltar ao menu principal\n");
                            replyMessage = mensagemConfirmacao.toString();

                        } else if (tempPessoaJuridica != null) {
                            PessoaJuridicaDTO pessoaJuridica = tempPessoaJuridica.get(from);

                            List<EnderecoDTO> enderecos = new ArrayList<>();
                            enderecos.add(new EnderecoDTO(body, null, null, null, null, null, null));

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
                            mensagemConfirmacao.append("3- Alterar cadastro\n");
                            mensagemConfirmacao.append("4- Voltar ao menu principal\n");
                            replyMessage = mensagemConfirmacao.toString();
                        }else{
                            //Seguindo o fluxo do orçamento...
                            PessoaResponseDTO pessoa = pessoaService.buscarPessoaPorTelefone(telefone);

                            // Recupera os endereços existentes
                            List<EnderecoDTO> enderecos = pessoa.getEnderecos();
                            if (enderecos == null) {
                                enderecos = new ArrayList<>();
                            }

                            // Adiciona o novo endereço
                            enderecos.add(new EnderecoDTO(body, null, null, null, null, null, null));

                            // Atualiza a lista de endereços da pessoa
                            pessoa.setEnderecos(enderecos);

                            // Atualiza a pessoa com o novo endereço
                            pessoaService.update(pessoa.id());


                        }
                    } else {
                        replyMessage = "Por favor, informe seu endereço:";
                    }
                    break;

                case "usuarioComCadastro":
                    if (body.equalsIgnoreCase("1")) {
                        sessionMap.put(from, "verificar_endereco");

                        StringBuilder mensagem = new StringBuilder("De acordo com seu cadastro "+usuario.nome() + ", os seus endereços cadastro são esses!\n");
                        mensagem.append("Qual é o local onde o serviço será realizado?\n");

                        PessoaResponseDTO pessoa = pessoaService.buscarPessoaPorTelefone(telefone);
                        List<EnderecoResponseDTO> enderecos = enderecoRepository.findByPessoaId(pessoa.id());
                        for (int i = 0; i < enderecos.size(); i++) {
                            mensagem.append(i).append(". ").append(enderecos.get(i)).append(".");
                        }
                        mensagem.append("Digite Adicionar para um novo endereço.\n");
                    } else if (body.equalsIgnoreCase("2")) {
                        orcamento = true;
                        sessionMap.put(from, "verificar_endereco");

                        StringBuilder mensagem = new StringBuilder("De acordo com seu cadastro "+usuario.nome() + ", os seus endereços cadastro são esses!\n");
                        mensagem.append("Qual é o local onde o serviço será realizado?\n");

                        PessoaResponseDTO pessoa = pessoaService.buscarPessoaPorTelefone(telefone);
                        List<EnderecoResponseDTO> enderecos = enderecoRepository.findByPessoaId(pessoa.id());
                        for (int i = 0; i < enderecos.size(); i++) {
                            mensagem.append(i).append(". ").append(enderecos.get(i)).append(".");
                        }
                        mensagem.append("Digite Adicionar para um novo endereço.\n");

                        replyMessage = mensagem.toString();
                    } else if (body.equalsIgnoreCase("3")) {
                        replyMessage = "fisica";
                    } else if (body.equalsIgnoreCase("4")) {
                        replyMessage = "Olá! Sou o assistente virtual da LL Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente. \n OI: " + from.trim();
                        sessionMap.put(from, "awaiting_option");
                    } else {
                        sessionMap.put(from, "usuarioComCadastro");
                        StringBuilder mensagem = new StringBuilder(usuario.nome() + ", por favor, escolha uma das opções abaixo: 😆\n");
                        mensagem.append("1- Fazer orçamento\n");
                        mensagem.append("2- Acompanhar serviço já solicitado\n");
                        mensagem.append("3- Alterar cadastro\n");
                        mensagem.append("4- Voltar ao menu principal\n");

                        replyMessage = mensagem.toString();
                    }
                    break;

                case "verificar_endereco":
                    body = body.trim();  // Remove espaços antes e depois da string
                    if (body.equalsIgnoreCase("Adicionar")){
                        sessionMap.put(from, "cadastrar_endereco");
                        replyMessage = "Digite o endereço completo que você deseja que seja feito o serviço: ";
                    }
                    else{
                        try {
                            Integer enderecoEscolhido = Integer.parseInt(body);  // Converte a string para inteiro
                            System.out.println("Número convertido: " + enderecoEscolhido);
                        } catch (NumberFormatException e) {
                            sessionMap.put(from, "verificar_endereco");
                            replyMessage =  "Digite o endereço escolhido ou Adicionar para adicionar um endereço";
                            // Aqui você pode lançar uma exceção personalizada ou tomar outra ação
                        }
                    }
                    StringBuilder mensagem = new StringBuilder(usuario.nome() + ", por favor, escolha uma das opções abaixo: 😆\n");

                    break;

                case "fazer_orcamento":
                    body = body.trim();
                    if (orcamento==true){
                        replyMessage = usuario.nome()+" você escolheu o serviço " + servicoEscolhido +
                                ", para o endenreço " + enderecoEscolhidoParaServico + ". Nossa equipe entrará em contato em breve.";
                    }
                    else{

                    }
                    break;

                case "contato_colaborador":
                    body = body.trim();  // Remove espaços antes e depois da string

                    replyMessage = "Obrigado! Nossa equipe entrará em contato em breve.";
                    sessionMap.remove(from); // Encerra a interação após registrar o pedido
                    break;

                default: // Estado desconhecido ou reset
                    sessionMap.put(from, "initial");
                    replyMessage = "Olá! Sou o assistente virtual da LL Serviços. Estou aqui para ajudar com os nossos serviços de jardinagem. Como posso ajudar você hoje? \n1. Solicitar um serviço. \n2. Conhecer os serviços de jardinagem. \n3. Acompanhar serviço. \n4. Falar com um atendente.";
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