-- Tabela Cliente (mantida como no exemplo)
CREATE TABLE Cliente (
    Id_Cliente SERIAL PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    CPF VARCHAR(11),
    Telefone VARCHAR(20),
    Endereco VARCHAR(20),
    DataDeCadastro VARCHAR(20)
);

-- Tabela Funcionario (mantida como no exemplo)
CREATE TABLE Funcionario (
    Id_Funcionario SERIAL PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    CPF VARCHAR(11),
    Cargo VARCHAR(50),
    Telefone VARCHAR(20),
    Data_Admissao VARCHAR(20)
);

-- Tabela Produto (corrigida conforme exemplo)
CREATE TABLE produto (
    Id_Produto SERIAL PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    Descricao VARCHAR(200),
    Preco DECIMAL(10,2),
    Quantidade_estoque VARCHAR(20),
    Categoria VARCHAR(50),
    Marca VARCHAR(50)
);

-- Tabela Venda (mantida como no exemplo)
CREATE TABLE Venda (
    Id_Venda SERIAL PRIMARY KEY,
    Data VARCHAR(100),
    Valor_Total DECIMAL(10,2),
    Forma_Pagamento VARCHAR(20),
    Desconto DECIMAL(10,2),
    Id_Cliente INTEGER,
    Id_Funcionario INTEGER,
    CONSTRAINT fk_cliente FOREIGN KEY (Id_Cliente) REFERENCES Cliente(Id_Cliente),
    CONSTRAINT fk_funcionario FOREIGN KEY (Id_Funcionario) REFERENCES Funcionario(Id_Funcionario)
);

-- Tabela Orcamento (corrigida conforme exemplo)
CREATE TABLE Orcamento (
    Id_Orcamento SERIAL PRIMARY KEY,
    Data VARCHAR(100),
    Valor_Total DECIMAL(10,2),
    Status VARCHAR(20),
    Observacoes VARCHAR(200),
    Id_Cliente INTEGER,
    CONSTRAINT fk_cliente_orc FOREIGN KEY (Id_Cliente) REFERENCES Cliente(Id_Cliente)
);

-- Tabela itens_Venda (corrigida conforme exemplo)
CREATE TABLE itens_Venda (
    Id_Venda INTEGER,
    Id_Produto INTEGER,
    Quantidade VARCHAR(20),
    Preco_unitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    PRIMARY KEY (Id_Venda, Id_Produto),
    FOREIGN KEY (Id_Venda) REFERENCES Venda(Id_Venda),
    FOREIGN KEY (Id_Produto) REFERENCES produto(Id_Produto)
);

-- Tabela itens_Orcamento (corrigida conforme exemplo)
CREATE TABLE itens_Orcamento (
    Id_Orcamento INTEGER,
    Id_Produto INTEGER,
    Quantidade VARCHAR(20),
    Preco_unitario DECIMAL(10,2),
    Subtotal DECIMAL(10,2),
    PRIMARY KEY (Id_Orcamento, Id_Produto),
    FOREIGN KEY (Id_Orcamento) REFERENCES Orcamento(Id_Orcamento),
    FOREIGN KEY (Id_Produto) REFERENCES produto(Id_Produto)
);

-- Tabela Estoque (corrigida conforme exemplo)
CREATE TABLE estoque (
    Nome VARCHAR(100) NOT NULL,
    Id_Produto INTEGER,
    Quantidade_Estoque VARCHAR(20),
    Preco DECIMAL(10,2),
    Descricao VARCHAR(200),
    Categoria VARCHAR(50),
    Marca VARCHAR(50),
    Tipo VARCHAR(50),
    PRIMARY KEY (Id_Produto),
    FOREIGN KEY (Id_Produto) REFERENCES produto(Id_Produto)
);
-- INSERÇÕES DE DADOS FICTÍCIOS

-- Corrigindo a estrutura da tabela Cliente (aumentando o tamanho do campo Endereco)
ALTER TABLE Cliente ALTER COLUMN Endereco TYPE VARCHAR(100);

-- Inserindo clientes (com endereços dentro do limite)
INSERT INTO Cliente (Nome, CPF, Telefone, Endereco, DataDeCadastro) VALUES
('Roberto Carlos', '12345678901', '11999998888', 'Av. Auto, 123', '2023-01-15'),
('Márcio Silva', '98765432109', '21988887777', 'R. Motores, 456', '2023-02-20'),
('Fernanda Oliveira', '45678912304', '31977776666', 'Tv. Rodas, 789', '2023-03-10');

-- Inserindo funcionários
INSERT INTO Funcionario (Nome, CPF, Cargo, Telefone, Data_Admissao) VALUES
('José Almeida', '11122233344', 'Vendedor', '11999995555', '2022-05-10'),
('Patrícia Santos', '55544433322', 'Gerente', '21988884444', '2021-11-15'),
('Ricardo Mendes', '99988877766', 'Mecânico', '31977773333', '2023-01-05');

-- Inserindo produtos automotivos
INSERT INTO produto (Nome, Descricao, Preco, Quantidade_estoque, Categoria, Marca) VALUES
('Kit Pastilha Freio', 'Kit freio dianteiro', 189.90, '50', 'Freios', 'Bosch'),
('Óleo 5W30', 'Óleo sintético 1L', 39.90, '100', 'Óleos', 'Mobil'),
('Amortecedor', 'Amort. dianteiro', 299.90, '15', 'Suspensão', 'Monroe'),
('Bateria 60Ah', 'Bateria selada', 349.90, '20', 'Elétrica', 'Moura'),
('Filtro de Ar', 'Filtro ar 1.0', 29.90, '75', 'Filtros', 'Mahle');

-- Inserindo em estoque
INSERT INTO estoque (Nome, Id_Produto, Quantidade_Estoque, Preco, Descricao, Categoria, Marca, Tipo) VALUES
('Kit Pastilha', 1, '50', 189.90, 'Kit freio', 'Freios', 'Bosch', 'Peça'),
('Óleo 5W30', 2, '100', 39.90, 'Óleo 1L', 'Óleos', 'Mobil', 'Consumo'),
('Amortecedor', 3, '15', 299.90, 'Amort. diant.', 'Suspensão', 'Monroe', 'Peça'),
('Bateria 60Ah', 4, '20', 349.90, 'Bateria', 'Elétrica', 'Moura', 'Peça'),
('Filtro de Ar', 5, '75', 29.90, 'Filtro ar', 'Filtros', 'Mahle', 'Consumo');

-- Inserindo vendas
INSERT INTO Venda (Data, Valor_Total, Forma_Pagamento, Desconto, Id_Cliente, Id_Funcionario) VALUES
('2023-04-01', 229.80, 'Cartão', 0.00, 1, 1),
('2023-04-02', 539.80, 'Pix', 30.00, 2, 2),
('2023-04-03', 379.80, 'Dinheiro', 20.00, 3, 3);

-- Inserindo itens de venda
INSERT INTO itens_Venda (Id_Venda, Id_Produto, Quantidade, Preco_unitario, Subtotal) VALUES
(1, 2, '2', 39.90, 79.80),
(1, 5, '1', 29.90, 29.90),
(2, 1, '1', 189.90, 189.90),
(2, 3, '1', 299.90, 299.90),
(3, 4, '1', 349.90, 349.90),
(3, 2, '1', 39.90, 39.90);

-- Inserindo orçamentos
INSERT INTO Orcamento (Data, Valor_Total, Status, Observacoes, Id_Cliente) VALUES
('2023-03-28', 789.70, 'Aprovado', 'Troca de freios', 1),
('2023-03-29', 1299.60, 'Pendente', 'Revisão 40k km', 2),
('2023-03-30', 449.80, 'Cancelado', 'Outro modelo', 3);

-- Inserindo itens de orçamento
INSERT INTO itens_Orcamento (Id_Orcamento, Id_Produto, Quantidade, Preco_unitario, Subtotal) VALUES
(1, 1, '2', 189.90, 379.80),
(1, 2, '2', 39.90, 79.80),
(2, 1, '2', 189.90, 379.80),
(2, 3, '2', 299.90, 599.80),
(3, 4, '1', 349.90, 349.90);
