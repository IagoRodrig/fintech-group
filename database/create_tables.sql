-- Script SQL para criação das tabelas do Sistema FINTECH
-- Oracle Database
-- Execute este script antes de rodar os testes

-- ==================================================
-- TABELA: Usuario
-- ==================================================
CREATE TABLE Usuario (
    id_usuario RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    nome_usuario VARCHAR2(25) UNIQUE NOT NULL,
    nome_completo VARCHAR2(100) NOT NULL,
    telefone VARCHAR2(20),
    senha VARCHAR2(100) NOT NULL,
    data_criacao DATE DEFAULT SYSDATE
);

-- ==================================================
-- TABELA: Conta
-- ==================================================
CREATE TABLE Conta (
    id_conta NUMBER PRIMARY KEY,
    id_usuario RAW(16) NOT NULL,
    saldo NUMBER(15,2) DEFAULT 0,
    tipo_conta VARCHAR2(50) NOT NULL,
    valor NUMBER(15,2) DEFAULT 0,
    data_criacao DATE DEFAULT SYSDATE,
    CONSTRAINT fk_conta_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- ==================================================
-- TABELA: Cartao
-- ==================================================
CREATE TABLE Cartao (
    id_cartao NUMBER PRIMARY KEY,
    id_conta NUMBER NOT NULL,
    tipo_cartao VARCHAR2(50) NOT NULL,
    numero_mascarado VARCHAR2(20) NOT NULL,
    validade VARCHAR2(10) NOT NULL,
    limite_credito NUMBER(15,2) DEFAULT 0,
    CONSTRAINT fk_cartao_conta FOREIGN KEY (id_conta) REFERENCES Conta(id_conta)
);

-- Primeiro: Adicionar colunas para identificar transações de cartão
ALTER TABLE Transacao ADD (
    id_cartao NUMBER,
    tipo_transacao VARCHAR2(50) DEFAULT 'TRANSFERENCIA',
    descricao VARCHAR2(200)
);

-- Segundo: Adicionar constraint para cartão (após criar a coluna)
ALTER TABLE Transacao ADD CONSTRAINT fk_transacao_cartao 
FOREIGN KEY (id_cartao) REFERENCES Cartao(id_cartao);

-- ==================================================
-- TABELA: Transacao
-- ==================================================
CREATE TABLE Transacao (
    id_transacao RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    id_conta_origem NUMBER NOT NULL,
    id_conta_destino NUMBER,
    valor NUMBER(15,2) NOT NULL,
    data_transacao DATE DEFAULT SYSDATE,
    CONSTRAINT fk_transacao_origem FOREIGN KEY (id_conta_origem) REFERENCES Conta(id_conta),
    CONSTRAINT fk_transacao_destino FOREIGN KEY (id_conta_destino) REFERENCES Conta(id_conta)
);

-- ==================================================
-- TABELA: Investimento
-- ==================================================
CREATE TABLE Investimento (
    id_investimento NUMBER PRIMARY KEY,
    id_usuario RAW(16) NOT NULL,
    tipo VARCHAR2(100) NOT NULL,
    valor_investido NUMBER(15,2) NOT NULL,
    data_aplicacao DATE DEFAULT SYSDATE,
    CONSTRAINT fk_investimento_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- ==================================================
-- TABELA: Recompensa (Bônus)
-- ==================================================
CREATE TABLE Recompensa (
    id_bonus NUMBER PRIMARY KEY,
    id_usuario RAW(16) NOT NULL,
    descricao VARCHAR2(200) NOT NULL,
    valor NUMBER(15,2) NOT NULL,
    status VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_recompensa_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- ==================================================
-- Inserir alguns usuários de teste
-- ==================================================
INSERT INTO Usuario (nome_completo, nome_usuario, telefone, senha, data_criacao)
VALUES ('João Silva', 'joao.silva', '11987654321', 'senha123', SYSDATE);

INSERT INTO Usuario (nome_usuario, nome_completo, telefone, senha, data_criacao)
VALUES ('Maria Santos', 'maria.santos', '11976543210', 'senha456', SYSDATE);

INSERT INTO Usuario (nome_usuario, nome_completo, telefone, senha, data_criacao)
VALUES ('Pedro Oliveira', 'pedro.oliveira', '11965432109', 'senha789', SYSDATE);

COMMIT;

-- ==================================================
-- Verificar se as tabelas foram criadas
-- ==================================================
SELECT 'Tabela ' || table_name || ' criada com sucesso!' AS status
FROM user_tables
WHERE table_name IN ('USUARIO', 'CONTA', 'CARTAO', 'TRANSACAO', 'INVESTIMENTO', 'RECOMPENSA')
ORDER BY table_name;

SELECT nome_usuario from Usuario