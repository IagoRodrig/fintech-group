# 📋 Instruções de Configuração e Uso - Sistema FINTECH

## 📦 Pré-requisitos

### 1. Java Development Kit (JDK)
- **Versão:** Java 17 ou superior
- Verifique a instalação: `java -version`

### 2. Driver JDBC Oracle
- **Download:** [Oracle JDBC Driver (ojdbc8.jar ou ojdbc11.jar)](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html)
- **Localização:** Adicione o arquivo `.jar` ao classpath do projeto

### 3. Acesso ao Banco de Dados Oracle FIAP
- **Hostname:** oracle.fiap.com.br
- **Porta:** 1521
- **SID:** ORCL
- **Usuário:** Seu RM da FIAP
- **Senha:** Sua senha do Oracle

---

## ⚙️ Configuração

### Passo 1: Configurar as Credenciais de Acesso

Abra o arquivo `src/dao/ConnectionFactory.java` e edite as seguintes linhas:

```java
private static final String USUARIO = "seu_usuario"; // SUBSTITUA pelo seu RM
private static final String SENHA = "sua_senha";     // SUBSTITUA pela sua senha
```

**Exemplo:**
```java
private static final String USUARIO = "RM123456";
private static final String SENHA = "MinhaSenh@123";
```

### Passo 2: Adicionar o Driver JDBC ao Projeto

#### Opção A: Usando IntelliJ IDEA
1. Clique com o botão direito no projeto
2. **Open Module Settings** → **Libraries**
3. Clique no **+** → **Java**
4. Selecione o arquivo `ojdbc8.jar` ou `ojdbc11.jar`
5. Clique em **OK**

#### Opção B: Via Linha de Comando
```bash
# Compile com o driver no classpath
javac -cp .:ojdbc8.jar src/**/*.java

# Execute com o driver no classpath
java -cp .:ojdbc8.jar:src test.TesteCompleto
```

### Passo 3: Criar as Tabelas no Banco de Dados

1. Conecte-se ao Oracle usando SQL Developer, SQL*Plus ou outra ferramenta
2. Execute o script: `database/create_tables.sql`
3. Verifique se as tabelas foram criadas com sucesso

---

## 🚀 Executando os Testes

### Opção 1: Testar Todas as Entidades de Uma Vez

```bash
java test.TesteCompleto
```

Este comando executará os testes para todas as entidades: Conta, Cartão, Transação, Investimento e Recompensa.

### Opção 2: Testar Entidades Individualmente

```bash
# Testar Contas
java test.TesteContaDAO

# Testar Cartões
java test.TesteCartaoDAO

# Testar Transações
java test.TesteTransacaoDAO

# Testar Investimentos
java test.TesteInvestimentoDAO

# Testar Recompensas
java test.TesteRecompensaDAO
```

---

## 📁 Estrutura do Projeto

```
fintech-group/
├── src/
│   ├── dao/                          # Data Access Objects
│   │   ├── ConnectionFactory.java    # Gerenciamento de conexões
│   │   ├── ContaDAO.java            # Operações de Conta
│   │   ├── CartaoDAO.java           # Operações de Cartão
│   │   ├── TransacaoDAO.java        # Operações de Transação
│   │   ├── InvestimentoDAO.java     # Operações de Investimento
│   │   └── RecompensaDAO.java       # Operações de Recompensa
│   │
│   ├── model/                        # Modelos de dados
│   │   ├── Conta.java
│   │   ├── Cartao.java
│   │   ├── Transacao.java
│   │   ├── Investimento.java
│   │   ├── Recompensa.java
│   │   ├── Bonus.java
│   │   └── Usuario.java
│   │
│   └── test/                         # Classes de teste
│       ├── TesteCompleto.java        # Suite completa de testes
│       ├── TesteContaDAO.java
│       ├── TesteCartaoDAO.java
│       ├── TesteTransacaoDAO.java
│       ├── TesteInvestimentoDAO.java
│       └── TesteRecompensaDAO.java
│
├── database/
│   └── create_tables.sql             # Script de criação das tabelas
│
└── INSTRUCOES.md                     # Este arquivo
```

---

## 🔍 Funcionalidades Implementadas

### Cada DAO possui os seguintes métodos:

1. **`getAll()`** - Retorna todos os registros da tabela
   - Executa um `SELECT *` no banco
   - Retorna uma `List<Entidade>`
   - Inclui tratamento de exceções

2. **`insert(entidade)`** - Insere um novo registro
   - Executa um `INSERT` no banco
   - Retorna `boolean` (true se sucesso)
   - Inclui tratamento de exceções com mensagens específicas

3. **`findById(id)`** - Busca um registro por ID
   - Executa um `SELECT WHERE id = ?`
   - Retorna o objeto encontrado ou `null`
   - Útil para consultas específicas

---

## 🛡️ Tratamento de Exceções

Todos os DAOs implementam tratamento robusto de exceções para:

- **Conexão indisponível:** Verifica se o banco está acessível
- **Tabela não encontrada (ORA-00942):** Alerta se a tabela não existe
- **Chave primária duplicada (ORA-00001):** Evita inserções duplicadas
- **Chave estrangeira inválida (ORA-02291):** Valida relacionamentos

---

## ✅ Exemplo de Saída dos Testes

```
==========================================
TESTE: ContaDAO - Sistema FINTECH
==========================================

>>> TESTE 1: Inserindo 5 contas no banco de dados
------------------------------------------
Conta inserida com sucesso! ID: 1
Conta inserida com sucesso! ID: 2
Conta inserida com sucesso! ID: 3
Conta inserida com sucesso! ID: 4
Conta inserida com sucesso! ID: 5

>>> TESTE 2: Consultando todas as contas cadastradas
------------------------------------------
Consulta realizada com sucesso! Total de contas: 5

Lista de Contas:
==========================================
Conta{idConta=1, idUsuario=1, saldo=5000.0, tipoConta='Corrente', valor=5000.0, data='2024-01-15'}
Conta{idConta=2, idUsuario=1, saldo=10000.0, tipoConta='Poupança', valor=10000.0, data='2024-01-20'}
...
==========================================
TESTES CONCLUÍDOS!
==========================================
```

---

## 🐛 Solução de Problemas

### Erro: "Driver Oracle JDBC não encontrado"
- Verifique se o arquivo `ojdbc.jar` está no classpath
- Certifique-se de usar a versão compatível com seu Java

### Erro: "ORA-01017: invalid username/password"
- Verifique as credenciais em `ConnectionFactory.java`
- Confirme se você consegue conectar usando SQL Developer

### Erro: "ORA-00942: table or view does not exist"
- Execute o script `create_tables.sql` primeiro
- Verifique se as tabelas foram criadas: `SELECT * FROM user_tables;`

### Erro: "ORA-02291: integrity constraint violated"
- Certifique-se de que existem usuários cadastrados na tabela `Usuario`
- Execute os INSERTs de usuários do script SQL

---

## 📝 Observações Importantes

1. **Não foi implementado DAO para a entidade `Usuario`** conforme solicitado nas instruções
2. **IDs devem ser únicos** - Os testes usam IDs sequenciais (1, 2, 3, etc.)
3. **Datas em formato String** - Para simplificar, as datas são armazenadas como VARCHAR2
4. **Transações do banco** - As operações são auto-commit por padrão
5. **Código preparado para expansão** - Fácil adicionar métodos `update()` e `delete()`

---

## 🎯 Próximos Passos (Fase Final)

- Implementar métodos `update()` e `delete()` em todos os DAOs
- Adicionar validações de negócio
- Implementar interface gráfica (Swing/JavaFX)
- Adicionar autenticação de usuários
- Implementar relatórios e dashboards
- Adicionar testes unitários (JUnit)

---

## 👨‍💻 Suporte

Se encontrar problemas:
1. Verifique as configurações de conexão
2. Confirme se o banco de dados está acessível
3. Revise os logs de erro no console
4. Consulte a documentação do Oracle JDBC

---

**Desenvolvido para o projeto FINTECH - FIAP**

