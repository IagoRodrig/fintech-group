# 🏦 Sistema FINTECH - Gerenciamento Financeiro

## 📌 Descrição

Sistema de gerenciamento financeiro desenvolvido em Java com integração ao banco de dados Oracle. O projeto implementa operações CRUD para entidades financeiras como Contas, Cartões, Transações, Investimentos e Recompensas.

## 🎯 Objetivos do Projeto

- Implementar classes DAO para acesso ao banco de dados Oracle
- Utilizar JDBC para operações de inserção e consulta
- Implementar tratamento robusto de exceções
- Criar testes automatizados para validação das funcionalidades

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Oracle Database** - Banco de dados relacional
- **JDBC** - API para conexão com banco de dados
- **Oracle FIAP Instance** - Ambiente de banco de dados

## 📦 Estrutura do Projeto

```
fintech-group/
├── src/
│   ├── dao/              # Data Access Objects
│   ├── model/            # Modelos de dados (entidades)
│   ├── controller/       # Controladores (lógica de negócio)
│   └── view/             # Interface com usuário
├── database/             # Scripts SQL
└── INSTRUCOES.md         # Instruções detalhadas
```

## 🚀 Como Usar

### 1. Configuração Inicial

1. Clone o repositório
2. Configure as credenciais do Oracle em `src/dao/ConnectionFactory.java`
3. Adicione o driver JDBC Oracle ao classpath
4. Execute o script `database/create_tables.sql` no Oracle

### 2. Testar Conexão

```bash
# Testar conexão com banco
java dao.TesteConexao
```

### 📖 Documentação Completa

Para instruções detalhadas de configuração e uso, consulte o arquivo [INSTRUCOES.md](INSTRUCOES.md)

## ✨ Funcionalidades Implementadas

### Entidades com DAO Completo:

- ✅ **Conta** - Gerenciamento de contas bancárias
- ✅ **Cartão** - Controle de cartões de crédito/débito
- ✅ **Transação** - Registro de transações financeiras
- ✅ **Investimento** - Controle de investimentos
- ✅ **Recompensa** - Sistema de bônus e recompensas

### Operações Disponíveis:

- `getAll()` - Consulta todos os registros
- `insert()` - Insere novos registros
- `findById()` - Busca por ID específico

## 🔒 Tratamento de Exceções

Todos os DAOs implementam tratamento completo para:

- Falhas de conexão com o banco
- Tabelas inexistentes
- Chaves primárias duplicadas
- Violações de chave estrangeira

## 👥 Autores

Desenvolvido como projeto acadêmico para FIAP

## 📄 Licença

Projeto acadêmico - FIAP

---

**Status:** ✅ Fase de DAO e Testes Concluída  
**Próxima Fase:** Implementação de update(), delete() e interface gráfica
