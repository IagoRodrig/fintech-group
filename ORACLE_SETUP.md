# 🚀 CONFIGURAÇÃO ORACLE DATABASE - FIAP

## 📋 **PASSO A PASSO PARA CONECTAR NO ORACLE**

### **1️⃣ BAIXAR O DRIVER ORACLE JDBC**

**Opção A - Download Manual (Recomendado):**

1. Acesse: https://www.oracle.com/database/technologies/maven-central-guide.html
2. Procure por "ojdbc17.jar" ou "ojdbc21.jar"
3. Baixe o arquivo JAR
4. Coloque o arquivo na pasta `lib/` do seu projeto

**Opção B - Maven Central:**

1. Acesse: https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc17
2. Baixe a versão mais recente (ex: ojdbc17-21.9.0.0.jar)
3. Coloque o arquivo na pasta `lib/`

### **2️⃣ CONFIGURAR O CLASSPATH**

**No Windows (CMD):**

```cmd
set CLASSPATH=%CLASSPATH%;lib\ojdbc17.jar
```

**No Windows (PowerShell):**

```powershell
$env:CLASSPATH += ";lib\ojdbc17.jar"
```

**Compilar com classpath:**

```cmd
javac -cp "src;lib\ojdbc17.jar" src/view/Main.java
```

**Executar com classpath:**

```cmd
java -cp "src;lib\ojdbc17.jar" view.Main
```

### **3️⃣ CONFIGURAÇÕES ATUAIS**

- **URL:** `jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL`
- **Usuário:** `rm561399`
- **Senha:** `131106`
- **Driver:** `ojdbc17.jar`

### **4️⃣ TESTAR A CONEXÃO**

O sistema já está configurado para usar Oracle. Para testar:

```cmd
java -cp "src;lib\ojdbc17.jar" dao.TesteConexao
```

### **5️⃣ EXECUTAR O PROGRAMA**

```cmd
java -cp "src;lib\ojdbc17.jar" view.Main
```

### **6️⃣ TROUBLESHOOTING**

**Erro: "Driver Oracle JDBC não encontrado"**

- Verifique se o arquivo `ojdbc17.jar` está na pasta `lib/`
- Verifique se o classpath está configurado corretamente

**Erro: "Conexão recusada"**

- Verifique suas credenciais FIAP
- Verifique se o servidor Oracle está acessível

**Erro: "Tabela não encontrada"**

- As tabelas já existem no Oracle (conforme informado)
- Verifique se você tem permissões para acessar as tabelas

### **7️⃣ COMANDOS ÚTEIS**

**Compilar tudo:**

```cmd
javac -cp "src;lib\ojdbc17.jar" src/view/Main.java
```

**Executar:**

```cmd
java -cp "src;lib\ojdbc17.jar" view.Main
```

**Testar conexão:**

```cmd
java -cp "src;lib\ojdbc17.jar" dao.TesteConexao
```

---

## ✅ **STATUS ATUAL**

- ✅ ConnectionFactory configurado para Oracle
- ✅ Credenciais FIAP configuradas
- ✅ UsuarioDAO configurado para JDBC
- ✅ Driver Oracle JDBC 17 configurado

**Próximo passo:** Execute o programa com `java -cp "src;lib\ojdbc17.jar" view.Main`

## 📝 **NOTAS IMPORTANTES**

- As tabelas já existem no Oracle (não precisa executar scripts)
- O arquivo `database/create_tables.sql` é apenas para referência da estrutura
- Todas as operações serão feitas diretamente no banco Oracle
- Os dados serão persistentes no banco da FIAP
- **Usando Oracle JDBC Driver 17 (ojdbc17.jar)**
