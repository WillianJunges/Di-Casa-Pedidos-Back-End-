package com.dicasa.pedidos.config;

import com.dicasa.pedidos.model.*;
import com.dicasa.pedidos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UsuarioRepository usuarioRepo,
            ProdutoRepository produtoRepo,
            ConfiguracoesRepository configRepo,
            CategoriaRepository categoriaRepo) {
        return args -> {
            // Usuário admin padrão
            if (!usuarioRepo.existsById("usuario-admin")) {
                Usuario admin = new Usuario();
                admin.setId("usuario-admin");
                admin.setNome("Administrador");
                admin.setTelefone("(11) 94002-8922");
                admin.setEndereco("Rua Itupeva, 45, Vila Furlan");
                admin.setDistancia(1.4);
                admin.setTaxaEntrega(0.0);
                admin.setIsAdmin(true);
                admin.setCredenciais(new Credenciais("admin", "admin"));
                usuarioRepo.save(admin);
                System.out.println("✅ Admin criado: usuario=admin senha=admin");
            }

            // Categorias padrão
            if (categoriaRepo.count() == 0) {
                List<Categoria> cats = List.of(
                    categoria("Marmitas",   "🍱", 0),
                    categoria("Assados",    "🍗", 1),
                    categoria("Bebidas",    "🥤", 2),
                    categoria("Sobremesas", "🍰", 3)
                );
                categoriaRepo.saveAll(cats);
                System.out.println("✅ Categorias padrão carregadas");
            }

            // Produtos de exemplo
            if (produtoRepo.count() == 0) {
                produtoRepo.saveAll(criarProdutos());
                System.out.println("✅ " + produtoRepo.count() + " produtos carregados");
            }

            // Configurações do restaurante
            if (configRepo.count() == 0) {
                configRepo.save(criarConfiguracoes());
                System.out.println("✅ Configurações do restaurante carregadas");
            }
        };
    }

    private Categoria categoria(String nome, String emoji, int ordem) {
        Categoria c = new Categoria();
        c.setNome(nome);
        c.setEmoji(emoji);
        c.setOrdem(ordem);
        c.setAtivo(true);
        return c;
    }

    private List<Produto> criarProdutos() {
        List<Produto> lista = new ArrayList<>();

        lista.add(produto("1", "Marmita de Frango", "Frango grelhado com arroz, feijão e salada", 25.0, "marmitas",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                List.of("domingo","segunda","terca","quarta","quinta","sexta","sabado")));
        lista.add(produto("2", "Marmita de Carne", "Carne assada com arroz, feijão e batata", 28.0, "marmitas",
                "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
                List.of("terca")));
        lista.add(produto("3", "Frango Assado", "Frango inteiro assado temperado", 40.0, "assados",
                "https://images.unsplash.com/photo-1604908177522-402f3f61b1b4",
                List.of("quarta","quinta")));
        lista.add(produto("4", "Costela Assada", "Costela bovina assada lentamente", 55.0, "assados",
                "https://images.unsplash.com/photo-1558030006-450675393462",
                List.of()));
        lista.add(produto("5", "Coca-Cola", "Lata 350ml gelada", 5.0, "bebidas",
                "https://images.unsplash.com/photo-1554866585-cd94860890b7",
                List.of("domingo","segunda","terca","quarta","quinta","sexta","sabado")));
        lista.add(produto("6", "Suco de Laranja", "Suco natural 500ml", 8.0, "bebidas",
                "https://images.unsplash.com/photo-1571689936114-b8c9d2c6a9b0",
                List.of()));
        lista.add(produto("7", "Pudim", "Pudim de leite condensado", 10.0, "sobremesas",
                "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445",
                List.of("quinta","sexta")));
        lista.add(produto("8", "Bolo de Chocolate", "Fatia de bolo com cobertura", 12.0, "sobremesas",
                "https://images.unsplash.com/photo-1599785209707-28c3e0c1c2f5",
                List.of()));
        lista.add(produto("9", "Marmita Fitness", "Frango grelhado, batata doce e legumes", 30.0, "marmitas",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
                List.of("sabado")));
        lista.add(produto("10", "Feijoada", "Feijoada completa com acompanhamentos", 35.0, "marmitas",
                "https://images.unsplash.com/photo-1600891964092-4316c288032e",
                List.of("domingo")));

        return lista;
    }

    private Produto produto(String id, String nome, String descricao, Double preco,
                            String categoria, String imagem, List<String> dias) {
        Produto p = new Produto();
        p.setId(id);
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(preco);
        p.setCategoria(categoria);
        p.setImagem(imagem);
        p.setDiaDaSemana(new ArrayList<>(dias));
        return p;
    }

    private Configuracoes criarConfiguracoes() {
        Configuracoes c = new Configuracoes();
        c.setNome("DiCasa Marmitaria");
        c.setEndereco("R. Dom Pedro I, 65 - Cidade Nova I, Indaiatuba - SP, 13334-100");
        c.setTelefone("(11) 98765-4321");
        c.setEmail("contato@dicasa.com.br");
        c.setLatitude(-23.0896);
        c.setLongitude(-47.2139);
        c.setRaioEntregaGratis(5.0);
        c.setTaxaPorKm(1.0);
        c.setFiltrarPorDia(true);

        Map<String, Object> horario = new LinkedHashMap<>();
        horario.put("segunda", null);
        horario.put("terca", Map.of("open", "11:00", "close", "22:00"));
        horario.put("quarta", Map.of("open", "11:00", "close", "22:00"));
        horario.put("quinta", Map.of("open", "09:00", "close", "18:00"));
        horario.put("sexta", Map.of("open", "09:00", "close", "23:00"));
        horario.put("sabado", Map.of("open", "11:00", "close", "22:00"));
        horario.put("domingo", Map.of("open", "11:00", "close", "22:00"));
        c.setHorarioFuncionamento(horario);

        Map<String, Object> pixKey = new LinkedHashMap<>();
        pixKey.put("tipo", "Email");
        pixKey.put("chave", "contato@dicasa.com.br");
        c.setChavesPix(List.of(pixKey));

        return c;
    }
}
