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