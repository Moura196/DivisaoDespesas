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

### Entrega 4
- **ActionMode para Contexto**: Implementação de menu contextual com long-press.  

- **Edição de Lançamentos**:  
  - Clique simples no item da lista abre o formulário de edição pré-preenchido.  
  - Após edição, a lista é atualizada e reordenada automaticamente.  

- **Exclusão de Lançamentos**:  
  - Long-press em um item ativa o **ActionMode** com visual destacado (background cinza).  
  - Menu contextual exibe opções "Editar" e "Excluir" com ícones.  
  - Exclusão remove o item da lista e atualiza a RecyclerView.  

- **Menu de Ações (Action Menu)**:  
  - Menu superior em **LancamentosActivity** com opções:  
    - **Adicionar**: abre **CadastroLancamentoActivity** para novo lançamento.  
    - **Sobre**: acessa **SobreActivity**.  
  - Ícones visuais para melhor UX.  

- **Ordenação Automática**:  
  - Lançamentos são ordenados em **ordem crescente por data** usando `Comparator`.  
  - Ordenação ocorre após adicionar ou editar um lançamento.  

- **ActivityResultLauncher**:  
  - Substituição de `startActivityForResult()` (deprecated) por **ActivityResultLauncher**.  
  - Launchers separados para criação (`launcherNovoLancamento`) e edição (`launcherEditarLancamento`).  

- **Validações e Feedback Visual**:  
  - Item selecionado em long-press recebe destaque visual (background cinza).  
  - RecyclerView desabilitada durante ActionMode para evitar múltiplas seleções.  

### Entrega 5
- **Sistema de Persistência com Room Database**:  
  - Implementação de banco de dados local usando Room ORM.  
  - `LancamentosDatabase`: configuração do banco de dados com TypeConverters.  
  - `Converters`: conversores de tipo para `Date` e `List<Item>` com suporte a serialização JSON via Gson.  
  - `LancamentoDao`: interface de acesso aos dados com operações CRUD.  

- **Integração de Itens em Lançamentos**:  
  - **CadastroItemActivity**: nova Activity para cadastro de itens com validação de entrada.  
    - Campos: descrição, quantidade, valor unitário, valor desconto (com cálculo automático de total).  
    - CheckBox para identificar se o item é rateado entre casais.  
    - Spinner para seleção de casal (habilitado apenas quando checkbox selecionado).  
    - TextWatcher para cálculo em tempo real do valor total do item.  
  - **Item Entity**: modelo de dados com campos `id_item`, `descricao`, `quantidade`, `valor_unitario`, `valor_desconto`, `valor_total`, `rateio_casal`, `casal_rateio`.  

- **Gerenciamento de Lista de Itens**:  
  - **RecyclerView de Itens em CadastroLancamentoActivity**: exibição da lista de itens adicionados ao lançamento.  
  - **ItemRecyclerViewAdapter**: adapter customizado para exibir itens com opções de interação.  
  - **FloatingActionButton (FAB)**: botão flutuante para adicionar novos itens, visível apenas quando o lançamento é do tipo "Compra de Mercado".  
  - Dinâmica de visibilidade: FAB fica oculto quando checkbox "Conta de Casa" é selecionado e visível para "Compra de Mercado".  

- **Fluxo de Navegação Melhorado**:  
  - **ActivityResultLauncher**: substituição de `startActivityForResult()` (deprecated) por **ActivityResultLauncher** para retorno de dados entre Activities.  
  - Communicação entre CadastroItemActivity e CadastroLancamentoActivity via Bundle com dados do item.  
  - Manutenção do estado da lista de itens durante a navegação entre Activities.  

- **ItensActivity**: Activity adicional para listagem e gerenciamento isolado de itens (caso necessário).  

- **Persistência de Dados**:  
  - Operações de banco de dados (inserção, atualização, consulta) integradas ao fluxo de cadastro de lançamentos.  
  - Serialização de `List<Item>` em JSON para armazenamento em banco relacional.  

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

### Delivery 4
- **ActionMode for Context**: Context menu implementation with long-press interaction.  

- **Edit Expenses (Lançamento)**:  
  - Single click on list item opens pre-filled edit form.  
  - After editing, list is updated and automatically re-sorted.  

- **Delete Expenses**:  
  - Long-press on an item activates **ActionMode** with visual highlight (gray background).  
  - Context menu displays "Edit" and "Delete" options with icons.  
  - Deletion removes the item from the list and updates RecyclerView.  

- **Action Menu**:  
  - Top menu in **LancamentosActivity** with options:  
    - **Add**: opens **CadastroLancamentoActivity** for new expense entry.  
    - **About**: accesses **SobreActivity**.  
  - Visual icons for better UX.  

- **Automatic Sorting**:  
  - Expenses are sorted in **ascending order by date** using `Comparator`.  
  - Sorting occurs after adding or editing an expense.  

- **ActivityResultLauncher**:  
  - Replacement of deprecated `startActivityForResult()` with **ActivityResultLauncher**.  
  - Separate launchers for creation (`launcherNovoLancamento`) and editing (`launcherEditarLancamento`).  

- **Validations and Visual Feedback**:  
  - Selected item in long-press receives visual highlight (gray background).  
  - RecyclerView disabled during ActionMode to prevent multiple selections.  

### Delivery 5
- **Persistence System with Room Database**:  
  - Implementation of local database using Room ORM.  
  - `LancamentosDatabase`: database configuration with TypeConverters.  
  - `Converters`: type converters for `Date` and `List<Item>` with JSON serialization support via Gson.  
  - `LancamentoDao`: data access interface with CRUD operations.  

- **Item Integration in Expenses (Lançamentos)**:  
  - **CadastroItemActivity**: new Activity for item registration with input validation.  
    - Fields: description, quantity, unit value, discount value (with automatic total calculation).  
    - CheckBox to identify if item is split between couples.  
    - Spinner for couple selection (enabled only when checkbox is selected).  
    - TextWatcher for real-time calculation of item total value.  
  - **Item Entity**: data model with fields `id_item`, `descricao`, `quantidade`, `valor_unitario`, `valor_desconto`, `valor_total`, `rateio_casal`, `casal_rateio`.  

- **Item List Management**:  
  - **RecyclerView of Items in CadastroLancamentoActivity**: displays list of items added to the expense.  
  - **ItemRecyclerViewAdapter**: customized adapter to display items with interaction options.  
  - **FloatingActionButton (FAB)**: floating button to add new items, visible only when expense type is "Market Purchase".  
  - Visibility dynamics: FAB is hidden when "Household Bill" checkbox is selected and visible for "Market Purchase".  

- **Enhanced Navigation Flow**:  
  - **ActivityResultLauncher**: replacement of deprecated `startActivityForResult()` with **ActivityResultLauncher** for data return between Activities.  
  - Communication between CadastroItemActivity and CadastroLancamentoActivity via Bundle with item data.  
  - Maintenance of item list state during navigation between Activities.  

- **ItensActivity**: additional Activity for isolated item listing and management (if needed).  

- **Data Persistence**:  
  - Database operations (insert, update, query) integrated into the expense registration flow.  
  - Serialization of `List<Item>` to JSON for storage in relational database.  


