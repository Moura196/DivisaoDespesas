# Divisor de Contas Familiar / Family Expense Splitter

## 📱 Sobre o Projeto (Português)
O **Divisor de Contas Familiar** é um aplicativo Android desenvolvido em **Java** durante minha pós-graduação, com o objetivo de simplificar a gestão de despesas em habitações compartilhadas.  
Ele elimina a necessidade de anotações em papel e cálculos manuais, fornecendo o valor exato que cada morador ou casal deve reembolsar ao comprador original.

---

## ✨ Funcionalidades
- **Cadastro de Moradores/Grupos**: registro de pessoas vinculadas a núcleos familiares (ex: Casal A, Casal B).  
- **Lançamento de Compras/Contas**: descrição, valor, data e quem realizou o pagamento.  
- **Classificação de Itens/Rateio**: despesas gerais ou exclusivas de um núcleo.  
- **Resumo de Fechamento**: cálculo automático do saldo devedor/credor.  
- **Histórico Offline**: consulta de lançamentos armazenados localmente.  

---

## 🛠️ Tecnologias
- Android Studio Otter 3 (Feature Drop 2025.2.3)  
- Java  
- Gradle (versão compatível com Android Studio)  
- targetSDK: API Level 36 (Android 16.0)  
- minSDK: API Level 24  

---

## 🚀 Status do Projeto
### Entrega 1
- **Cadastro de Morador**: Activity com formulário usando TextView, EditText, RadioButton, CheckBox, Spinner, ScrollView e validação com Toast.  

### Entrega 2
- **Nova Entidade: Lançamento**  
  - `id_lancamento`  
  - `descricao`  
  - `valor_total`  
  - `data`  
  - `id_morador_comprador`  
  - `tipo_rateio` (checkbox → se selecionado, é uma conta fixa dividida igualmente)  
  - `itens` (lista de itens, usada apenas em compras de mercado)  

- **Nova Entidade: Item**  
  - `id_item`  
  - `descricao_item`  
  - `quantidade`  
  - `valor_unitario`  
  - `valor_total_item`  
  - `tipo_rateio` (checkbox → se selecionado, dividido por casal)  
  - `núcleo_familiar` (spinner → habilitado apenas se o checkbox acima for selecionado)  

- **Data Source**: Arrays em `res/values/arrays.xml` com pelo menos 10 lançamentos simulados.  
- **ArrayList<Lancamento>**: armazenamento dos objetos instanciados.  
- **Activity Principal (Launcher)**: Listagem de Lançamentos usando **RecyclerView**.  
- **Adapter Customizado**: exibe dados de cada Lançamento.  
- **Item Click**: Toast exibindo informações do Lançamento clicado.

### Entrega 3
- **Entidade Morador Expandida**:  
  - `id_morador`  
  - `nome`  
  - `genero` (Enum)  
  - `grupo_familiar` (spinner de grupos familiares)  
  - `responsavel_contas` (checkbox → identifica morador responsável por pagar contas)  

- **Entidade Lancamento Consolidada**:  
  - `id_lancamento`  
  - `descricao`  
  - `valor_total`  
  - `data`  
  - `morador_comprador` (spinner listando moradores cadastrados)  
  - `tipo_lancamento` (checkbox → true = conta de casa, false = compra de mercado)  

- **CadastroMoradorActivity**: Activity com validação de entrada e formulário completo para registro de moradores.  

- **CadastroLancamentoActivity**: Activity com validação de entrada (descrição, valor positivo, data em formato dd/MM/yyyy) e formulário completo para registro de lançamentos.  

- **MoradoresActivity**: Listagem de Moradores usando **RecyclerView** com dados carregados a partir de `res/values/arrays.xml`.  

- **LancamentosActivity**: Listagem de Lançamentos usando **RecyclerView** com listeners de clique simples e longo que exibem Toast com informações do lançamento.  

- **Adapters Customizados**:  
  - `MoradorRecyclerViewAdapter`: exibe dados de cada Morador na lista.  
  - `LancamentoRecyclerViewAdapter`: exibe dados de cada Lançamento na lista com interface de listener para interações.  

- **SobreActivity**: Tela "Sobre" do aplicativo acessível a partir da Activity principal.  

- **Data Source**: Arrays em `res/values/arrays.xml` com dados simulados de moradores e lançamentos.  

- **Navegação**: Menu de opções e botões para navegação entre Cadastros, Listagens e Sobre.  

---

## 📱 About the Project (English)
**Family Expense Splitter** is an Android app developed in **Java** during my postgraduate studies, designed to simplify expense management in shared households.  
It removes the need for paper notes and manual calculations, providing the exact amount each resident or couple should reimburse to the original payer.

---

## ✨ Features
- **Resident/Group Registration**: link people to family groups (e.g., Couple A, Couple B).  
- **Expense Entry (Lançamento)**: record description, value, date, and payer.  
- **Item Classification**: mark as shared (general) or exclusive to one group.  
- **Summary Screen**: automatic calculation of balances.  
- **Offline History**: local storage of all entries.  

---

## 🚀 Project Status
### Delivery 1
- **Resident Registration**: Activity with form using TextView, EditText, RadioButton, CheckBox, Spinner, ScrollView, and validation with Toast.  

### Delivery 2
- **New Entity: Lançamento**  
  - `id_lancamento`  
  - `descricao`  
  - `valor_total`  
  - `data`  
  - `id_morador_comprador`  
  - `tipo_rateio` (checkbox → if selected, household bill divided equally)  
  - `itens` (list of items, only for market purchases)  

- **New Entity: Item**  
  - `id_item`  
  - `descricao_item`  
  - `quantidade`  
  - `valor_unitario`  
  - `valor_total_item`  
  - `tipo_rateio` (checkbox → if selected, divided by couple)  
  - `núcleo_familiar` (spinner → enabled only if checkbox is selected)  

- **Data Source**: Arrays in `res/values/arrays.xml` with at least 10 sample entries.  
- **ArrayList<Lancamento>**: stores instantiated objects.  
- **Main Activity (Launcher)**: List of Lançamentos using **RecyclerView**.  
- **Custom Adapter**: displays data for each Lançamento.  
- **Item Click**: Toast showing details of the clicked Lançamento.

### Delivery 3
- **Expanded Morador Entity**:  
  - `id_morador`  
  - `nome`  
  - `genero` (Enum)  
  - `grupo_familiar` (spinner with family groups)  
  - `responsavel_contas` (checkbox → identifies resident responsible for paying bills)  

- **Consolidated Lancamento Entity**:  
  - `id_lancamento`  
  - `descricao`  
  - `valor_total`  
  - `data`  
  - `morador_comprador` (spinner listing registered residents)  
  - `tipo_lancamento` (checkbox → true = household bill, false = market purchase)  

- **CadastroMoradorActivity**: Activity with input validation and complete form for resident registration.  

- **CadastroLancamentoActivity**: Activity with input validation (description, positive value, date format dd/MM/yyyy) and complete form for expense entry.  

- **MoradoresActivity**: Resident List using **RecyclerView** with data loaded from `res/values/arrays.xml`.  

- **LancamentosActivity**: Expense List using **RecyclerView** with single and long click listeners that display Toast messages with expense information.  

- **Custom Adapters**:  
  - `MoradorRecyclerViewAdapter`: displays data for each resident in the list.  
  - `LancamentoRecyclerViewAdapter`: displays data for each expense in the list with listener interface for interactions.  

- **SobreActivity**: About screen accessible from the main Activity.  

- **Data Source**: Arrays in `res/values/arrays.xml` with simulated resident and expense data.  

- **Navigation**: Menu options and buttons for navigation between Registrations, Lists, and About.  