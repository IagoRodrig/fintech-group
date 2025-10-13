# ✅ Checklist de Configuração - Sistema FINTECH

Use este checklist para garantir que tudo está configurado corretamente antes de executar os testes.

---

## 📋 Pré-Requisitos

### 1. Java Development Kit
- [x] Java 17 ou superior instalado
- [ ] Variável de ambiente JAVA_HOME configurada
- [ ] Comando `java -version` funciona no terminal

### 2. Driver JDBC Oracle
- [ ] Baixei o arquivo ojdbc8.jar ou ojdbc11.jar
- [ ] Adicionei o driver ao classpath do projeto
- [ ] (IntelliJ) Adicionei como Library nas configurações do projeto

### 3. Acesso ao Banco Oracle FIAP
- [ ] Tenho meu RM (usuário)
- [ ] Tenho minha senha do Oracle
- [ ] Consigo conectar usando SQL Developer ou SQL*Plus

---

## ⚙️ Configuração do Projeto

### Passo 1: Credenciais de Banco de Dados
- [ ] Abri o arquivo `src/dao/ConnectionFactory.java`
- [ ] Substitui `"seu_usuario"` pelo meu RM
- [ ] Substitui `"sua_senha"` pela minha senha Oracle
- [ ] Salvei o arquivo

**Arquivo:** `src/dao/ConnectionFactory.java`
```java
private static final String USUARIO = "RM123456"; // ⬅️ SEU RM AQUI
private static final String SENHA = "SuaSenha123";  // ⬅️ SUA SENHA AQUI
```

### Passo 2: Criar Tabelas no Banco
- [ ] Conectei ao Oracle usando SQL Developer/SQL*Plus
- [ ] Abri o arquivo `database/create_tables.sql`
- [ ] Executei o script completo
- [ ] Verifiquei que as 6 tabelas foram criadas:
  - [ ] Usuario
  - [ ] Conta
  - [ ] Cartao
  - [ ] Transacao
  - [ ] Investimento
  - [ ] Recompensa

**Verificação:**
```sql
SELECT table_name FROM user_tables 
WHERE table_name IN ('USUARIO', 'CONTA', 'CARTAO', 'TRANSACAO', 'INVESTIMENTO', 'RECOMPENSA');
```

Deve retornar 6 tabelas.

### Passo 3: Compilar o Projeto
- [ ] Compilei todos os arquivos Java

**Via Terminal:**
```bash
# Linux/Mac
javac -cp .:ojdbc8.jar -d out src/**/*.java

# Windows
javac -cp .;ojdbc8.jar -d out src/**/*.java
```

**Via IDE:**
- [ ] Build > Build Project (IntelliJ)
- [ ] Project > Build All (Eclipse)

---

## 🧪 Executar Testes

### Opção 1: Teste Completo (Recomendado)
- [ ] Executei: `java test.TesteCompleto`
- [ ] Todos os 5 DAOs foram testados
- [ ] Nenhum erro de conexão ocorreu
- [ ] Registros foram inseridos com sucesso

### Opção 2: Testes Individuais
- [ ] Testei ContaDAO: `java test.TesteContaDAO`
- [ ] Testei CartaoDAO: `java test.TesteCartaoDAO`
- [ ] Testei TransacaoDAO: `java test.TesteTransacaoDAO`
- [ ] Testei InvestimentoDAO: `java test.TesteInvestimentoDAO`
- [ ] Testei RecompensaDAO: `java test.TesteRecompensaDAO`

---

## ✅ Verificação de Sucesso

Após executar os testes, você deve ver:

### Mensagens de Sucesso Esperadas:
```
✅ "Conta inserida com sucesso! ID: X"
✅ "Consulta realizada com sucesso! Total de contas: 5"
✅ "Conta encontrada: Conta{...}"
✅ "TESTES CONCLUÍDOS!"
```

### Verificar no Banco de Dados:
```sql
-- Deve retornar 5 registros de cada
SELECT COUNT(*) FROM Conta;      -- Deve ser >= 5
SELECT COUNT(*) FROM Cartao;     -- Deve ser >= 5
SELECT COUNT(*) FROM Transacao;  -- Deve ser >= 5
SELECT COUNT(*) FROM Investimento; -- Deve ser >= 5
SELECT COUNT(*) FROM Recompensa; -- Deve ser >= 5
```

---

## 🐛 Troubleshooting

### ❌ Erro: "Driver Oracle JDBC não encontrado"
**Causa:** ojdbc.jar não está no classpath

**Solução:**
- [ ] Verifiquei que o arquivo ojdbc8.jar existe
- [ ] Adicionei ao classpath manualmente: `-cp .:ojdbc8.jar`
- [ ] (IntelliJ) Adicionei como Library no projeto

---

### ❌ Erro: "ORA-01017: invalid username/password"
**Causa:** Credenciais incorretas

**Solução:**
- [ ] Verifiquei meu RM e senha
- [ ] Testei login no SQL Developer primeiro
- [ ] Atualizei ConnectionFactory.java com credenciais corretas

---

### ❌ Erro: "ORA-00942: table or view does not exist"
**Causa:** Tabelas não foram criadas

**Solução:**
- [ ] Executei o script create_tables.sql
- [ ] Verifiquei com: `SELECT * FROM user_tables;`
- [ ] Conferi se estou conectado ao schema correto

---

### ❌ Erro: "ORA-00001: unique constraint violated"
**Causa:** Tentando inserir ID duplicado

**Solução:**
- [ ] Limpei os dados: `DELETE FROM Conta; COMMIT;`
- [ ] Executei os testes novamente
- [ ] Ou mudei os IDs nos testes para valores únicos

---

### ❌ Erro: "ORA-02291: integrity constraint violated"
**Causa:** Usuários não existem na tabela Usuario

**Solução:**
- [ ] Executei os INSERTs de usuários do script SQL
- [ ] Verifiquei: `SELECT * FROM Usuario;`
- [ ] Deve ter pelo menos 3 usuários (IDs 1, 2, 3)

---

## 📊 Resultados Esperados

Ao final, você deve ter:

### No Banco de Dados:
- ✅ 3 usuários cadastrados (do script SQL)
- ✅ 5 contas cadastradas (dos testes)
- ✅ 5 cartões cadastrados (dos testes)
- ✅ 5 transações cadastradas (dos testes)
- ✅ 5 investimentos cadastrados (dos testes)
- ✅ 5 recompensas cadastradas (dos testes)

### No Console:
- ✅ Mensagens de sucesso para todas as operações
- ✅ Lista de registros exibida corretamente
- ✅ Nenhum erro de SQLException

---

## 🎯 Próximos Passos

Depois de concluir todos os checkpoints:

1. [ ] Li o arquivo ANALISE_TECNICA.md para entender melhorias possíveis
2. [ ] Considerei implementar métodos update() e delete()
3. [ ] Pensei em adicionar validações nas entidades
4. [ ] Planejei a interface gráfica (Swing/JavaFX) para a Fase Final

---

## 📞 Suporte

Se todos os checks estão ✅ mas ainda há problemas:
1. Verifique os logs de erro completos
2. Confirme a conectividade de rede com o Oracle FIAP
3. Teste a conexão com SQL Developer primeiro
4. Revise o arquivo INSTRUCOES.md

---

**Boa sorte com seu projeto! 🚀**

